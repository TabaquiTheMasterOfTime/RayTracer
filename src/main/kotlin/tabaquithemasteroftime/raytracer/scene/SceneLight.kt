package tabaquithemasteroftime.raytracer.scene

import tabaquithemasteroftime.raytracer.utils.Color
import tabaquithemasteroftime.yellowstar.math.geometry.Vector3D

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