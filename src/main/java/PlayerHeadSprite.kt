
class PlayerHeadSprite : Sprite() {

  companion object {
    private val sprite: Texture = Texture.loadTexture("src/main/resources/sprites/head_male_black.png")
  }

  private enum class SpriteOrientation {
    Down,
    DownLeft,
    Left,
    UpLeft,
    Up
  }

  private fun getSpriteXOffset(orientation: SpriteOrientation): Int =
    when (orientation) {
      SpriteOrientation.Down -> 8
      SpriteOrientation.DownLeft -> 58
      SpriteOrientation.Left -> 108
      SpriteOrientation.UpLeft -> 158
      SpriteOrientation.Up -> 208
    }

  private fun getSpriteYOffset(orientation: SpriteOrientation): Int =
    when (orientation) {
      SpriteOrientation.Down -> 8
      SpriteOrientation.DownLeft -> 8
      SpriteOrientation.Left -> 8
      SpriteOrientation.UpLeft -> 8
      SpriteOrientation.Up -> 8
    }


  private fun getSpriteWidth(): Int = 32

  private fun getSpriteHeight(): Int = 32

  fun render(x: Double, y: Double, orientation: Orientation) {

    val spriteOrientation = when (orientation) {
      Orientation.Up -> SpriteOrientation.Up
      Orientation.UpRight -> SpriteOrientation.UpLeft
      Orientation.Right -> SpriteOrientation.Left
      Orientation.DownRight -> SpriteOrientation.DownLeft
      Orientation.Down -> SpriteOrientation.Down
      Orientation.DownLeft -> SpriteOrientation.DownLeft
      Orientation.Left -> SpriteOrientation.Left
      Orientation.UpLeft -> SpriteOrientation.UpLeft
    }

    val xOffset = getSpriteXOffset(spriteOrientation)
    val yOffset = getSpriteYOffset(spriteOrientation)

    val u1 = xOffset.fractionOf(sprite.width)
    val v2 = 1f - yOffset.fractionOf(sprite.height)
    val v1 = v2 - getSpriteHeight().fractionOf(sprite.height)
    val u2 = u1 + getSpriteWidth().fractionOf(sprite.width)

    val x1 = x - Camera.zoom * getSpriteWidth().fractionOf(Window.resolution.width) / 2f
    val y1 = y - Camera.zoom * getSpriteHeight().fractionOf(Window.resolution.height) / 2f
    val x2 = x + Camera.zoom * getSpriteWidth().fractionOf(Window.resolution.width) / 2f
    val y2 = y + Camera.zoom * getSpriteHeight().fractionOf(Window.resolution.height) / 2f

    if (orientation == Orientation.UpRight || orientation == Orientation.Right || orientation == Orientation.DownRight) {
      GraphicsManager.render(sprite, x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), u2, v1, u1, v2)
    } else {
      GraphicsManager.render(sprite, x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), u1, v1, u2, v2)
    }
  }
}