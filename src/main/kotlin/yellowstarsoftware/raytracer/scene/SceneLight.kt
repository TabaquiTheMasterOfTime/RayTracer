package yellowstarsoftware.raytracer.scene

import yellowstarsoftware.raytracer.utils.Color
import yellowstarsoftware.yellowstar.math.geometry.Vector3D

/**
 * Light source.
 */
interface SceneLight {

    /**
     * Center of the light source.
     */
    val center: Vector3D

    /**
     * Gets light for [point] without considering shadow casters.
     */
    fun getLightColor(point: Vector3D): Color
}