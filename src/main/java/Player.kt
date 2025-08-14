data object Player : Entity(zeroCoordinates()) {

  enum class PlayerState {
    Standing,
    Walking,
    Sitting
  }

  private var playerState = PlayerState.Walking

  private val bodySprite = BodyMaleNoviceSprite()
  private val headSprite = PlayerHeadSprite()

  override fun update(timeElapsedMillis: Long) {
    val speed = 2.0
    val movementVector = DoubleArray(2).apply {
      this[0] = 0.0
      this[1] = 0.0
    }

    if (InputListener.movingUp) movementVector[1] += 1
    if (InputListener.movingLeft) movementVector[0] -= 1
    if (InputListener.movingDown) movementVector[1] -= 1
    if (InputListener.movingRight) movementVector[0] += 1

    playerState = if (movementVector.module() > 0) {
      PlayerState.Walking
    } else {
      if (InputListener.sitting) {
        PlayerState.Sitting
      } else {
        PlayerState.Standing
      }
    }

    movementVector.normalizeVector().let { movementVectorNormalized ->
      worldCoordinates = DoubleArray(3).apply {
        this[0] = worldCoordinates[0] + (movementVectorNormalized[0] * speed)
        this[1] = worldCoordinates[1] + (movementVectorNormalized[1] * speed)
        this[2] = 0.0
      }
    }
    super.update(timeElapsedMillis)
  }

  override fun render() {
    bodySprite.render(
      cameraCoordinates[0] / Parameters.resolution.width,
      cameraCoordinates[1] / Parameters.resolution.height,
      playerState,
      frameIteration.toInt()
    )
    headSprite.render(
      cameraCoordinates[0] / Parameters.resolution.width,
      (cameraCoordinates[1] + 62) / Parameters.resolution.height  //TODO
    )
  }
}