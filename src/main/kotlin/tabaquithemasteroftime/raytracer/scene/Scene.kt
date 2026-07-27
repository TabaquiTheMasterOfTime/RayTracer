package tabaquithemasteroftime.raytracer.scene

import tabaquithemasteroftime.raytracer.utils.Color

/**
 * Scene.
 * @property objects scene objects
 * @property lights scene light sources
 * @property fog scene fog
 * @property ambientLight scene ambient light
 * @property voidColor color of The Infinite Emptiness Of The Great Void
 */
data class Scene(
    val objects: List<SceneObject>,
    val lights: List<SceneLight>,
    val fog: SceneFog,
    val ambientLight: Color,
    val voidColor: Color
)