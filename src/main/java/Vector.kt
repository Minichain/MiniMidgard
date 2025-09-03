import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

abstract class Vector {

  abstract var values: FloatArray

  override fun toString(): String =
    values.joinToString(prefix = "(", separator = ", ", postfix = ")") { String.format("%.2f", it) }

}

class Vector2(x: Float, y: Float) : Vector() {

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

  fun multiplyByFactor(factor: Float): Vector2 =
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

class Vector3(x: Float, y: Float, z: Float) : Vector() {

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

  fun multiplyByFactor(factor: Float): Vector3 =
    Vector3(x * factor, y * factor, z * factor)

  fun dot(vector: Vector3): Float =
    x * vector.x + y * vector.y + z * vector.z

  fun toCameraCoordinates(): Vector3 =
    Vector3(
      Camera.viewMatrix.values[0][0] * x + Camera.viewMatrix.values[1][0] * y + Camera.viewMatrix.values[2][0] * z + Camera.viewMatrix.values[3][0] * 1f,
      Camera.viewMatrix.values[0][1] * x + Camera.viewMatrix.values[1][1] * y + Camera.viewMatrix.values[2][1] * z + Camera.viewMatrix.values[3][1] * 1f,
      Camera.viewMatrix.values[0][2] * x + Camera.viewMatrix.values[1][2] * y + Camera.viewMatrix.values[2][2] * z + Camera.viewMatrix.values[3][2] * 1f
    )

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

class Vector4(x: Float, y: Float, z: Float, k: Float) : Vector() {

  override var values: FloatArray = floatArrayOf(x, y, z, k)
  val x get() = values[0]
  val y get() = values[1]
  val z get() = values[2]
  val k get() = values[3]

  fun module(): Float =
    sqrt(x.pow(2f) + y.pow(2f) + z.pow(2f) + k.pow(2f))

  fun toCameraCoordinates(): Vector4 =
    Vector4(
      Camera.viewMatrix.values[0][0] * x + Camera.viewMatrix.values[1][0] * y + Camera.viewMatrix.values[2][0] * z + Camera.viewMatrix.values[3][0] * 1f,
      Camera.viewMatrix.values[0][1] * x + Camera.viewMatrix.values[1][1] * y + Camera.viewMatrix.values[2][1] * z + Camera.viewMatrix.values[3][1] * 1f,
      Camera.viewMatrix.values[0][2] * x + Camera.viewMatrix.values[1][2] * y + Camera.viewMatrix.values[2][2] * z + Camera.viewMatrix.values[3][2] * 1f,
      Camera.viewMatrix.values[0][3] * x + Camera.viewMatrix.values[1][3] * y + Camera.viewMatrix.values[2][3] * z + Camera.viewMatrix.values[3][3] * 1f
    )

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