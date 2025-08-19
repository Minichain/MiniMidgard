
fun DoubleArray.toCameraCoordinates(): DoubleArray =
  DoubleArray(3).apply {
    this@apply[0] = (this@toCameraCoordinates[0] - Camera.position[0] + (Camera.getWidth() / 2.0)) * Camera.zoom
    this@apply[1] = (this@toCameraCoordinates[1] - Camera.position[1] + (Camera.getHeight() / 2.0)) * Camera.zoom
    this@apply[2] = 0.0
  }

fun DoubleArray.toWorldCoordinates(): DoubleArray =
  DoubleArray(3).apply {
    this@apply[0] = this@toWorldCoordinates[0] / Camera.zoom + Camera.position[0] - (Camera.getWidth() / 2.0)
    this@apply[1] = this@toWorldCoordinates[1] / Camera.zoom + Camera.position[1] - (Camera.getHeight() / 2.0)
    this@apply[2] = 0.0
  }

fun DoubleArray.minus(coordinates: DoubleArray): DoubleArray =
  DoubleArray(3).apply {
    this@apply[0] = this@minus[0] - coordinates[0]
    this@apply[1] = this@minus[1] - coordinates[1]
    this@apply[2] = this@minus[2] - coordinates[2]
  }

fun DoubleArray.plus(coordinates: DoubleArray): DoubleArray =
  DoubleArray(3).apply {
    this@apply[0] = this@plus[0] + coordinates[0]
    this@apply[1] = this@plus[1] + coordinates[1]
    this@apply[2] = this@plus[2] + coordinates[2]
  }

fun randomCoordinates() =
  DoubleArray(3).apply {
    this[0] = (-1000..1000).random().toDouble()
    this[1] = (-1000..1000).random().toDouble()
    this[2] = (-1000..1000).random().toDouble()
  }

fun zeroCoordinates() =
  DoubleArray(3).apply {
    this[0] = 0.0
    this[1] = 0.0
    this[2] = 0.0
  }
