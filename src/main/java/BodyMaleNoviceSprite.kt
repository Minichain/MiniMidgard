class BodyMaleNoviceSprite : Sprite() {

  companion object {
    private val spriteSheet: Texture = Texture.loadTexture("src/main/resources/sprites/body_male_novice.png")
  }

  private enum class Animation {
    Standing,
    Walking,
    Sitting
  }

  private enum class Orientation {
    Down,
    DownLeft,
    Left,
    UpLeft,
    Up
  }

  private fun getSpriteXOffset(animation: Animation, orientation: Orientation, frame: Int): Int =
    when (animation) {
      Animation.Standing -> {
        when (orientation) {
          Orientation.Down -> 10
          Orientation.DownLeft -> TODO()
          Orientation.Left -> TODO()
          Orientation.UpLeft -> TODO()
          Orientation.Up -> TODO()
        }
      }
      Animation.Walking -> {
        when (orientation) {
          Orientation.Down -> {
            10 + frame * 10 + frame * 38
          }
          Orientation.DownLeft -> TODO()
          Orientation.Left -> TODO()
          Orientation.UpLeft -> TODO()
          Orientation.Up -> TODO()
        }
      }
      Animation.Sitting -> {
        when (orientation) {
          Orientation.Down -> 234
          Orientation.DownLeft -> TODO()
          Orientation.Left -> TODO()
          Orientation.UpLeft -> TODO()
          Orientation.Up -> TODO()
        }
      }
    }

  private fun getSpriteYOffset(animation: Animation, orientation: Orientation, frame: Int): Int =
    when (animation) {
      Animation.Standing -> {
        when (orientation) {
          Orientation.Down -> 10
          Orientation.DownLeft -> TODO()
          Orientation.Left -> TODO()
          Orientation.UpLeft -> TODO()
          Orientation.Up -> TODO()
        }
      }
      Animation.Walking -> {
        when (orientation) {
          Orientation.Down -> 112
          Orientation.DownLeft -> TODO()
          Orientation.Left -> TODO()
          Orientation.UpLeft -> TODO()
          Orientation.Up -> TODO()
        }
      }
      Animation.Sitting -> {
        when (orientation) {
          Orientation.Down -> 10
          Orientation.DownLeft -> TODO()
          Orientation.Left -> TODO()
          Orientation.UpLeft -> TODO()
          Orientation.Up -> TODO()
        }
      }
    }

  private fun getSpriteWidth(animation: Animation): Int =
    when (animation) {
      Animation.Standing -> 40
      Animation.Walking -> 40
      Animation.Sitting -> 54
    }

  private fun getSpriteHeight(animation: Animation): Int =
    when (animation) {
      Animation.Standing -> 74
      Animation.Walking -> 74
      Animation.Sitting -> 74
    }

  private fun getSpriteCount(animation: Animation, orientation: Orientation): Int =
    when (animation) {
      Animation.Standing -> {
        when (orientation) {
          Orientation.Down -> 1
          Orientation.DownLeft -> 1
          Orientation.Left -> 1
          Orientation.UpLeft -> 1
          Orientation.Up -> 1
        }
      }
      Animation.Walking -> {
        when (orientation) {
          Orientation.Down -> 8
          Orientation.DownLeft -> 8
          Orientation.Left -> 8
          Orientation.UpLeft -> 8
          Orientation.Up -> 8
        }
      }
      Animation.Sitting -> {
        when (orientation) {
          Orientation.Down -> 1
          Orientation.DownLeft -> 1
          Orientation.Left -> 1
          Orientation.UpLeft -> 1
          Orientation.Up -> 1
        }
      }
    }

  private fun Player.PlayerState.toSpriteAnimation() =
    when (this) {
      Player.PlayerState.Standing -> Animation.Standing
      Player.PlayerState.Walking -> Animation.Walking
      Player.PlayerState.Sitting -> Animation.Sitting
    }

  fun render(x: Double, y: Double, playerState: Player.PlayerState, frameIteration: Int) {

    val orientation = Orientation.Down
    val spriteAnimation = playerState.toSpriteAnimation()
    val frame = frameIteration % getSpriteCount(spriteAnimation, orientation)

    val xOffset = getSpriteXOffset(spriteAnimation, orientation, frame)
    val yOffset = getSpriteYOffset(spriteAnimation, orientation, frame)

    val u1 = xOffset.fractionOf(spriteSheet.width)
    val v2 = 1f - yOffset.fractionOf(spriteSheet.height)
    val v1 = v2 - getSpriteHeight(spriteAnimation).fractionOf(spriteSheet.height)
    val u2 = u1 + getSpriteWidth(spriteAnimation).fractionOf(spriteSheet.width)

    val x1 = x - Camera.zoom * getSpriteWidth(spriteAnimation).fractionOf(Window.resolution.width) / 2f
    val y1 = y - Camera.zoom * getSpriteHeight(spriteAnimation).fractionOf(Window.resolution.height) / 2f
    val x2 = x + Camera.zoom * getSpriteWidth(spriteAnimation).fractionOf(Window.resolution.width) / 2f
    val y2 = y + Camera.zoom * getSpriteHeight(spriteAnimation).fractionOf(Window.resolution.height) / 2f

    GraphicsManager.render(spriteSheet, x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), u1, v1, u2, v2)
  }
}