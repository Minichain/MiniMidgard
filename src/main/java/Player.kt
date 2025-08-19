
data object Player : Entity(doubleArrayOf(0.0, 0.0, 0.0)) {

  enum class PlayerState {
    Standing,
    Walking,
    Sitting
  }

  private var state = PlayerState.Walking

  private val bodySprite = BodyMaleNoviceSprite()
  private val headSprite = HeadMaleSprite()
  private var headWorldCoordinates = doubleArrayOf(0.0, 0.0, 0.0)
  private var movementVector: DoubleArray = doubleArrayOf(0.0, 0.0, 0.0)
  private var facingVector: DoubleArray = doubleArrayOf(0.0, 0.0, 1.0)
  private var orientation: Orientation = Orientation.Down

  override fun update(timeElapsedMillis: Long) {
    val speed = 2.0

    movementVector = doubleArrayOf(0.0, 0.0, 0.0)

    if (!InputListener.sitting) {
      if (InputListener.movingUp) movementVector[2] += 1
      if (InputListener.movingLeft) movementVector[0] += 1
      if (InputListener.movingDown) movementVector[2] -= 1
      if (InputListener.movingRight) movementVector[0] -= 1
    }

    movementVector = movementVector.normalizeVector()
    val isMoving = movementVector.module() > 0
    if (isMoving) {
      facingVector = movementVector
    }
    state = if (isMoving) {
      PlayerState.Walking
    } else {
      if (InputListener.sitting) {
        PlayerState.Sitting
      } else {
        PlayerState.Standing
      }
    }

    orientation = Orientation.fromVectors(facingVector, Camera.cameraDirection)

    worldCoordinates = doubleArrayOf(
      worldCoordinates[0] + (movementVector[0] * speed),
      worldCoordinates[1] + (movementVector[1] * speed),
      worldCoordinates[2] + (movementVector[2] * speed)
    )
    headWorldCoordinates = doubleArrayOf(
      worldCoordinates[0],
      worldCoordinates[1] + 75,
      worldCoordinates[2],
    )
    super.update(timeElapsedMillis)
  }

  override fun render() {
    bodySprite.render(
      worldCoordinates,
      state,
      frameIteration.toInt(),
      orientation
    )
    headSprite.render(
      headWorldCoordinates,
      orientation
    )
  }
}