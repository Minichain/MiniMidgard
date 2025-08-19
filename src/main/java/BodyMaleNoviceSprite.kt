class BodyMaleNoviceSprite : Sprite() {

  companion object {
    private val spriteSheet: Texture = Texture.loadTexture("src/main/resources/sprites/body_male_novice.png")
  }

  private enum class Animation {
    Standing,
    Walking,
    Sitting
  }

  private enum class SpriteOrientation {
    Down,
    DownLeft,
    Left,
    UpLeft,
    Up
  }

  private fun getSpriteXOffset(animation: Animation, spriteOrientation: SpriteOrientation, frame: Int): Int =
    when (animation) {
      Animation.Standing -> {
        when (spriteOrientation) {
          SpriteOrientation.Down -> 10
          SpriteOrientation.DownLeft -> 56
          SpriteOrientation.Left -> 98
          SpriteOrientation.UpLeft -> 142
          SpriteOrientation.Up -> 190
        }
      }
      Animation.Walking -> {
        when (spriteOrientation) {
          SpriteOrientation.Down -> {
            10 + frame * 10 + frame * 38
          }
          SpriteOrientation.DownLeft -> {
            10 + frame * 10 + frame * 38
          }
          SpriteOrientation.Left -> {
            4 + frame * 12 + frame * 38
          }
          SpriteOrientation.UpLeft -> {
            10 + frame * 10 + frame * 38
          }
          SpriteOrientation.Up -> {
            7 + frame * 6 + frame * 38
          }
        }
      }
      Animation.Sitting -> {
        when (spriteOrientation) {
          SpriteOrientation.Down -> 234
          SpriteOrientation.DownLeft -> 292
          SpriteOrientation.Left -> 360
          SpriteOrientation.UpLeft -> 430
          SpriteOrientation.Up -> 494
        }
      }
    }

  private fun getSpriteYOffset(animation: Animation, spriteOrientation: SpriteOrientation, frame: Int): Int =
    when (animation) {
      Animation.Standing -> {
        when (spriteOrientation) {
          SpriteOrientation.Down -> 10
          SpriteOrientation.DownLeft -> 10
          SpriteOrientation.Left -> 10
          SpriteOrientation.UpLeft -> 10
          SpriteOrientation.Up -> 10
        }
      }
      Animation.Walking -> {
        when (spriteOrientation) {
          SpriteOrientation.Down -> 112
          SpriteOrientation.DownLeft -> 212
          SpriteOrientation.Left -> 316
          SpriteOrientation.UpLeft -> 416
          SpriteOrientation.Up -> 514
        }
      }
      Animation.Sitting -> {
        when (spriteOrientation) {
          SpriteOrientation.Down -> 10
          SpriteOrientation.DownLeft -> 10
          SpriteOrientation.Left -> 10
          SpriteOrientation.UpLeft -> 10
          SpriteOrientation.Up -> 10
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

  private fun getSpriteCount(animation: Animation, spriteOrientation: SpriteOrientation): Int =
    when (animation) {
      Animation.Standing -> {
        when (spriteOrientation) {
          SpriteOrientation.Down -> 1
          SpriteOrientation.DownLeft -> 1
          SpriteOrientation.Left -> 1
          SpriteOrientation.UpLeft -> 1
          SpriteOrientation.Up -> 1
        }
      }
      Animation.Walking -> {
        when (spriteOrientation) {
          SpriteOrientation.Down -> 8
          SpriteOrientation.DownLeft -> 8
          SpriteOrientation.Left -> 8
          SpriteOrientation.UpLeft -> 8
          SpriteOrientation.Up -> 8
        }
      }
      Animation.Sitting -> {
        when (spriteOrientation) {
          SpriteOrientation.Down -> 1
          SpriteOrientation.DownLeft -> 1
          SpriteOrientation.Left -> 1
          SpriteOrientation.UpLeft -> 1
          SpriteOrientation.Up -> 1
        }
      }
    }

  private fun Player.PlayerState.toSpriteAnimation() =
    when (this) {
      Player.PlayerState.Standing -> Animation.Standing
      Player.PlayerState.Walking -> Animation.Walking
      Player.PlayerState.Sitting -> Animation.Sitting
    }

  fun render(x: Double, y: Double, playerState: Player.PlayerState, frameIteration: Int, orientation: Orientation) {

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
    val spriteAnimation = playerState.toSpriteAnimation()
    val frame = frameIteration % getSpriteCount(spriteAnimation, spriteOrientation)

    val xOffset = getSpriteXOffset(spriteAnimation, spriteOrientation, frame)
    val yOffset = getSpriteYOffset(spriteAnimation, spriteOrientation, frame)

    val u1 = xOffset.fractionOf(spriteSheet.width)
    val v2 = 1f - yOffset.fractionOf(spriteSheet.height)
    val v1 = v2 - getSpriteHeight(spriteAnimation).fractionOf(spriteSheet.height)
    val u2 = u1 + getSpriteWidth(spriteAnimation).fractionOf(spriteSheet.width)

    val x1 = x - Camera.zoom * getSpriteWidth(spriteAnimation).fractionOf(Window.resolution.width) / 2f
    val y1 = y - Camera.zoom * getSpriteHeight(spriteAnimation).fractionOf(Window.resolution.height) / 2f
    val x2 = x + Camera.zoom * getSpriteWidth(spriteAnimation).fractionOf(Window.resolution.width) / 2f
    val y2 = y + Camera.zoom * getSpriteHeight(spriteAnimation).fractionOf(Window.resolution.height) / 2f

    if (orientation == Orientation.UpRight || orientation == Orientation.Right || orientation == Orientation.DownRight) {
      GraphicsManager.render(spriteSheet, x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), u2, v1, u1, v2)
    } else {
      GraphicsManager.render(spriteSheet, x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), u1, v1, u2, v2)
    }
  }
}