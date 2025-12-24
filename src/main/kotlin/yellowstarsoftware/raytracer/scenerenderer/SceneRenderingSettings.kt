package yellowstarsoftware.raytracer.scenerenderer

import yellowstarsoftware.raytracer.utils.matrixworkexecutor.MatrixWorkExecutor

/**
 * Settings for rendering.
 * @property lightEnabled are lights enabled
 * @property ambientLightEnabled is ambient light enabled
 * @property fogEnabled is fog enabled
 * @property shadowsEnabled are shadows enabled
 * @property executor [MatrixWorkExecutor] for tracing rays
 */
data class SceneRenderingSettings(
    val lightEnabled: Boolean,
    val ambientLightEnabled: Boolean,
    val fogEnabled: Boolean,
    val shadowsEnabled: Boolean,
    val executor: MatrixWorkExecutor
)