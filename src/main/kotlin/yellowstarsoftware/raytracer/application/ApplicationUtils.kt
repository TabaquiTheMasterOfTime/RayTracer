package yellowstarsoftware.raytracer.application

import yellowstarsoftware.raytracer.awtutils.*
import yellowstarsoftware.raytracer.scenerenderer.SceneRenderer

/**
 * Runs [this] [Application].
 * @param windowWidth window width
 * @param windowHeight window height
 * @param bufferWidth buffer width
 * @param bufferHeight buffer height
 */
fun Application.run(
    windowWidth: Int = 700,
    windowHeight: Int = 700,
    bufferWidth: Int = 640,
    bufferHeight: Int = 480
) {
    val imageBuffer = ImageBuffer.of(bufferWidth, bufferHeight)
    val sceneRenderer = SceneRenderer.of()
    val pressedKeys = mutableSetOf<Key>()
    val reactiveKeyBindings = reactiveKeyBindings
    val continuousKeyBindings = continuousKeyBindings

    val renderer = Renderer { component, graphics ->
        sceneRenderer.renderScene(
            scene = scene,
            camera = camera,
            renderingSettings = sceneRenderingSettings,
            imageBuffer = imageBuffer
        )
        imageBuffer.draw(component, graphics)

        continuousKeyBindings.forEach { binding ->
            if (binding.key in pressedKeys) {
                binding.action.invoke()
            }
        }

        onSceneRendered(component, graphics)
    }

    val keyboardListener = object : KeyboardListener {

        override fun onKeyDown(key: Key) {
            pressedKeys += key
            processReactiveKeys(key)
        }

        override fun onKeyUp(key: Key) {
            pressedKeys -= key
        }

        private fun processReactiveKeys(key: Key) {
            reactiveKeyBindings.forEach { binding ->
                if (binding.key == key) {
                    binding.action.invoke()
                }
            }
        }
    }

    showWindow(
        width = windowWidth,
        height = windowHeight,
        repaintDelay = 1,
        renderer = renderer,
        keyboardListener = keyboardListener
    )
}