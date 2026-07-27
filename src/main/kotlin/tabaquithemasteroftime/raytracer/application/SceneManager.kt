package tabaquithemasteroftime.raytracer.application

import tabaquithemasteroftime.raytracer.scene.Scene
import tabaquithemasteroftime.raytracer.scene.SceneLight

/**
 * Scene manager.
 */
interface SceneManager {

    /**
     * Scene.
     */
    val scene: Scene

    /**
     * Adds [light] to the scene.
     */
    operator fun plusAssign(light: SceneLight)

    companion object {

        /**
         * Creates a [SceneManager] with given initial [scene].
         */
        fun of(
            scene: Scene
        ) : SceneManager = SceneManagerImpl(scene)
    }
}

private class SceneManagerImpl(
    override var scene: Scene
) : SceneManager {

    override fun plusAssign(
        light: SceneLight
    ) {
        scene = scene.copy(lights = scene.lights + listOf(light))
    }
}