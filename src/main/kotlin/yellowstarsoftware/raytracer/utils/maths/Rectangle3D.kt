package yellowstarsoftware.raytracer.utils.maths

import yellowstarsoftware.yellowstar.math.geometry.Vector3D
import yellowstarsoftware.yellowstar.math.geometry.objects.Plane

/**
 * Rectangle in 3D.
 * @property vertex lower left point of the rectangle
 * @property right "right" direction of the rectangle of unit size
 * @property up "up" direction of the rectangle of unit size
 * @property plane [Plane] of the rectangle
 * @property width width of the rectangle
 * @property height height of the rectangle
 */
@ConsistentCopyVisibility
data class Rectangle3D private constructor(
    val vertex: Vector3D,
    val right: Vector3D,
    val up: Vector3D,
    val plane: Plane,
    val width: Float,
    val height: Float
) {

    companion object {

        /**
         * Creates a [Rectangle3D].
         * @param vertex vertex of the rectangle
         * @param right "right" direction
         * @param up "up" direction
         * @param width width
         * @param height height
         * @param needInvertNormal if true, normal will be multiplied by -1
         */
        fun of(
            vertex: Vector3D,
            right: Vector3D,
            up: Vector3D,
            width: Float,
            height: Float,
            needInvertNormal: Boolean
        ) = Rectangle3D(
            vertex = vertex,
            right = right,
            up = up,
            plane = Plane(
                point = vertex,
                normal = (right cross up).normalized * if (needInvertNormal) -1f else 1f
            ),
            width = width,
            height = height
        )
    }
}