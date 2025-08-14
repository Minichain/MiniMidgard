class Poring(
  coordinates: DoubleArray = randomCoordinates()
) : Entity(coordinates) {

  enum class EnemyState {
    Standing,
    Walking
  }

  private var playerState = EnemyState.Standing

  private val sprite = PoringSprite()

  override fun update(timeElapsedMillis: Long) {
    super.update(timeElapsedMillis)
  }

  override fun render() {
    sprite.render(
      cameraCoordinates[0] / Parameters.resolution.width,
      cameraCoordinates[1] / Parameters.resolution.height,
      playerState,
      frameIteration.toInt()
    )
  }
}