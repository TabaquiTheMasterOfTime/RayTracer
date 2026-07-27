package tabaquithemasteroftime.raytracer.application

import tabaquithemasteroftime.raytracer.awtutils.Key
import tabaquithemasteroftime.raytracer.scene.Scene
import tabaquithemasteroftime.raytracer.scene.lights.SimplePointLight
import tabaquithemasteroftime.raytracer.scenerenderer.SceneRenderingSettings
import tabaquithemasteroftime.raytracer.utils.Camera
import tabaquithemasteroftime.raytracer.utils.Colors
import tabaquithemasteroftime.yellowstar.math.geometry.PI
import tabaquithemasteroftime.yellowstar.math.geometry.PID2
import tabaquithemasteroftime.yellowstar.math.geometry.Quaternion
import tabaquithemasteroftime.yellowstar.math.geometry.Vector3D
import java.awt.Color
import java.awt.Graphics2D
import javax.swing.JComponent
import kotlin.math.abs
import kotlin.system.exitProcess

/**
 * Application.
 */
interface Application {

    /**
     * Current camera.
     */
    val camera: Camera

    /**
     * Current scene.
     */
    val scene: Scene

    /**
     * Current scene rendering settings.
     */
    val sceneRenderingSettings: SceneRenderingSettings

    /**
     * [List] of [KeyBinding] that are triggered
     * once per a key press event.
     */
    val reactiveKeyBindings: List<tabaquithemasteroftime.raytracer.application.KeyBinding>

    /**
     * [List] of [KeyBinding] that are triggered
     * on each frame while their corresponding key is pressed.
     */
    val continuousKeyBindings: List<tabaquithemasteroftime.raytracer.application.KeyBinding>

    /**
     * Called after [scene] was rendered.
     * A perfect place to draw UI.
     */
    fun onSceneRendered(
        component: JComponent,
        graphics: Graphics2D
    )

    companion object {

        /**
         * Creates an [Application].
         */
        fun of() : tabaquithemasteroftime.raytracer.application.Application =
            tabaquithemasteroftime.raytracer.application.ApplicationImpl.Companion.of()
    }
}

private class ApplicationImpl(
    private val cameraManager: tabaquithemasteroftime.raytracer.application.CameraManager,
    private val sceneManager: tabaquithemasteroftime.raytracer.application.SceneManager,
    private val sceneRenderingSettingsManager: tabaquithemasteroftime.raytracer.application.SceneRenderingSettingsManager
) : tabaquithemasteroftime.raytracer.application.Application {

    private val speed = 20f
    private val angleSpeed = PI / 50f
    private var isUiEnabled = true

    override val camera get() = cameraManager.camera
    override val scene get() = sceneManager.scene
    override val sceneRenderingSettings get() = sceneRenderingSettingsManager.sceneRenderingSettings

    override val reactiveKeyBindings = listOf(
        Key.VK_ESCAPE.bind {
            exitProcess(0)
        },
        Key.VK_ENTER.bind {
            val light = SimplePointLight(
                center = cameraManager.camera.position,
                radius = 10_000f,
                color = Colors.YELLOW_LAMP_COLOR
            )
            sceneManager += light
        },
        Key.VK_F1.bind {
            isUiEnabled = !isUiEnabled
        },
        Key.VK_F2.bind {
            sceneRenderingSettingsManager.toggleLight()
        },
        Key.VK_F3.bind {
            sceneRenderingSettingsManager.toggleAmbientLight()
        },
        Key.VK_F4.bind {
            sceneRenderingSettingsManager.toggleFog()
        },
        Key.VK_F5.bind {
            sceneRenderingSettingsManager.toggleShadows()
        }
    )

    override val continuousKeyBindings = listOf(
        Key.VK_W.bind {
            cameraManager.moveForward(speed)
        },
        Key.VK_S.bind {
            cameraManager.moveForward(-speed)
        },
        Key.VK_D.bind {
            cameraManager.moveStrafe(speed)
        },
        Key.VK_A.bind {
            cameraManager.moveStrafe(-speed)
        },
        Key.VK_SPACE.bind {
            cameraManager.moveUp(speed)
        },
        Key.VK_C.bind {
            cameraManager.moveUp(-speed)
        },
        Key.VK_RIGHT.bind {
            cameraManager.rotateHorizontally(angleSpeed)
        },
        Key.VK_LEFT.bind {
            cameraManager.rotateHorizontally(-angleSpeed)
        },
        Key.VK_UP.bind {
            cameraManager.rotateVertically(angleSpeed)
        },
        Key.VK_DOWN.bind {
            cameraManager.rotateVertically(-angleSpeed)
        }
    )

    override fun onSceneRendered(
        component: JComponent,
        graphics: Graphics2D
    ) {
        if (!isUiEnabled) return
        val text = listOf(
            "F1 - ui enabled: true", // always true because otherwise they won't see this
            "F2 - lights enabled: ${sceneRenderingSettings.lightEnabled}",
            "F3 - ambient light enabled: ${sceneRenderingSettings.ambientLightEnabled}",
            "F4 - fog enabled: ${sceneRenderingSettings.fogEnabled}",
            "F5 - shadows enabled: ${sceneRenderingSettings.shadowsEnabled}"
        )
        val stringHeight = 40
        graphics.paint = Color.BLUE
        graphics.font = graphics.font.deriveFont(stringHeight.toFloat())
        text.forEachIndexed { index, string ->
            graphics.drawString(
                string,
                0,
                stringHeight * (index + 1)
            )
        }
    }

    companion object {

        fun of() : tabaquithemasteroftime.raytracer.application.Application {
            val cameraManager = tabaquithemasteroftime.raytracer.application.CameraManager.Companion.of(
                initialCamera = Camera.of(
                    Vector3D.ZERO,
                    rotation = Quaternion.NO_ROTATION,
                    near = 1f,
                    fovY = PID2,
                    aspect = 1f,
                ),
                validator = { camera ->
                    val directionY = camera.forward.y
                    // checking if "vertical" angle is in [-PI/2, PI/2]
                    abs(directionY) < 0.999f
                }
            )
            val sceneManager = tabaquithemasteroftime.raytracer.application.SceneManager.Companion.of(
                tabaquithemasteroftime.raytracer.application.SceneFactory.Companion.of().create()
            )
            val sceneRenderingSettingsManager =
                tabaquithemasteroftime.raytracer.application.SceneRenderingSettingsManager.Companion.of(
                    tabaquithemasteroftime.raytracer.application.DEFAULT_RENDERING_SETTINGS
                )
            return tabaquithemasteroftime.raytracer.application.ApplicationImpl(
                cameraManager = cameraManager,
                sceneManager = sceneManager,
                sceneRenderingSettingsManager = sceneRenderingSettingsManager
            )
        }
    }
}