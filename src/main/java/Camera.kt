object Camera {

  var position = zeroCoordinates()
    private set
  var target = zeroCoordinates()
    private set
  var direction = zeroCoordinates()
    private set
  var zoom: Double = 2.0
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
      this[0] = Player.worldCoordinates[0] - position[0]
      this[1] = Player.worldCoordinates[1] - position[1]
    }
    val cameraSpeed = cameraVelocityVector.module() * followSpeed * timeElapsed
    cameraVelocityVector = cameraVelocityVector.normalizeVector()
    this.position[0] = position[0] + (cameraVelocityVector[0] * cameraSpeed)
    this.position[1] = position[1] + (cameraVelocityVector[1] * cameraSpeed)
  }

  private fun updateVectors() {
    this.target = Player.worldCoordinates
    this.direction = this.position.minus(this.target).normalizeVector()
  }
}
