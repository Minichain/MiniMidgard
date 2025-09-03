package entities

import Vector3

abstract class Entity(
  var worldCoordinates: Vector3
) {

  var cameraCoordinates: Vector3 = worldCoordinates.toCameraCoordinates()
  var frameIteration: Float = Math.random().toFloat() * 10f

  open fun update(timeElapsedMillis: Long) {
    cameraCoordinates = worldCoordinates.toCameraCoordinates()
    frameIteration += 10f * timeElapsedMillis.toFloat() / 1000f
  }

  abstract fun render()

  open fun isCloseToCamera(): Boolean {
    return cameraCoordinates.module() < 2000f
  }
}