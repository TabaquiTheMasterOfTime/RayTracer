package yellowstarsoftware.raytracer.application

import yellowstarsoftware.raytracer.scenerenderer.SceneRenderingSettings
import yellowstarsoftware.raytracer.utils.matrixworkexecutor.CoroutinesMatrixWorkExecutor

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