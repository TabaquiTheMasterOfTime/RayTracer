package tabaquithemasteroftime.raytracer.scene

import tabaquithemasteroftime.raytracer.utils.Color

/**
 * Fog.
 */
interface SceneFog {

    /**
     * Adds fog to [originalColor] using [rayTracingResult].
     */
    fun getColorWithFog(
        rayTracingResult: SceneObject.RayTracingResult,
        originalColor: Color
    ): Color
}