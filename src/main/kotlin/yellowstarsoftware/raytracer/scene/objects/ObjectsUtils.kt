package yellowstarsoftware.raytracer.scene.objects

import yellowstarsoftware.raytracer.scene.SceneObject
import yellowstarsoftware.raytracer.utils.Color
import yellowstarsoftware.yellowstar.math.geometry.Vector3D

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