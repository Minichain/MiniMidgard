object Camera {

  var cameraPosition = zeroCoordinates()
    private set
  var cameraTarget = zeroCoordinates()
    private set
  var cameraDirection = zeroCoordinates()
    private set
  var cameraRight = zeroCoordinates()
    private set
  var cameraUp = zeroCoordinates()
    private set
  var zoom: Double = 4.0
    private set
  private var minZoom: Double = 1.0
  private var maxZoom: Double = 8.0

  /**
   * Amount of pixels we are able to see in the X axis.
   */
  fun getWidth(): Double = Parameters.resolution.width.toDouble() / zoom

  /**
   * Amount of pixels we are able to see in the Y axis.
   */
  fun getHeight(): Double = Parameters.resolution.height.toDouble() / zoom

  fun setZoom(zoom: Double) {
    if (zoom <= minZoom) Camera.zoom = minZoom
    else if (zoom >= maxZoom) Camera.zoom = maxZoom
    else Camera.zoom = zoom
  }

  fun increaseZoom() {
    setZoom(zoom + 1.0 / zoom)
  }

  fun decreaseZoom() {
    setZoom(zoom - 1.0 / zoom)
  }

  fun update(timeElapsed: Long) {
    updateVectors()
    val followSpeed = 0.0015 * zoom
    var cameraVelocityVector = DoubleArray(2).apply {
      this[0] = Player.worldCoordinates[0] - cameraPosition[0]
      this[1] = Player.worldCoordinates[1] - cameraPosition[1]
    }
    val cameraSpeed = cameraVelocityVector.module() * followSpeed * timeElapsed
    cameraVelocityVector = cameraVelocityVector.normalizeVector()
    cameraPosition[0] = cameraPosition[0] + (cameraVelocityVector[0] * cameraSpeed)
    cameraPosition[1] = cameraPosition[1] + (cameraVelocityVector[1] * cameraSpeed)
  }

  private fun updateVectors() {
    cameraTarget = Player.worldCoordinates
    cameraDirection = cameraPosition.minus(cameraTarget).normalizeVector()
    val up = DoubleArray(3).apply {
      this[0] = 0.0
      this[1] = 1.0
      this[2] = 0.0
    }
    cameraRight = up.cross(cameraDirection).normalizeVector()
    cameraUp = cameraDirection.cross(cameraRight)
  }
}
