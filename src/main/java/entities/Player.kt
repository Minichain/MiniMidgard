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
  fun isMoving(): Boolean = movementVector.module() > 0f
  private var facingVector = Vector3(0f, 0f, 1f)
  private var orientation: Orientation = Orientation.Down
  private var moveToTargetPin: Vector3? = null

  override fun update(timeElapsedMillis: Long) {
    val speed = 0.2f
    val distanceToMove = speed * timeElapsedMillis.toFloat()

    movementVector = Vector3(0f, 0f, 0f)

    val cameraFacingVector2d = Vector3(Camera.cameraFacingVector.x, 0f, Camera.cameraFacingVector.z).normalized()
    val cameraFacingVectorLeft2d = Vector3(Camera.cameraFacingVector.z, 0f, -Camera.cameraFacingVector.x).normalized()

    if (!InputListener.sitting) {
      if (InputListener.movingUp) movementVector += cameraFacingVector2d
      if (InputListener.movingLeft) movementVector += cameraFacingVectorLeft2d
      if (InputListener.movingDown) movementVector -= cameraFacingVector2d
      if (InputListener.movingRight) movementVector -= cameraFacingVectorLeft2d
    }

    if (!isMoving()) {
      moveToTargetPin?.let { moveToTargetPin ->
        movementVector = moveToTargetPin - worldCoordinates
        if (movementVector.module() < 10f) movementVector = Vector3()
      }
    }

    movementVector = movementVector.normalized()
    if (isMoving()) {
      facingVector = movementVector
    }
    state = if (isMoving()) {
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

    moveToTargetPin?.toCameraCoordinates()?.let {
      headSprite.render(
        it,
        orientation
      )
    }

  }

  fun setMoveToTargetPin(pin: Vector3) {
    moveToTargetPin = pin
  }

  //TODO Debug
  private var timeElapsedSinceLastPrint = 0L
  private fun printVectors(timeElapsed: Long) {
    timeElapsedSinceLastPrint += timeElapsed
    if (timeElapsedSinceLastPrint > 5000L) {
      timeElapsedSinceLastPrint = 0L
      println("Player moveToTargetPin: $moveToTargetPin")
    }
  }
}