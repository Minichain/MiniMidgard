import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

//TODO Create a Vector/Coordinates class and put all the methods there
fun DoubleArray.toCameraCoordinates(): DoubleArray {
  val firstMultiplication = doubleArrayOf(
    Camera.viewMatrix[0][0] * this[0] + Camera.viewMatrix[1][0] * this[1] + Camera.viewMatrix[2][0] * this[2] + Camera.viewMatrix[3][0] * 1.0,
    Camera.viewMatrix[0][1] * this[0] + Camera.viewMatrix[1][1] * this[1] + Camera.viewMatrix[2][1] * this[2] + Camera.viewMatrix[3][1] * 1.0,
    Camera.viewMatrix[0][2] * this[0] + Camera.viewMatrix[1][2] * this[1] + Camera.viewMatrix[2][2] * this[2] + Camera.viewMatrix[3][2] * 1.0,
    Camera.viewMatrix[0][3] * this[0] + Camera.viewMatrix[1][3] * this[1] + Camera.viewMatrix[2][3] * this[2] + Camera.viewMatrix[3][3] * 1.0
  )
//  val secondMultiplication = doubleArrayOf(
//    Camera.perspectiveMatrix[0][0] * firstMultiplication[0] + Camera.perspectiveMatrix[1][0] * firstMultiplication[1] + Camera.perspectiveMatrix[2][0] * firstMultiplication[2] + Camera.perspectiveMatrix[3][0] * 1.0,
//    Camera.perspectiveMatrix[0][1] * firstMultiplication[0] + Camera.perspectiveMatrix[1][1] * firstMultiplication[1] + Camera.perspectiveMatrix[2][1] * firstMultiplication[2] + Camera.perspectiveMatrix[3][1] * 1.0,
//    Camera.perspectiveMatrix[0][2] * firstMultiplication[0] + Camera.perspectiveMatrix[1][2] * firstMultiplication[1] + Camera.perspectiveMatrix[2][2] * firstMultiplication[2] + Camera.perspectiveMatrix[3][2] * 1.0,
//    Camera.perspectiveMatrix[0][3] * firstMultiplication[0] + Camera.perspectiveMatrix[1][3] * firstMultiplication[1] + Camera.perspectiveMatrix[2][3] * firstMultiplication[2] + Camera.perspectiveMatrix[3][3] * 1.0
//  )
  return firstMultiplication
}

//TODO Define a Matrix class for scenarios such as this one
fun makeViewMatrix(): Array<DoubleArray> {
  val zAxis = Camera.cameraDirection.negative().normalizeVector()
  val xAxis = Camera.cameraUp.cross(zAxis).normalizeVector()
  val yAxis = zAxis.cross(xAxis)
  return arrayOf(
    doubleArrayOf(xAxis[0], yAxis[0], zAxis[0], 0.0),
    doubleArrayOf(xAxis[1], yAxis[1], zAxis[1], 0.0),
    doubleArrayOf(xAxis[2], yAxis[2], zAxis[2], 0.0),
    doubleArrayOf(-xAxis.dot(Camera.cameraPosition), -yAxis.dot(Camera.cameraPosition), -zAxis.dot(Camera.cameraPosition), 1.0)
  )
}

fun makePerspectiveMatrix(fov: Float, aspect: Float, near: Float, far: Float): Matrix {
  val f = (1.0 / tan(Math.toRadians(fov.toDouble()) / 2.0)).toFloat()
  return Matrix(
    rows = 4,
    columns = 4,
    array = floatArrayOf(
      aspect * f, 0.0f, 0.0f, 0.0f,
      0.0f, f, 0.0f, 0.0f,
      0.0f, 0.0f, - far / (far - near), -1.0f,
      0.0f, 0.0f, - (near * far) / (far - near), 0.0f
    )
  )
}

fun DoubleArray.negative(): DoubleArray =
  DoubleArray(this.size) { i -> this[i] * -1 }

fun DoubleArray.minus(coordinates: DoubleArray): DoubleArray =
  DoubleArray(this.size) { i -> this[i] - coordinates[i] }

fun DoubleArray.plus(coordinates: DoubleArray): DoubleArray =
  DoubleArray(this.size) { i -> this[i] + coordinates[i] }

fun DoubleArray.multiplyByFactor(factor: Double): DoubleArray =
  DoubleArray(this.size) { i -> this[i] * factor }

fun DoubleArray.dot(vector: DoubleArray): Double {
  var sum = 0.0
  forEachIndexed { i, value ->
    sum += value * vector[i]
  }
  return sum
}

fun DoubleArray.cross(coordinates: DoubleArray): DoubleArray =
  doubleArrayOf(
    this[1] * coordinates[2] - this[2] * coordinates[1],
    this[2] * coordinates[0] - this[0] * coordinates[2],
    this[0] * coordinates[1] - this[1] * coordinates[0]
  )

fun DoubleArray.toFloatArray(): FloatArray =
  FloatArray(this.size) { i -> this[i].toFloat() }

fun randomCoordinates(
  xRange: IntRange = 0..0,
  yRange: IntRange = 0..0,
  zRange: IntRange = 0..0
) = doubleArrayOf(
  xRange.random().toDouble(),
  yRange.random().toDouble(),
  zRange.random().toDouble()
)

fun DoubleArray.normalizeVector(): DoubleArray {
  var vectorNormalized = DoubleArray(size)
  val vectorModule = module()
  if (vectorModule > 0) {
    for (i in 0 until size) {
      vectorNormalized[i] = this[i] / vectorModule
    }
  } else {
    vectorNormalized = this
  }
  return vectorNormalized
}

fun DoubleArray.module(): Double {
  var sum = 0.0
  forEachIndexed { i, value ->
    sum += value.pow(2.0)
  }
  return sqrt(sum)
}

fun DoubleArray.rotateXAxis(radians: Double): DoubleArray =
  doubleArrayOf(
    this[0],
    this[1] * cos(radians) - this[2] * sin(radians),
    this[1] * sin(radians) + this[2] * cos(radians)
  )

fun DoubleArray.rotateYAxis(radians: Double): DoubleArray =
  doubleArrayOf(
    this[0] * cos(radians) - this[2] * sin(radians),
    this[1],
    - this[0] * sin(radians) - this[2] * cos(radians)
  )

fun DoubleArray.rotateZAxis(radians: Double): DoubleArray =
  doubleArrayOf(
    this[0] * cos(radians) - this[1] * sin(radians),
    this[0] * sin(radians) + this[1] * cos(radians),
    this[2]
  )

fun DoubleArray.print(): String =
  "(${this[0]}, ${this[1]}, ${this[2]})"
