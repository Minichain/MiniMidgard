
class PlayerHeadSprite : Sprite() {

  companion object {
    private val sprite: Texture = Texture.loadTexture("src/main/resources/sprites/head_male_black.png")
  }

  private fun getSpriteXOffset(): Int = 8

  private fun getSpriteYOffset(): Int = 8

  private fun getSpriteWidth(): Int = 32

  private fun getSpriteHeight(): Int = 32

  fun render(x: Double, y: Double) {

    val u1 = getSpriteXOffset().fractionOf(sprite.height)
    val v2 = 1f - getSpriteYOffset().fractionOf(sprite.height)
    val v1 = v2 - getSpriteHeight().fractionOf(sprite.height)
    val u2 = u1 + getSpriteWidth().fractionOf(sprite.width)

    val x1 = x - Camera.zoom * getSpriteWidth().fractionOf(Window.resolution.width) / 2f
    val y1 = y - Camera.zoom * getSpriteHeight().fractionOf(Window.resolution.height) / 2f
    val x2 = x + Camera.zoom * getSpriteWidth().fractionOf(Window.resolution.width) / 2f
    val y2 = y + Camera.zoom * getSpriteHeight().fractionOf(Window.resolution.height) / 2f

    GraphicsManager.render(sprite, x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), u1, v1, u2, v2)
  }
}