package tabaquithemasteroftime.raytracer.utils.maths

import tabaquithemasteroftime.yellowstar.math.geometry.Vector3D
import tabaquithemasteroftime.yellowstar.math.geometry.algorithms.getLinePlaneIntersectionParameter
import tabaquithemasteroftime.yellowstar.math.geometry.objects.Ray3D
import tabaquithemasteroftime.yellowstar.math.geometry.times
import kotlin.math.abs

/**
 * Information about intersection
 * of a [Rectangle3D] and a [Ray3D].
 */
class RectangleRayIntersectionResult(
    val t: Float,
    val x: Float,
    val y: Float
)

/**
 * Checks if [rectangle] intersects [ray].
 */
fun intersectRectangle2DRay(
    rectangle: Rectangle3D,
    ray: Ray3D
) : RectangleRayIntersectionResult? {
    // back-face culling
    if (rectangle.plane.normal dot -ray.direction < 0.01f) return null

    val t = getLinePlaneIntersectionParameter(
        startPoint = ray.vertex,
        direction = ray.direction,
        plane = rectangle.plane
    )
    if (t < 0) return null
    val p = ray.vertex + t * ray.direction
    val v = p - rectangle.vertex
    val x = v dot rectangle.right
    val y = v dot rectangle.up
    if (x < 0f || x > rectangle.width || y < 0f || y > rectangle.height) return null
    return RectangleRayIntersectionResult(
        t = t,
        x = x,
        y = y
    )
}

/**
 * Checks if [rectangle] intersects the segment
 * defined by [first] and [second].
 */
fun intersectRectangleSegment(
    rectangle: Rectangle3D,
    first: Vector3D,
    second: Vector3D
) : Boolean {
    val direction = second - first
    // if the rectangle is parallel to the segment,
    // considering them as not intersecting
    if (abs(rectangle.plane.normal dot direction) < 0.01f) return false
    val t = getLinePlaneIntersectionParameter(
        startPoint = first,
        direction = direction,
        plane = rectangle.plane
    )
    if (t < 0 || t > 1) return false
    val p = first + t * direction
    val v = p - rectangle.vertex
    val x = v dot rectangle.right
    val y = v dot rectangle.up
    return x > 0f && x < rectangle.width && y > 0f && y < rectangle.height
}