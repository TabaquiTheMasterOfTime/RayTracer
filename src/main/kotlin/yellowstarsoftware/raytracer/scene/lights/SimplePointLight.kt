package yellowstarsoftware.raytracer.scene.lights

import yellowstarsoftware.raytracer.scene.SceneLight
import yellowstarsoftware.raytracer.utils.Color
import yellowstarsoftware.raytracer.utils.Colors
import yellowstarsoftware.raytracer.utils.asColor
import yellowstarsoftware.yellowstar.math.geometry.Vector3D
import yellowstarsoftware.yellowstar.math.geometry.times

/**
 * Simple point source of light.
 * @property center center of the light source
 * @property radius radius of the light source
 * @property color color of the light source
 */
class SimplePointLight(
    override val center: Vector3D,
    private val radius: Float,
    private val color: Color
) : SceneLight {

    override fun getLightColor(
        point: Vector3D
    ): Color {
        val distance = (center - point).length
        return if (distance <= radius) {
            val k = (radius - distance) / radius
            (k * color.rgb).asColor()
        } else {
            Colors.BLACK
        }
    }
}