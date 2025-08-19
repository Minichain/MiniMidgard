
data class Tile(
  private val i: Int,
  private val j: Int
) {

  companion object {
    private const val width: Int = 100
    private const val height: Int = 100
    private val sprite: Texture = Texture.loadTexture("src/main/resources/textures/grass_01.png")
  }

  private val worldCoordinates: Array<DoubleArray>
  private var cameraCoordinates: Array<DoubleArray>

  init {
    val centerX = i.toDouble() * width.toDouble()
    val centerZ = j.toDouble() * height.toDouble()
    worldCoordinates = arrayOf(
      doubleArrayOf(centerX + width.toDouble() / 2.0, 0.0, centerZ - height.toDouble()),
      doubleArrayOf(centerX + width.toDouble() / 2.0, 0.0, centerZ + height.toDouble()),
      doubleArrayOf(centerX - width.toDouble() / 2.0, 0.0, centerZ + height.toDouble()),
      doubleArrayOf(centerX - width.toDouble() / 2.0, 0.0, centerZ - height.toDouble())
    )
    cameraCoordinates = arrayOf(
      worldCoordinates[0].toCameraCoordinates(),
      worldCoordinates[1].toCameraCoordinates(),
      worldCoordinates[2].toCameraCoordinates(),
      worldCoordinates[3].toCameraCoordinates()
    )
  }

  fun update() {
    cameraCoordinates = arrayOf(
      worldCoordinates[0].toCameraCoordinates(),
      worldCoordinates[1].toCameraCoordinates(),
      worldCoordinates[2].toCameraCoordinates(),
      worldCoordinates[3].toCameraCoordinates()
    )
  }

  fun render() {

    val u1 = 0f
    val v2 = 1f
    val v1 = 0f
    val u2 = 1f

    val vertex1 = cameraCoordinates[0].multiplyByFactor(1.0 / 720.0).toFloatArray()
    val vertex2 = cameraCoordinates[1].multiplyByFactor(1.0 / 720.0).toFloatArray()
    val vertex3 = cameraCoordinates[2].multiplyByFactor(1.0 / 720.0).toFloatArray()
    val vertex4 = cameraCoordinates[3].multiplyByFactor(1.0 / 720.0).toFloatArray()

    GraphicsManager.render(sprite, vertex1, vertex2, vertex3, vertex4, u1, v1, u2, v2)

  }
}