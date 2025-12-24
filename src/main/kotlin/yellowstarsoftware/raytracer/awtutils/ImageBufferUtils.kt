package yellowstarsoftware.raytracer.awtutils

import yellowstarsoftware.raytracer.utils.Color
import yellowstarsoftware.raytracer.utils.blue
import yellowstarsoftware.raytracer.utils.green
import yellowstarsoftware.raytracer.utils.red
import yellowstarsoftware.yellowstar.math.utils.mapAndCoerce

/**
 * Sets pixel [color] of this image
 * at given position ([x], [y]).
 */
operator fun ImageBuffer.set(
    x: Int,
    y: Int,
    color: Color
) {
    val r = mapAndCoerce(color.red, 0f, 1f, 0, 255)
    val g = mapAndCoerce(color.green, 0f, 1f, 0, 255)
    val b = mapAndCoerce(color.blue, 0f, 1f, 0, 255)
    val baseIndex = (y * width + x) * ImageBuffer.BYTES_PER_PIXEL
    this.pixels[baseIndex] = r
    this.pixels[baseIndex + 1] = g
    this.pixels[baseIndex + 2] = b
}