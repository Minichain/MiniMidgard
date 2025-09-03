package entities

import Orientation
import PoringSprite
import Vector3
import sound.SoundManager

class Poring(
  coordinates: Vector3 = Vector3.random(xRange = -2500..2500, zRange = -2500..2500)
) : Entity(coordinates) {

  enum class EnemyState {
    Standing,
    Walking
  }

  private var state = EnemyState.Standing

  private val sprite = PoringSprite()
  private var movementVector = Vector3(0f, 0f, 0f)
  private var facingVector = Vector3(0f, 0f, 1f)
  private var orientation: Orientation = Orientation.Down

  override fun update(timeElapsedMillis: Long) {
    val speed = 0.05f
    val distanceToMove = speed * timeElapsedMillis.toFloat()

    var playSound = false

    if (Math.random() < 0.001) {
      if (state == EnemyState.Standing) {
        state = EnemyState.Walking
        movementVector = Vector3.random(xRange = -250..250, yRange = 0..0, zRange = -250..250)
        frameIteration = 0f
        playSound = true
      } else if (state == EnemyState.Walking) {
        frameIteration = 0f
        state = EnemyState.Standing
        movementVector = Vector3(0f, 0f, 0f)
      }
    }

    val isMoving = movementVector.module() > 0
    if (isMoving) {
      facingVector = movementVector
    }

    orientation = Orientation.fromVectors(facingVector, Camera.cameraFacingVector)

    movementVector = movementVector.normalized()

    worldCoordinates = Vector3(
      worldCoordinates.x + (movementVector.x * distanceToMove),
      worldCoordinates.y + (movementVector.y * distanceToMove),
      worldCoordinates.z + (movementVector.z * distanceToMove)
    )
    super.update(timeElapsedMillis)
    if (playSound && isCloseToCamera()) {
      SoundManager.playSound(SoundManager.poringEffect01)
    }
  }

  override fun render() {
    if (!isCloseToCamera()) return
    sprite.render(
      cameraCoordinates,
      state,
      frameIteration.toInt(),
      orientation
    )
  }
}