import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

abstract class Vector {

  abstract var values: FloatArray

  override fun toString(): String =
    values.joinToString(prefix = "(", separator = ", ", postfix = ")") { String.format("%.2f", it) }

}

class Vector2(x: Float = 0f, y: Float = 0f) : Vector() {

  override var values: FloatArray = floatArrayOf(x, y)
  val x get() = values[0]
  val y get() = values[1]

  fun module(): Float =
    sqrt(x.pow(2f) + y.pow(2f))

  fun normalized(): Vector2 {
    val module = module()
    return if (module > 0f) {
      Vector2(x / module, y / module)
    } else {
      this
    }
  }

  operator fun unaryMinus(): Vector2 =
    Vector2(-x, -y)

  operator fun minus(vector: Vector2): Vector2 =
    Vector2(x - vector.x, y - vector.y)

  operator fun plus(vector: Vector2): Vector2 =
    Vector2(x + vector.x, y + vector.y)

  operator fun times(factor: Float): Vector2 =
    Vector2(x * factor, y * factor)

  fun dot(vector: Vector2): Float =
    x * vector.x + y * vector.y

  companion object {
    fun random(
      xRange: IntRange = -0..0,
      yRange: IntRange = -0..0
    ) =
      Vector2(
        xRange.random().toFloat(),
        yRange.random().toFloat()
      )
  }
}

class Vector3(x: Float = 0f, y: Float = 0f, z: Float = 0f) : Vector() {

  override var values: FloatArray = floatArrayOf(x, y, z)
  val x get() = values[0]
  val y get() = values[1]
  val z get() = values[2]

  fun module(): Float =
    sqrt(x.pow(2f) + y.pow(2f) + z.pow(2f))

  fun normalized(): Vector3 {
    val module = module()
    return if (module > 0f) {
      Vector3(x / module, y / module, z / module)
    } else {
      this
    }
  }

  operator fun unaryMinus(): Vector3 =
    Vector3(-x, -y, -z)

  operator fun minus(vector: Vector3): Vector3 =
    Vector3(x - vector.x, y - vector.y, z - vector.z)

  operator fun plus(vector: Vector3): Vector3 =
    Vector3(x + vector.x, y + vector.y, z + vector.z)

  operator fun times(factor: Float): Vector3 =
    Vector3(x * factor, y * factor, z * factor)

  fun dot(vector: Vector3): Float =
    x * vector.x + y * vector.y + z * vector.z

  fun toCameraCoordinates(): Vector3 {
    val vec4 = Vector4(x, y, z, 1f).toCameraCoordinates()
    return Vector3(vec4.x, vec4.y, vec4.z)
  }

  fun multiplyByPerspectiveInvMatrix(): Vector3 {
    val perspectiveMatrixInv = Camera.perspectiveMatrix.inv()
    val vec4 = Vector4(x, y, z, 1f) * perspectiveMatrixInv
    return Vector3(vec4.x, vec4.y, vec4.z)
  }

  fun cross(coordinates: Vector3): Vector3 =
    Vector3(
      y * coordinates.z - z * coordinates.y,
      z * coordinates.x - x * coordinates.z,
      x * coordinates.y - y * coordinates.x
    )

  fun rotateXAxis(radians: Float): Vector3 =
    Vector3(
      x,
      y * cos(radians) - z * sin(radians),
      y * sin(radians) + z * cos(radians)
    )

  fun rotateYAxis(radians: Float): Vector3 =
    Vector3(
      x * cos(radians) + z * sin(radians),
      y,
      - x * sin(radians) + z * cos(radians)
    )

  fun rotateZAxis(radians: Float): Vector3 =
    Vector3(
      x * cos(radians) - y * sin(radians),
      x * sin(radians) + y * cos(radians),
      z
    )

  companion object {
    fun random(
      xRange: IntRange = -0..0,
      yRange: IntRange = -0..0,
      zRange: IntRange = -0..0
    ) =
      Vector3(
        xRange.random().toFloat(),
        yRange.random().toFloat(),
        zRange.random().toFloat()
      )
  }
}

class Vector4(x: Float = 0f, y: Float = 0f, z: Float = 0f, k: Float = 0f) : Vector() {

  override var values: FloatArray = floatArrayOf(x, y, z, k)
  val x get() = values[0]
  val y get() = values[1]
  val z get() = values[2]
  val k get() = values[3]

  fun module(): Float =
    sqrt(x.pow(2f) + y.pow(2f) + z.pow(2f) + k.pow(2f))

  fun normalized(): Vector4 {
    val module = module()
    return if (module > 0f) {
      Vector4(x / module, y / module, z / module, k / module)
    } else {
      this
    }
  }

  operator fun unaryMinus(): Vector4 =
    Vector4(-x, -y, -z, -k)

  operator fun minus(vector: Vector4): Vector4 =
    Vector4(x - vector.x, y - vector.y, z - vector.z, k - vector.k)

  operator fun plus(vector: Vector4): Vector4 =
    Vector4(x + vector.x, y + vector.y, z + vector.z, k + vector.k)

  operator fun times(matrix4x4: Matrix4x4): Vector4 {
    var x = 0f; var y = 0f; var z = 0f; var k = 0f
    for (i in 0 until 4) {
      x += matrix4x4.values[i][0] * values[i]
      y += matrix4x4.values[i][1] * values[i]
      z += matrix4x4.values[i][2] * values[i]
      k += matrix4x4.values[i][3] * values[i]
    }
    return Vector4(x, y, z, k)
  }

  operator fun times(factor: Float): Vector4 =
    Vector4(x * factor, y * factor, z * factor, k * factor)

  fun dot(vector: Vector4): Float =
    x * vector.x + y * vector.y + z * vector.z + k * vector.k

  fun toCameraCoordinates(): Vector4 =
    this * Camera.viewMatrix

  companion object {
    fun random(
      xRange: IntRange = -0..0,
      yRange: IntRange = -0..0,
      zRange: IntRange = -0..0,
      kRange: IntRange = -0..0
    ) =
      Vector4(
        xRange.random().toFloat(),
        yRange.random().toFloat(),
        zRange.random().toFloat(),
        kRange.random().toFloat()
      )
  }
}