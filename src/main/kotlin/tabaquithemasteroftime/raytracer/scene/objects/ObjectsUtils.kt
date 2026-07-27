package tabaquithemasteroftime.raytracer.scene.objects

import tabaquithemasteroftime.raytracer.scene.SceneObject
import tabaquithemasteroftime.raytracer.utils.Color
import tabaquithemasteroftime.yellowstar.math.geometry.Vector3D

/**
 * Shortcut for creating [SceneObject.RayTracingResult].
 */
fun SceneObject.makeRayTraceResult(
    distance: Float,
    normal: Vector3D,
    colorProvider: () -> Color,
) = SceneObject.RayTracingResult(
    sceneObject = this,
    distance = distance,
    normal = normal,
    colorProvider = colorProvider
)