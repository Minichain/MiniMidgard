package entities

import BodyMaleNoviceSprite
import HeadMaleSprite
import Orientation
import Vector3

data object Player : Entity(Vector3(0f, 0f, 0f)) {

  enum class PlayerState {
    Standing,
    Walking,
    Sitting
  }

  private var state = PlayerState.Walking

  private val bodySprite = BodyMaleNoviceSprite()
  private val headSprite = HeadMaleSprite()
  private var movementVector = Vector3(0f, 0f, 0f)
  private var facingVector = Vector3(0f, 0f, 1f)
  private var orientation: Orientation = Orientation.Down

  override fun update(timeElapsedMillis: Long) {
    val speed = 0.2f
    val distanceToMove = speed * timeElapsedMillis.toFloat()

    movementVector = Vector3(0f, 0f, 0f)

    if (!InputListener.sitting) {
      if (InputListener.movingUp) movementVector.values[2] -= 1f
      if (InputListener.movingLeft) movementVector.values[0] -= 1f
      if (InputListener.movingDown) movementVector.values[2] += 1f
      if (InputListener.movingRight) movementVector.values[0] += 1f
    }

    movementVector = movementVector.normalized()
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

    orientation = Orientation.fromVectors(facingVector, Camera.cameraFacingVector)

    worldCoordinates = Vector3(
      worldCoordinates.x + (movementVector.x * distanceToMove),
      worldCoordinates.y + (movementVector.y * distanceToMove),
      worldCoordinates.z + (movementVector.z * distanceToMove)
    )
    printVectors(timeElapsedMillis)
    super.update(timeElapsedMillis)
  }

  override fun render() {
    bodySprite.render(
      cameraCoordinates,
      state,
      frameIteration.toInt(),
      orientation
    )
    headSprite.render(
      Vector3(cameraCoordinates.x, cameraCoordinates.y + 60, cameraCoordinates.z + 1),
      orientation
    )
  }

  //TODO Debug
  private var timeElapsedSinceLastPrint = 0L
  private fun printVectors(timeElapsed: Long) {
    timeElapsedSinceLastPrint += timeElapsed
    if (timeElapsedSinceLastPrint > 5000L) {
      timeElapsedSinceLastPrint = 0L
//      println("Vectors updated")
//      println("movementVector: $movementVector")
    }
  }
}