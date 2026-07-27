package tabaquithemasteroftime.raytracer.scene.objects

import tabaquithemasteroftime.raytracer.awtutils.Image

/**
 * Information about a texture.
 * @property texture the texture
 * @property scaleX OX scale of the texture
 * @property scaleY OY scale of the texture
 */
class TextureInfo(
    val texture: Image,
    val scaleX: Float,
    val scaleY: Float
)
