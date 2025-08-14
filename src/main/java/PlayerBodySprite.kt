class PlayerBodySprite : Sprite() {

  private val sprite: Texture = Texture.loadTexture("src/main/resources/sprites/body_male_novice.png")
  
  enum class PlayerBodySpriteState {
    Standing,
    Walking,
    Sitting
  }

  private fun getSpriteXOffset(state: PlayerBodySpriteState): Int =
    when (state) {
      PlayerBodySpriteState.Standing -> 0
      PlayerBodySpriteState.Walking -> 10
      PlayerBodySpriteState.Sitting -> 232
    }

  private fun getSpriteYOffset(state: PlayerBodySpriteState): Int =
    when (state) {
      PlayerBodySpriteState.Standing -> 0
      PlayerBodySpriteState.Walking -> 94
      PlayerBodySpriteState.Sitting -> 0
    }

  private fun getSpriteWidth(state: PlayerBodySpriteState): Int =
    when (state) {
      PlayerBodySpriteState.Standing -> 46
      PlayerBodySpriteState.Walking -> 48
      PlayerBodySpriteState.Sitting -> 52
    }

  private fun getSpriteHeight(state: PlayerBodySpriteState): Int =
    when (state) {
      PlayerBodySpriteState.Standing -> 96
      PlayerBodySpriteState.Walking -> 96
      PlayerBodySpriteState.Sitting -> 96
    }

  private val size: Float = 4f

  private var spriteFrame = 0

  private fun getSpriteCount(state: PlayerBodySpriteState): Int =
    when (state) {
      PlayerBodySpriteState.Standing -> 5
      PlayerBodySpriteState.Walking -> 8
      PlayerBodySpriteState.Sitting -> 5
    }

  fun render(x: Float, y: Float, state: PlayerBodySpriteState) {

    spriteFrame = (spriteFrame + 1) % getSpriteCount(state)

    val u1 = getSpriteXOffset(state).fractionOf(sprite.height) + spriteFrame.toFloat() * getSpriteWidth(state).fractionOf(sprite.width)
    val v2 = 1f - getSpriteYOffset(state).fractionOf(sprite.height)
    val v1 = v2 - getSpriteHeight(state).fractionOf(sprite.height)
    val u2 = u1 + getSpriteWidth(state).fractionOf(sprite.width)

    val x1 = x - size * getSpriteWidth(state).fractionOf(Window.width) / 2f
    val y1 = y - size * getSpriteHeight(state).fractionOf(Window.height) / 2f
    val x2 = x + size * getSpriteWidth(state).fractionOf(Window.width) / 2f
    val y2 = y + size * getSpriteHeight(state).fractionOf(Window.height) / 2f

    render(sprite, x1, y1, x2, y2, u1, v1, u2, v2)
  }
}