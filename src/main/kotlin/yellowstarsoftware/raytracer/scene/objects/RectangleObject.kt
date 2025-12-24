package yellowstarsoftware.raytracer.scene.objects

import yellowstarsoftware.raytracer.scene.SceneObject
import yellowstarsoftware.raytracer.utils.maths.Rectangle3D
import yellowstarsoftware.raytracer.utils.maths.intersectRectangle2DRay
import yellowstarsoftware.raytracer.utils.maths.intersectRectangleSegment
import yellowstarsoftware.yellowstar.math.geometry.Vector3D
import yellowstarsoftware.yellowstar.math.geometry.objects.Ray3D
import kotlin.math.roundToInt

/**
 * A rectangle in space.
 * @param rectangle [Rectangle3D]
 * @param textureInfo [TextureInfo]
 */
class RectangleObject(
    private val rectangle: Rectangle3D,
    private val textureInfo: TextureInfo
) : SceneObject {

    override fun traceRay(
        ray: Ray3D
    ): SceneObject.RayTracingResult? {
        val result = intersectRectangle2DRay(
            rectangle = rectangle,
            ray = ray
        )
        result ?: return null
        return makeRayTraceResult(
            distance = result.t,
            normal = rectangle.plane.normal,
            colorProvider = {
                val image = textureInfo.texture
                val tx = textureInfo.scaleX / rectangle.width
                val i = (result.x * tx * image.width).roundToInt() % image.width
                val ty = textureInfo.scaleY / rectangle.height
                val j = (result.y * ty * image.height).roundToInt() % image.height
                image[i, j]
            }
        )
    }

    override fun blocksLight(
        first: Vector3D,
        second: Vector3D
    ) = intersectRectangleSegment(
        rectangle = rectangle,
        first = first,
        second = second
    )
}

/**
 * Creates a [RectangleObject].
 * @receiver rectangle
 * @param textureInfo [TextureInfo]
 */
fun Rectangle3D.asObject(
    textureInfo: TextureInfo
) = RectangleObject(
    rectangle = this,
    textureInfo = textureInfo
)