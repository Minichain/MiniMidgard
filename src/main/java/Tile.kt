
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
    val u1 = 0f
    val v2 = 1f
    val v1 = 0f
    val u2 = 1f

    val x1 = (cameraCoordinates[0] / Parameters.resolution.width) - Camera.zoom * width.fractionOf(Window.resolution.width) / 2f
    val y1 = (cameraCoordinates[1] / Parameters.resolution.height) - Camera.zoom * height.fractionOf(Window.resolution.height) / 2f
    val x2 = (cameraCoordinates[0] / Parameters.resolution.width) + Camera.zoom * width.fractionOf(Window.resolution.width) / 2f
    val y2 = (cameraCoordinates[1] / Parameters.resolution.height) + Camera.zoom * height.fractionOf(Window.resolution.height) / 2f

    GraphicsManager.render(sprite, x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), u1, v1, u2, v2)
  }
}