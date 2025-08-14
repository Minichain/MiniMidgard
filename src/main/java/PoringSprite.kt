class PoringSprite : Sprite() {

  companion object {
    private val sprite: Texture = Texture.loadTexture("src/main/resources/sprites/poring.png")
  }

  private enum class State {
    Standing,
    Walking
  }

  private fun getSpriteXOffset(state: State): Int =
    when (state) {
      State.Standing -> 0
      State.Walking -> 0
    }

  private fun getSpriteYOffset(state: State): Int =
    when (state) {
      State.Standing -> 0
      State.Walking -> 0
    }

  private fun getSpriteWidth(state: State): Int =
    when (state) {
      State.Standing -> 60
      State.Walking -> 60
    }

  private fun getSpriteHeight(state: State): Int =
    when (state) {
      State.Standing -> 58
      State.Walking -> 58
    }

  private fun getSpriteCount(state: State): Int =
    when (state) {
      State.Standing -> 4
      State.Walking -> 4
    }

  private fun Poring.EnemyState.toSpriteAnimation() =
    when (this) {
      Poring.EnemyState.Standing -> State.Standing
      Poring.EnemyState.Walking -> State.Walking
    }

  fun render(x: Double, y: Double, enemyState: Poring.EnemyState, frameIteration: Int) {

    val spriteAnimation = enemyState.toSpriteAnimation()

    val u1 = getSpriteXOffset(spriteAnimation).fractionOf(sprite.height) + (frameIteration % getSpriteCount(spriteAnimation)).toFloat() * getSpriteWidth(spriteAnimation).fractionOf(sprite.width)
    val v2 = 1f - getSpriteYOffset(spriteAnimation).fractionOf(sprite.height)
    val v1 = v2 - getSpriteHeight(spriteAnimation).fractionOf(sprite.height)
    val u2 = u1 + getSpriteWidth(spriteAnimation).fractionOf(sprite.width)

    val x1 = x - Camera.zoom * getSpriteWidth(spriteAnimation).fractionOf(Window.resolution.width) / 2f
    val y1 = y - Camera.zoom * getSpriteHeight(spriteAnimation).fractionOf(Window.resolution.height) / 2f
    val x2 = x + Camera.zoom * getSpriteWidth(spriteAnimation).fractionOf(Window.resolution.width) / 2f
    val y2 = y + Camera.zoom * getSpriteHeight(spriteAnimation).fractionOf(Window.resolution.height) / 2f

    GraphicsManager.render(sprite, x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), u1, v1, u2, v2)
  }
}