class Poring {

  private val coordinates = Coordinates(-0.5f, 0.5f)

//  enum class EnemyState {
//    Standing,
//    Walking
//  }

  private val sprite = PoringSprite()

  fun render() {
    sprite.render(coordinates.x, coordinates.y, PoringSprite.PoringSpriteState.Standing)
  }
}