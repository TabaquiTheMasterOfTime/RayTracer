package tabaquithemasteroftime.raytracer.awtutils

import tabaquithemasteroftime.raytracer.awtutils.ImageBuffer.Companion.BYTES_PER_PIXEL
import java.awt.Graphics
import java.awt.image.BufferedImage
import javax.swing.JComponent

/**
 * Image buffer.
 */
interface ImageBuffer {

    /**
     * Width of the instance.
     */
    val width: Int

    /**
     * Height of the instance.
     */
    val height: Int

    /**
     * Pixels of the instance.
     * There is a byte for each r, g and b components for each pixel.
     * So it's size is always [width] * [height] * [BYTES_PER_PIXEL].
     */
    val pixels: IntArray

    /**
     * Draws this buffer using [component] and [graphics].
     */
    fun draw(
        component: JComponent,
        graphics: Graphics
    )

    companion object {

        /**
         * Count of bytes per pixel.
         */
        const val BYTES_PER_PIXEL = 3

        /**
         * Creates an [ImageBuffer] with given [width] and [height].
         */
        fun of(
            width: Int,
            height: Int
        ): ImageBuffer = ImageBufferImpl.of(
            width = width,
            height = height
        )
    }
}

private class ImageBufferImpl(
    override val pixels: IntArray,
    private val buffer: BufferedImage
) : ImageBuffer {

    override val width: Int
        get() = buffer.width

    override val height: Int
        get() = buffer.height

    override fun draw(
        component: JComponent,
        graphics: Graphics
    ) {
        buffer.raster.setPixels(0, 0, width, height, pixels)
        graphics.drawImage(buffer, 0, 0, component.width, component.height, null)
    }

    companion object {

        fun of(
            width: Int,
            height: Int
        ) = ImageBufferImpl(
            pixels = IntArray(size = width * height * BYTES_PER_PIXEL),
            buffer = BufferedImage(
                width,
                height,
                BufferedImage.TYPE_INT_RGB
            )
        )
    }
}