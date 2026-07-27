package tabaquithemasteroftime.raytracer.scenerenderer

import tabaquithemasteroftime.raytracer.awtutils.ImageBuffer
import tabaquithemasteroftime.raytracer.awtutils.set
import tabaquithemasteroftime.raytracer.scene.Scene
import tabaquithemasteroftime.raytracer.scene.SceneObject
import tabaquithemasteroftime.raytracer.utils.Camera
import tabaquithemasteroftime.raytracer.utils.Color
import tabaquithemasteroftime.raytracer.utils.asColor
import tabaquithemasteroftime.yellowstar.math.geometry.Vector3D
import tabaquithemasteroftime.yellowstar.math.geometry.objects.Ray3D
import tabaquithemasteroftime.yellowstar.math.geometry.times
import tabaquithemasteroftime.yellowstar.math.utils.map

/**
 * Scene renderer.
 */
interface SceneRenderer {

    /**
     * Renders [scene] into [imageBuffer] from point of view
     * of [camera] using [renderingSettings]
     */
    fun renderScene(
        scene: Scene,
        camera: Camera,
        renderingSettings: SceneRenderingSettings,
        imageBuffer: ImageBuffer
    )

    companion object {

        /**
         * Creates a [SceneRenderer].
         */
        fun of(): SceneRenderer = SceneRendererImpl()
    }
}

private class SceneRendererImpl : SceneRenderer {

    override fun renderScene(
        scene: Scene,
        camera: Camera,
        renderingSettings: SceneRenderingSettings,
        imageBuffer: ImageBuffer
    ) {
        renderingSettings.executor.invoke(
            width = imageBuffer.width,
            height = imageBuffer.height
        ) { i, j ->
            val ray = makeRay(i, j, imageBuffer, camera)
            val traceResult = traceRayToObjects(ray, scene)
            imageBuffer[i, j] = if (traceResult != null) {
                getObjectColor(
                    rayTracingResult = traceResult,
                    ray = ray,
                    scene = scene,
                    renderingSettings = renderingSettings
                )
            } else {
                scene.voidColor
            }
        }
    }
}

private fun traceRayToObjects(
    ray: Ray3D,
    scene: Scene
): SceneObject.RayTracingResult? {
    var closest: SceneObject.RayTracingResult? = null
    for (sceneObject in scene.objects) {
        val result = sceneObject.traceRay(ray)
        if (closest == null || (result != null && result.distance < closest.distance)) {
            closest = result
        }
    }
    return closest
}

fun getObjectColor(
    rayTracingResult: SceneObject.RayTracingResult,
    ray: Ray3D,
    scene: Scene,
    renderingSettings: SceneRenderingSettings
): Color {
    val light = getObjectLight(
        rayTracingResult,
        ray,
        scene,
        renderingSettings
    ).rgb
    val baseColor = rayTracingResult.colorProvider.invoke().rgb
    val lightedColor = (light * baseColor).asColor()
    return if (renderingSettings.fogEnabled) {
        scene.fog.getColorWithFog(rayTracingResult, lightedColor)
    } else {
        lightedColor
    }
}

private fun getObjectLight(
    rayTracingResult: SceneObject.RayTracingResult,
    ray: Ray3D,
    scene: Scene,
    renderingSettings: SceneRenderingSettings
): Color {
    var resultColor = Vector3D(0f, 0f, 0f)

    if (renderingSettings.ambientLightEnabled) {
        resultColor += scene.ambientLight.rgb
    }

    if (renderingSettings.lightEnabled) {
        val sceneObject = rayTracingResult.sceneObject
        val intersectionPoint = ray.vertex + ray.direction * rayTracingResult.distance
        scene.lights.forEach { light ->
            val direction = (light.center - intersectionPoint).normalized
            val scale = direction dot rayTracingResult.normal
            if (scale > 0) {
                val isLightBlocked = renderingSettings.shadowsEnabled && sceneObject.canBeShadowed && scene
                    .objects
                    .any { shadowCaster ->
                        shadowCaster !== sceneObject && shadowCaster.blocksLight(
                            intersectionPoint,
                            light.center
                        )
                    }
                if (!isLightBlocked) {
                    resultColor += light.getLightColor(intersectionPoint).rgb * scale
                }
            }
        }
    }

    return resultColor.asColor()
}

private fun makeRay(
    i: Int,
    j: Int,
    imageBuffer: ImageBuffer,
    camera: Camera
): Ray3D {
    val w = imageBuffer.width.toFloat()
    val h = imageBuffer.height.toFloat()
    val w1 = camera.virtualWindowWidthHalf
    val h1 = camera.virtualWindowHeightHalf
    val x = map(
        i.toFloat() + 0.5f,
        0f, w - 1f,
        -w1, w1
    )
    val y = map(
        h - j.toFloat() - 0.5f,
        0f, h - 1f,
        -h1, h1
    )
    val direction = x * camera.right +
        y * camera.up +
        camera.near * camera.forward
    return Ray3D(
        vertex = camera.position,
        direction = direction.normalized
    )
}