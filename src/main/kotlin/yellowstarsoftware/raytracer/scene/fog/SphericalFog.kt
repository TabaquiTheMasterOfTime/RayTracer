package yellowstarsoftware.raytracer.scene.fog

import yellowstarsoftware.raytracer.scene.SceneFog
import yellowstarsoftware.raytracer.scene.SceneObject
import yellowstarsoftware.raytracer.utils.Color
import yellowstarsoftware.raytracer.utils.asColor
import yellowstarsoftware.yellowstar.math.geometry.lerp

/**
 * Spherical fog implementation.
 * @property startDistance start fog distance
 * @property length length of the fog
 *            (fog end distance = [startDistance] + [length])
 * @property color color of the fog
 */
data class SphericalFog(
    val startDistance: Float,
    val length: Float,
    val color: Color
) : SceneFog {

    override fun getColorWithFog(
        rayTracingResult: SceneObject.RayTracingResult,
        originalColor: Color
    ): Color {
        val k = (rayTracingResult.distance - startDistance) / length
        return originalColor.rgb.lerp(
            b = color.rgb,
            t = k.coerceIn(0f, 1f)
        ).asColor()
    }
}