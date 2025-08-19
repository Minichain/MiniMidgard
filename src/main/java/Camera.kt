object Camera {

  private var cameraShift = doubleArrayOf(0.0, 10.0, 10.0)
  var cameraPosition = doubleArrayOf(0.0, 0.0, 0.0).plus(cameraShift)
    private set
  var cameraTarget = doubleArrayOf(0.0, 0.0, 0.0)
    private set
  var cameraDirection = doubleArrayOf(0.0, 0.0, 0.0)
    private set
  var cameraRight = doubleArrayOf(0.0, 0.0, 0.0)
    private set
  var cameraUp = doubleArrayOf(0.0, 0.0, 0.0)
    private set
  var viewMatrix: Array<DoubleArray> = arrayOf(
    doubleArrayOf(0.0, 0.0, 0.0, 0.0),
    doubleArrayOf(0.0, 0.0, 0.0, 0.0),
    doubleArrayOf(0.0, 0.0, 0.0, 0.0),
    doubleArrayOf(0.0, 0.0, 0.0, 0.0)
  )
    private set
  var perspectiveMatrix: Matrix = Matrix(
    rows = 4,
    columns = 4,
    array = floatArrayOf(
      0f, 0f, 0f, 0f,
      0f, 0f, 0f, 0f,
      0f, 0f, 0f, 0f,
      0f, 0f, 0f, 0f
    )
  )
    private set

  private var cameraAngle: Double = 0.0
  private var cameraFov: Float = 75.0f
  private var cameraHeight: Double = 500.0

  fun changeCameraAngle(angle: Double) {
    cameraAngle += angle
  }

  fun changeCameraHeight(height: Double) {
    cameraHeight += height
  }

  fun changeCameraFov(increase: Float) {
    cameraFov += increase
  }

  fun update(timeElapsed: Long) {
//    cameraFov = sin(System.currentTimeMillis() / 1000.0) * 15.0 + 75.0
    cameraShift = doubleArrayOf(0.0, cameraHeight, 250.0)
    cameraTarget = Player.worldCoordinates
    cameraPosition = cameraTarget.plus(cameraShift.rotateYAxis(cameraAngle))
    cameraDirection = cameraTarget.minus(cameraPosition).normalizeVector()
    cameraRight = doubleArrayOf(0.0, 1.0, 0.0).cross(cameraDirection).normalizeVector()
    cameraUp = cameraDirection.cross(cameraRight).normalizeVector()

//    val followSpeed = 0.0015 * zoom
//    var cameraVelocityVector = goal.minus(cameraPosition)
//    val cameraSpeed = cameraVelocityVector.module() * followSpeed * timeElapsed
//    cameraVelocityVector = cameraVelocityVector.normalizeVector().multiplyByFactor(cameraSpeed)
//    cameraPosition = Player.worldCoordinates.plus(doubleArrayOf(0.0, 100.0, 100.0))

    viewMatrix = makeViewMatrix()
    perspectiveMatrix = makePerspectiveMatrix(
      fov = cameraFov,
      aspect = Window.resolution.height.toFloat() / Window.resolution.width.toFloat(),
      near = 0.1f,
      far = 1000.0f
    )
    printVectors(timeElapsed)
  }

  //TODO Debug
  private var timeElapsedSinceLastPrint = 0L
  private fun printVectors(timeElapsed: Long) {
    timeElapsedSinceLastPrint += timeElapsed
    if (timeElapsedSinceLastPrint > 5000L) {
      timeElapsedSinceLastPrint = 0L
      println("Vectors updated")
      println("cameraPosition: ${cameraPosition.print()}")
      println("cameraTarget: ${cameraTarget.print()}")
      println("cameraDirection: ${cameraDirection.print()}")
      println("cameraRight: ${cameraRight.print()}")
      println("cameraUp: ${cameraUp.print()}")
    }
  }
}
