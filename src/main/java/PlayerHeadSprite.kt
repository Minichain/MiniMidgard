
class PlayerHeadSprite : Sprite() {

  companion object {
    private val spriteSheet: Texture = Texture.loadTexture("src/main/resources/sprites/head_male_black.png")
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

    val u1 = xOffset.toFloat() / spriteSheet.width.toFloat()
    val v2 = 1f - yOffset.toFloat() / spriteSheet.height.toFloat()
    val v1 = v2 - getSpriteHeight() / spriteSheet.height.toFloat()
    val u2 = u1 + getSpriteWidth() / spriteSheet.width.toFloat()

    val x1 = x - Camera.zoom * (getSpriteWidth().toFloat() / Window.resolution.width.toFloat()) / 2f
    val y1 = y - Camera.zoom * (getSpriteHeight().toFloat() / Window.resolution.height.toFloat()) / 2f
    val x2 = x + Camera.zoom * (getSpriteWidth().toFloat() / Window.resolution.width.toFloat()) / 2f
    val y2 = y + Camera.zoom * (getSpriteHeight().toFloat() / Window.resolution.height.toFloat()) / 2f

    if (orientation == Orientation.UpRight || orientation == Orientation.Right || orientation == Orientation.DownRight) {
      GraphicsManager.render(spriteSheet, x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), u2, v1, u1, v2)
    } else {
      GraphicsManager.render(spriteSheet, x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), u1, v1, u2, v2)
    }
  }
}