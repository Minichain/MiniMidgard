
class Poring(
  coordinates: DoubleArray = randomCoordinates(xRange = -2500..2500, zRange = -2500..2500)
) : Entity(coordinates) {

  enum class EnemyState {
    Standing,
    Walking
  }

  private var state = EnemyState.Standing

  private val sprite = PoringSprite()
  private var movementVector: DoubleArray = doubleArrayOf(0.0, 0.0, 0.0)
  private var facingVector: DoubleArray = doubleArrayOf(0.0, 0.0, 1.0)
  private var orientation: Orientation = Orientation.Down

  override fun update(timeElapsedMillis: Long) {
    val speed = 0.5

    if (Math.random() < 0.001) {
      if (state == EnemyState.Standing) {
        state = EnemyState.Walking
        movementVector = randomCoordinates(xRange = -250..250, yRange = 0..0, zRange = -250..250)
        frameIteration = 0f
        SoundManager.playSound(SoundManager.poringEffect01)
      } else if (state == EnemyState.Walking) {
        frameIteration = 0f
        state = EnemyState.Standing
        movementVector = doubleArrayOf(0.0, 0.0, 0.0)
      }
    }

    val isMoving = movementVector.module() > 0
    if (isMoving) {
      facingVector = movementVector
    }

    orientation = Orientation.fromVectors(facingVector, Camera.cameraDirection)

    movementVector = movementVector.normalizeVector()

    worldCoordinates = doubleArrayOf(
      worldCoordinates[0] + (movementVector[0] * speed),
      worldCoordinates[1] + (movementVector[1] * speed),
      worldCoordinates[2] + (movementVector[2] * speed)
    )
    super.update(timeElapsedMillis)
  }

  override fun render() {
    sprite.render(
      worldCoordinates,
      state,
      frameIteration.toInt(),
      orientation
    )
  }
}