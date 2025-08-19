
class Poring(
  coordinates: DoubleArray = randomCoordinates()
) : Entity(coordinates) {

  enum class EnemyState {
    Standing,
    Walking
  }

  private var state = EnemyState.Standing

  private val sprite = PoringSprite()
  private var movementVector: DoubleArray = zeroCoordinates()
  private var orientation: Orientation = Orientation.Down

  override fun update(timeElapsedMillis: Long) {
    val speed = 0.5

    if (Math.random() < 0.001) {
      if (state == EnemyState.Standing) {
        state = EnemyState.Walking
        movementVector = randomCoordinates()
        frameIteration = 0f
        SoundManager.playSound(SoundManager.poringEffect01)
      } else if (state == EnemyState.Walking) {
        frameIteration = 0f
        state = EnemyState.Standing
        movementVector = zeroCoordinates()
      }
    }

    val isMoving = movementVector.module() > 0

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
    super.update(timeElapsedMillis)
  }

  override fun render() {
    sprite.render(
      (cameraCoordinates[0] - (Parameters.resolution.width / 2f)) / Parameters.resolution.width,
      (cameraCoordinates[1] - (Parameters.resolution.height / 2f)) / Parameters.resolution.height,
      state,
      frameIteration.toInt(),
      orientation
    )
  }
}