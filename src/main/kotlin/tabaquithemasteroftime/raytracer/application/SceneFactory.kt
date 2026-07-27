package tabaquithemasteroftime.raytracer.application

import tabaquithemasteroftime.raytracer.awtutils.Image
import tabaquithemasteroftime.raytracer.awtutils.loadImage
import tabaquithemasteroftime.raytracer.scene.Scene
import tabaquithemasteroftime.raytracer.scene.SceneObject
import tabaquithemasteroftime.raytracer.scene.fog.SphericalFog
import tabaquithemasteroftime.raytracer.scene.objects.TextureInfo
import tabaquithemasteroftime.raytracer.scene.objects.asObject
import tabaquithemasteroftime.raytracer.utils.Colors
import tabaquithemasteroftime.raytracer.utils.Colors.SAINT_PETERSBUGR_DEFAULT_SKY_COLOR
import tabaquithemasteroftime.raytracer.utils.asColor
import tabaquithemasteroftime.raytracer.utils.maths.Rectangle3D
import tabaquithemasteroftime.yellowstar.math.geometry.Vector2D
import tabaquithemasteroftime.yellowstar.math.geometry.Vector3D

/**
 * Scene factory.
 */
interface SceneFactory {

    /**
     * Creates a scene.
     */
    fun create(): Scene

    companion object {

        /**
         * Creates a [SceneFactoryImpl].
         */
        fun of(): SceneFactory = SceneFactoryImpl()
    }
}

private class SceneFactoryImpl : SceneFactory {

    private val globalTextureScale = 1f / 128f

    override fun create(): Scene {
        val backgroundColor = SAINT_PETERSBUGR_DEFAULT_SKY_COLOR
        val floorSize = 100_000f
        val floorTextureInfo = TextureInfo(
            loadImage("Asphalt.png"),
            floorSize * globalTextureScale,
            floorSize * globalTextureScale
        )
        val wallTexture = loadImage("Bricks.png")
        val ceilingTexture = loadImage("Roof.png")
        val objects = buildList<SceneObject> {
            // it's actually a floor, not a ceiling
            this += createCeiling(
                vertex = Vector3D(-floorSize / 2, 0f, -floorSize / 2),
                width = floorSize,
                length = floorSize
            ).asObject(floorTextureInfo)

            this += createBuilding(
                start = Vector2D(100f, 100f),
                width = 1000f,
                length = 2000f,
                height = 500f,
                wallTexture = wallTexture,
                ceilingTexture = ceilingTexture
            )

            this += createBuilding(
                start = Vector2D(-2_000f, 100f),
                width = 1000f,
                length = 2000f,
                height = 5_000f,
                wallTexture = wallTexture,
                ceilingTexture = ceilingTexture
            )
        }
        return Scene(
            objects = objects,
            lights = emptyList(),
            fog = SphericalFog(
                50f,
                10_000f,
                backgroundColor
            ),
            ambientLight = Colors.WHITE.rgb.times(0.45f).asColor(),
            voidColor = backgroundColor
        )
    }

    private fun createBuilding(
        start: Vector2D,
        width: Float,
        length: Float,
        height: Float,
        wallTexture: Image,
        ceilingTexture: Image
    ): List<SceneObject> {
        val p1 = start
        val p2 = p1 + Vector2D(0f, length)
        val p3 = p1 + Vector2D(width, length)
        val p4 = p1 + Vector2D(width, 0f)
        val walls = listOf(
            createWall(p1, p2, height),
            createWall(p2, p3, height),
            createWall(p3, p4, height),
            createWall(p4, p1, height),
        ).map { rectangle ->
            rectangle.asObject(
                TextureInfo(
                    texture = wallTexture,
                    scaleX = rectangle.width * globalTextureScale,
                    scaleY = rectangle.height * globalTextureScale,
                )
            )
        }

        val ceiling = createCeiling(
            vertex = Vector3D(p1.x, height, p1.y),
            width = width,
            length = length
        ).asObject(
            TextureInfo(
                texture = ceilingTexture,
                scaleX = width * globalTextureScale,
                scaleY = length * globalTextureScale
            )
        )

        return walls + ceiling
    }

    private fun createCeiling(
        vertex: Vector3D,
        width: Float,
        length: Float,
    ) = Rectangle3D.of(
        vertex = vertex,
        right = Vector3D.I,
        up = Vector3D.K,
        width = width,
        height = length,
        needInvertNormal = true
    )

    private fun createWall(
        start: Vector2D,
        end: Vector2D,
        height: Float
    ): Rectangle3D {
        val direction = end - start
        val right = Vector3D(direction.x, 0f, direction.y).normalized
        val up = Vector3D.J
        return Rectangle3D.of(
            vertex = Vector3D(start.x, 0f, start.y),
            right = right,
            up = up,
            width = direction.length,
            height = height,
            needInvertNormal = false
        )
    }
}