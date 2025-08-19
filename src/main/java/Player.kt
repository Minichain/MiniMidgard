data object Player : Entity(zeroCoordinates()) {

  enum class PlayerState {
    Standing,
    Walking,
    Sitting
  }

  private var playerState = PlayerState.Walking

  private val bodySprite = BodyMaleNoviceSprite()
  private val headSprite = PlayerHeadSprite()
  private var headWorldCoordinates = zeroCoordinates()
  private var headCameraCoordinates = zeroCoordinates()
  private var movementVector: DoubleArray = zeroCoordinates()
  private var orientation: Orientation = Orientation.Down

  override fun update(timeElapsedMillis: Long) {
    val speed = 2.0

    movementVector = zeroCoordinates()

    if (!InputListener.sitting) {
      if (InputListener.movingUp) movementVector[1] += 1
      if (InputListener.movingLeft) movementVector[0] -= 1
      if (InputListener.movingDown) movementVector[1] -= 1
      if (InputListener.movingRight) movementVector[0] += 1
    }

    val isMoving = movementVector.module() > 0
    playerState = if (isMoving) {
      PlayerState.Walking
    } else {
      if (InputListener.sitting) {
        PlayerState.Sitting
      } else {
        PlayerState.Standing
      }
    }

    if (isMoving) {
      Orientation.from2dVector(movementVector[0], movementVector[1]).let {
        orientation = it
      }
    }

    movementVector = movementVector.normalizeVector()

    worldCoordinates = DoubleArray(3).apply {
      this[0] = worldCoordinates[0] + (movementVector[0] * speed)
      this[1] = worldCoordinates[1] + (movementVector[1] * speed)
      this[2] = 0.0
    }
    headWorldCoordinates = DoubleArray(3).apply {
      this[0] = worldCoordinates[0] + 2
      this[1] = worldCoordinates[1] + 39
      this[2] = 0.0
    }
    headCameraCoordinates = headWorldCoordinates.toCameraCoordinates()
    super.update(timeElapsedMillis)
  }

  override fun render() {
    bodySprite.render(
      cameraCoordinates[0] / Parameters.resolution.width,
      cameraCoordinates[1] / Parameters.resolution.height,
      playerState,
      frameIteration.toInt(),
      orientation
    )
    headSprite.render(
      headCameraCoordinates[0] / Parameters.resolution.width,
      headCameraCoordinates[1] / Parameters.resolution.height,
      orientation
    )
  }
}