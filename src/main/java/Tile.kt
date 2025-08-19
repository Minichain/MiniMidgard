
data class Tile(
  val worldCoordinates: DoubleArray
) {

  companion object {
    val width: Int = 100
    val height: Int = 100
    private val sprite: Texture = Texture.loadTexture("src/main/resources/textures/grass_01.png")
  }

  private lateinit var cameraCoordinates: DoubleArray

  fun update() {
    cameraCoordinates = worldCoordinates.toCameraCoordinates()
  }

  fun render() {

    val x = (cameraCoordinates[0] - (Parameters.resolution.width / 2f)) / Parameters.resolution.width
    val y = (cameraCoordinates[1] - (Parameters.resolution.height / 2f)) / Parameters.resolution.height

    val u1 = 0f
    val v2 = 1f
    val v1 = 0f
    val u2 = 1f

    val x1 = x - Camera.zoom * (width.toFloat() / Window.resolution.width.toFloat()) / 2f
    val y1 = y - Camera.zoom * (height.toFloat() / Window.resolution.height.toFloat()) / 2f
    val x2 = x + Camera.zoom * (width.toFloat() / Window.resolution.width.toFloat()) / 2f
    val y2 = y + Camera.zoom * (height.toFloat() / Window.resolution.height.toFloat()) / 2f

    GraphicsManager.render(sprite, x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), u1, v1, u2, v2)
  }
}