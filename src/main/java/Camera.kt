import entities.Player

object Camera {

  private var cameraShift = Vector3(0f, 10f, 10f)
  var cameraPosition = Vector3().plus(cameraShift)
    private set
  var cameraTarget = Vector3()
    private set
  var cameraFacingVector = Vector3()
    private set
  var cameraRight = Vector3()
    private set
  var cameraUp = Vector3()
    private set
  var viewMatrix: Matrix = Matrix(
    rows = 4,
    columns = 4,
    values = arrayOf(
      floatArrayOf(0f, 0f, 0f, 0f),
      floatArrayOf(0f, 0f, 0f, 0f),
      floatArrayOf(0f, 0f, 0f, 0f),
      floatArrayOf(0f, 0f, 0f, 0f)
    )
  )
    private set
  var perspectiveMatrix: Matrix4x4 = Matrix4x4()
    private set

  private var cameraPanAngle: Float = 0f
  private var cameraTiltAngle: Float = Math.PI.toFloat() / 8f
  private var cameraFov: Float = 75.0f
  private var cameraDistance: Float = 500f

  fun changeCameraPanAngle(angle: Float) {
    cameraPanAngle += angle
  }

  fun changeCameraTiltAngle(angle: Float) {
    cameraTiltAngle += angle
  }

  fun changeCameraDistance(distance: Float) {
    cameraDistance += distance
  }

  fun changeCameraFov(increase: Float) {
    cameraFov += increase
  }

  fun update(timeElapsed: Long) {
//    cameraFov = sin(System.currentTimeMillis() / 1000.0) * 15.0 + 75.0
    cameraShift = Vector3(0f, cameraDistance, cameraDistance)
    cameraTarget = Player.worldCoordinates
    cameraShift = cameraShift.rotateYAxis(cameraPanAngle)
//    cameraShift = cameraShift.rotateXAxis(cameraTiltAngle)
    cameraPosition = cameraTarget.plus(cameraShift)
    cameraFacingVector = cameraTarget.minus(cameraPosition).normalized()
    cameraRight = Vector3(0f, 1f, 0f).cross(cameraFacingVector).normalized()
    cameraUp = cameraFacingVector.cross(cameraRight).normalized()

//    val followSpeed = 0.0015 * zoom
//    var cameraVelocityVector = goal.minus(cameraPosition)
//    val cameraSpeed = cameraVelocityVector.module() * followSpeed * timeElapsed
//    cameraVelocityVector = cameraVelocityVector.normalizeVector().multiplyByFactor(cameraSpeed)
//    cameraPosition = entities.Player.worldCoordinates.plus(floatArrayOf(0f, 100.0, 100.0))

    viewMatrix = Matrix.makeViewMatrix(cameraPosition, cameraFacingVector, cameraUp)
    perspectiveMatrix = Matrix.makePerspectiveMatrix(
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
      println("cameraPosition: $cameraPosition")
      println("cameraTarget: $cameraTarget")
      println("cameraDirection: $cameraFacingVector")
      println("cameraRight: $cameraRight")
      println("cameraUp: $cameraUp")
    }
  }
}
