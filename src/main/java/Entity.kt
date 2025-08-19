abstract class Entity(
  var worldCoordinates: DoubleArray
) {

  var cameraCoordinates: DoubleArray = worldCoordinates.toCameraCoordinates()
  var frameIteration: Float = Math.random().toFloat() * 10f

  open fun update(timeElapsedMillis: Long) {
    cameraCoordinates = worldCoordinates.toCameraCoordinates()
    frameIteration += 10f * timeElapsedMillis.toFloat() / 1000f
  }

  abstract fun render()
}