package tabaquithemasteroftime.raytracer.application

import tabaquithemasteroftime.raytracer.scenerenderer.SceneRenderingSettings
import tabaquithemasteroftime.raytracer.utils.matrixworkexecutor.CoroutinesMatrixWorkExecutor

/**
 * Default [SceneRenderingSettings].
 */
val DEFAULT_RENDERING_SETTINGS = SceneRenderingSettings(
    lightEnabled = true,
    ambientLightEnabled = true,
    fogEnabled = true,
    shadowsEnabled = true,
    executor = CoroutinesMatrixWorkExecutor(16)
)