class PoringSprite : Sprite() {

  private val sprite: Texture = Texture.loadTexture("src/main/resources/sprites/poring.png")

  enum class PoringSpriteState {
    Standing,
    Walking
  }

  private fun getSpriteXOffset(state: PoringSpriteState): Int =
    when (state) {
      PoringSpriteState.Standing -> 0
      PoringSpriteState.Walking -> 0
    }

  private fun getSpriteYOffset(state: PoringSpriteState): Int =
    when (state) {
      PoringSpriteState.Standing -> 0
      PoringSpriteState.Walking -> 0
    }

  private fun getSpriteWidth(state: PoringSpriteState): Int =
    when (state) {
      PoringSpriteState.Standing -> 60
      PoringSpriteState.Walking -> 60
    }

  private fun getSpriteHeight(state: PoringSpriteState): Int =
    when (state) {
      PoringSpriteState.Standing -> 58
      PoringSpriteState.Walking -> 58
    }

  private val size: Float = 4f

  private var spriteFrame = 0

  private fun getSpriteCount(state: PoringSpriteState): Int =
    when (state) {
      PoringSpriteState.Standing -> 4
      PoringSpriteState.Walking -> 4
    }

  fun render(x: Float, y: Float, state: PoringSpriteState) {
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