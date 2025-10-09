import entities.Poring

class PoringSprite : Sprite() {

  companion object {
    private val spriteSheet: Texture = Texture.loadTexture("src/main/resources/sprites/poring.png")
  }

  private enum class State {
    Standing,
    Walking
  }

  private enum class SpriteOrientation {
    DownLeft,
    UpLeft
  }

  private fun getSpriteXOffset(state: State, orientation: SpriteOrientation, frame: Int): Int =
    when (state) {
      State.Standing -> {
        when (orientation) {
          SpriteOrientation.DownLeft -> frame * getSpriteWidth(state, orientation)
          SpriteOrientation.UpLeft -> 244 + frame * getSpriteWidth(state, orientation)
        }
      }
      State.Walking -> {
        when (orientation) {
          SpriteOrientation.DownLeft -> frame * getSpriteWidth(state, orientation)
          SpriteOrientation.UpLeft -> frame * getSpriteWidth(state, orientation)
        }
      }
    }

  private fun getSpriteYOffset(state: State, orientation: SpriteOrientation, frame: Int): Int =
    when (state) {
      State.Standing -> 0
      State.Walking -> {
        when (orientation) {
          SpriteOrientation.DownLeft -> 64
          SpriteOrientation.UpLeft -> 128
        }
      }
    }

  private fun getSpriteWidth(state: State, orientation: SpriteOrientation): Int =
    when (state) {
      State.Standing -> 60
      State.Walking -> {
        when (orientation) {
          SpriteOrientation.DownLeft -> 52
          SpriteOrientation.UpLeft -> 50
        }
      }
    }

  private fun getSpriteHeight(state: State): Int =
    when (state) {
      State.Standing -> 58
      State.Walking -> 58
    }

  private fun getSpriteCount(state: State, orientation: SpriteOrientation): Int =
    when (state) {
      State.Standing -> 4
      State.Walking -> 8
    }

  private fun Poring.EnemyState.toSpriteAnimation() =
    when (this) {
      Poring.EnemyState.Standing -> State.Standing
      Poring.EnemyState.Walking -> State.Walking
    }

  fun render(coordinates: Vector3, enemyState: Poring.EnemyState, frameIteration: Int, orientation: Orientation) {

    val spriteOrientation = when (orientation) {
      Orientation.Up,
      Orientation.UpRight,
      Orientation.UpLeft -> SpriteOrientation.UpLeft
      Orientation.Right,
      Orientation.DownRight,
      Orientation.Left,
      Orientation.Down,
      Orientation.DownLeft -> SpriteOrientation.DownLeft
    }
    val spriteAnimation = enemyState.toSpriteAnimation()
    val frame = frameIteration % getSpriteCount(spriteAnimation, spriteOrientation)

    val xOffset = getSpriteXOffset(spriteAnimation, spriteOrientation, frame)
    val yOffset = getSpriteYOffset(spriteAnimation, spriteOrientation, frame)

    val flipTextureYAxis = orientation == Orientation.UpRight || orientation == Orientation.Right || orientation == Orientation.DownRight

    val u1 = xOffset.toFloat() / spriteSheet.width.toFloat()
    val v2 = 1f - yOffset.toFloat() / spriteSheet.height.toFloat()
    val v1 = v2 - getSpriteHeight(spriteAnimation) / spriteSheet.height.toFloat()
    val u2 = u1 + getSpriteWidth(spriteAnimation, spriteOrientation) / spriteSheet.width.toFloat()

    val spriteWidth = getSpriteWidth(spriteAnimation, spriteOrientation)
    val spriteHeight = getSpriteHeight(spriteAnimation)

    val upVector = Vector3(0f, 1f, 0f) * (spriteHeight.toFloat())
    val rightVector = Vector3(1f, 0f, 0f) * (spriteWidth.toFloat() / 2f)

    val vertex1 = (coordinates + upVector + rightVector) * (1f / Window.resolution.height.toFloat())
    val vertex2 = (coordinates + rightVector) * (1f / Window.resolution.height.toFloat())
    val vertex3 = (coordinates - rightVector) * (1f / Window.resolution.height.toFloat())
    val vertex4 = (coordinates + upVector - rightVector) * (1f / Window.resolution.height.toFloat())

    if (flipTextureYAxis) {
      GraphicsManager.render(spriteSheet, vertex1, vertex2, vertex3, vertex4, u2, v1, u1, v2)
    } else {
      GraphicsManager.render(spriteSheet, vertex1, vertex2, vertex3, vertex4, u1, v1, u2, v2)
    }
  }
}