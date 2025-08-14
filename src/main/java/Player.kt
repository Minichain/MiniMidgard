class Player {

  private val coordinates = Coordinates(-0.75f, 0.5f)

//  enum class PlayerState {
//    Standing,
//    Walking,
//    Sitting
//  }
//
//  private var playerState = PlayerState.Walking

  private val bodySprite = PlayerBodySprite()

  fun render() {
    bodySprite.render(coordinates.x, coordinates.y, PlayerBodySprite.PlayerBodySpriteState.Walking)
  }
}