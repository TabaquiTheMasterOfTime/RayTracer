package yellowstarsoftware.raytracer.scene

import yellowstarsoftware.raytracer.utils.Color
import yellowstarsoftware.yellowstar.math.geometry.Vector3D
import yellowstarsoftware.yellowstar.math.geometry.objects.Ray3D

/**
 * Scene object. Can be considered as
 * a rendering primitive.
 */
interface SceneObject {

    /**
     * Traces [ray] to the [SceneObject].
     */
    fun traceRay(
        ray: Ray3D,
    ): RayTracingResult?

    /**
     * Returns true, if this object
     * blocks light from [first] point to [second] point.
     */
    fun blocksLight(
        first: Vector3D,
        second: Vector3D
    ) : Boolean

    /**
     * Returns true if light to this [SceneObject]
     * can be blocked by other objects.
     */
    val canBeShadowed: Boolean get() = true

    /**
     * Ray tracing result.
     */
    data class RayTracingResult(
        val sceneObject: SceneObject,
        val distance: Float,
        val normal: Vector3D,
        val colorProvider: () -> Color,
    )
}