package tabaquithemasteroftime.raytracer.application

import tabaquithemasteroftime.raytracer.scenerenderer.SceneRenderingSettings

/**
 * Scene rendering settings manager.
 */
interface SceneRenderingSettingsManager {

    /**
     * Current [SceneRenderingSettings].
     */
    val sceneRenderingSettings: SceneRenderingSettings

    /**
     * Toggles light.
     */
    fun toggleLight()

    /**
     * Toggles ambient light.
     */
    fun toggleAmbientLight()

    /**
     * Toggles fog.
     */
    fun toggleFog()

    /**
     * Toggles shadows.
     */
    fun toggleShadows()

    companion object {

        /**
         * Creates a [SceneRenderingSettingsManager].
         */
        fun of(
            sceneRenderingSettings: SceneRenderingSettings
        ): SceneRenderingSettingsManager = SceneRenderingSettingsManagerImpl(
            sceneRenderingSettings = sceneRenderingSettings
        )
    }
}

private class SceneRenderingSettingsManagerImpl(
    override var sceneRenderingSettings: SceneRenderingSettings
) : SceneRenderingSettingsManager {

    override fun toggleLight() {
        sceneRenderingSettings = sceneRenderingSettings.copy(
            lightEnabled = !sceneRenderingSettings.lightEnabled
        )
    }

    override fun toggleAmbientLight() {
        sceneRenderingSettings = sceneRenderingSettings.copy(
            ambientLightEnabled = !sceneRenderingSettings.ambientLightEnabled
        )
    }

    override fun toggleFog() {
        sceneRenderingSettings = sceneRenderingSettings.copy(
            fogEnabled = !sceneRenderingSettings.fogEnabled
        )
    }

    override fun toggleShadows() {
        sceneRenderingSettings = sceneRenderingSettings.copy(
            shadowsEnabled = !sceneRenderingSettings.shadowsEnabled
        )
    }
}