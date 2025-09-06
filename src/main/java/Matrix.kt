import kotlin.math.tan
import org.lwjgl.system.MemoryStack

open class Matrix(
  val rows: Int,
  val columns: Int,
  var values: Array<FloatArray>
) {

  fun toBuffer() = MemoryStack.stackPush().use { stack ->
    stack.mallocFloat(rows * columns).put(asConcatenatedFloatArray()).flip()
  }

  fun asConcatenatedFloatArray(): FloatArray =
    values.flatMap { it.asList() }.toFloatArray()

  override fun toString(): String {
    val stringBuilder: StringBuilder = StringBuilder()
    for (i in 0 until rows) {
      stringBuilder.append(values[i].joinToString(separator = ", ") { String.format("%.2f", it) } + "\n")
    }
    return stringBuilder.toString()
  }

  companion object {
    fun makeViewMatrix(position: Vector3, facingVector: Vector3, upVector: Vector3): Matrix4x4 {
      val zAxis = -facingVector.normalized()
      val xAxis = upVector.cross(zAxis).normalized()
      val yAxis = zAxis.cross(xAxis)
      return Matrix4x4(
        values = arrayOf(
          floatArrayOf(xAxis.x, yAxis.x, zAxis.x, 0f),
          floatArrayOf(xAxis.y, yAxis.y, zAxis.y, 0f),
          floatArrayOf(xAxis.z, yAxis.z, zAxis.z, 0f),
          floatArrayOf(-xAxis.dot(position), -yAxis.dot(position), -zAxis.dot(position), 1f)
        )
      )
    }

    fun makePerspectiveMatrix(fov: Float, aspect: Float, near: Float, far: Float): Matrix4x4 {
      val f = (1f / tan(Math.toRadians(fov.toDouble()) / 2f)).toFloat()
      return Matrix4x4(
        values = arrayOf(
          floatArrayOf(aspect * f, 0.0f, 0.0f, 0.0f),
          floatArrayOf(0.0f, f, 0.0f, 0.0f),
          floatArrayOf(0.0f, 0.0f, - far / (far - near), -1.0f),
          floatArrayOf(0.0f, 0.0f, - (near * far) / (far - near), 0.0f)
        )
      )
    }
  }
}

class Matrix4x4(
  values: Array<FloatArray> = arrayOf(
    floatArrayOf(0f, 0f, 0f, 0f),
    floatArrayOf(0f, 0f, 0f, 0f),
    floatArrayOf(0f, 0f, 0f, 0f),
    floatArrayOf(0f, 0f, 0f, 0f)
  )
) : Matrix(
  rows = 4,
  columns = 4,
  values = values
) {

  fun inv(): Matrix4x4 {
    val inv = FloatArray(16)
    val invOut = FloatArray(16)
    val m = toBuffer()
    inv[0] = m[5]  * m[10] * m[15] - m[5]  * m[11] * m[14] - m[9]  * m[6]  * m[15] + m[9]  * m[7]  * m[14] + m[13] * m[6]  * m[11] - m[13] * m[7]  * m[10]
    inv[4] = -m[4]  * m[10] * m[15] + m[4]  * m[11] * m[14] + m[8]  * m[6]  * m[15] - m[8]  * m[7]  * m[14] - m[12] * m[6]  * m[11] + m[12] * m[7]  * m[10]
    inv[8] = m[4]  * m[9] * m[15] - m[4]  * m[11] * m[13] - m[8]  * m[5] * m[15] + m[8]  * m[7] * m[13] + m[12] * m[5] * m[11] - m[12] * m[7] * m[9]
    inv[12] = -m[4]  * m[9] * m[14] + m[4]  * m[10] * m[13] + m[8]  * m[5] * m[14] - m[8]  * m[6] * m[13] - m[12] * m[5] * m[10] + m[12] * m[6] * m[9]
    inv[1] = -m[1]  * m[10] * m[15] + m[1]  * m[11] * m[14] + m[9]  * m[2] * m[15] - m[9]  * m[3] * m[14] - m[13] * m[2] * m[11] + m[13] * m[3] * m[10]
    inv[5] = m[0]  * m[10] * m[15] - m[0]  * m[11] * m[14] - m[8]  * m[2] * m[15] + m[8]  * m[3] * m[14] + m[12] * m[2] * m[11] - m[12] * m[3] * m[10]
    inv[9] = -m[0]  * m[9] * m[15] + m[0]  * m[11] * m[13] + m[8]  * m[1] * m[15] - m[8]  * m[3] * m[13] - m[12] * m[1] * m[11] + m[12] * m[3] * m[9]
    inv[13] = m[0]  * m[9] * m[14] - m[0]  * m[10] * m[13] - m[8]  * m[1] * m[14] + m[8]  * m[2] * m[13] + m[12] * m[1] * m[10] - m[12] * m[2] * m[9]
    inv[2] = m[1]  * m[6] * m[15] - m[1]  * m[7] * m[14] - m[5]  * m[2] * m[15] + m[5]  * m[3] * m[14] + m[13] * m[2] * m[7] - m[13] * m[3] * m[6]
    inv[6] = -m[0]  * m[6] * m[15] + m[0]  * m[7] * m[14] + m[4]  * m[2] * m[15] - m[4]  * m[3] * m[14] - m[12] * m[2] * m[7] + m[12] * m[3] * m[6]
    inv[10] = m[0]  * m[5] * m[15] - m[0]  * m[7] * m[13] - m[4]  * m[1] * m[15] + m[4]  * m[3] * m[13] + m[12] * m[1] * m[7] - m[12] * m[3] * m[5]
    inv[14] = -m[0]  * m[5] * m[14] + m[0]  * m[6] * m[13] + m[4]  * m[1] * m[14] - m[4]  * m[2] * m[13] - m[12] * m[1] * m[6] + m[12] * m[2] * m[5]
    inv[3] = -m[1] * m[6] * m[11] + m[1] * m[7] * m[10] + m[5] * m[2] * m[11] - m[5] * m[3] * m[10] - m[9] * m[2] * m[7] + m[9] * m[3] * m[6]
    inv[7] = m[0] * m[6] * m[11] - m[0] * m[7] * m[10] - m[4] * m[2] * m[11] + m[4] * m[3] * m[10] + m[8] * m[2] * m[7] - m[8] * m[3] * m[6]
    inv[11] = -m[0] * m[5] * m[11] + m[0] * m[7] * m[9] + m[4] * m[1] * m[11] - m[4] * m[3] * m[9] - m[8] * m[1] * m[7] + m[8] * m[3] * m[5]
    inv[15] = m[0] * m[5] * m[10] - m[0] * m[6] * m[9] - m[4] * m[1] * m[10] + m[4] * m[2] * m[9] + m[8] * m[1] * m[6] - m[8] * m[2] * m[5]
    val det: Float = m[0] * inv[0] + m[1] * inv[4] + m[2] * inv[8] + m[3] * inv[12]
    if (det == 0f) throw IllegalStateException("Matrix not invertible")
    val invDet = 1.0f / det
    for (i in 0..15) {
      invOut[i] = inv[i] * invDet
    }
    return Matrix4x4(
      arrayOf(
        floatArrayOf(invOut[0], invOut[1], invOut[2], invOut[3]),
        floatArrayOf(invOut[4], invOut[5], invOut[6], invOut[7]),
        floatArrayOf(invOut[8], invOut[9], invOut[10], invOut[11]),
        floatArrayOf(invOut[12], invOut[13], invOut[14], invOut[15])
      )
    )
  }
}

fun TestMatrixRotation() {
  val testingMatrix = Matrix4x4(
    arrayOf(
      floatArrayOf(0f, 0f, -1f, 2f),
      floatArrayOf(0f, 1f, 0f, 0f),
      floatArrayOf(9f, 0f, 0f, 0f),
      floatArrayOf(0f, 0f, 0f, 1f)
    )
  )
  print("testing matrix: $testingMatrix")
  print("testing matrix inverse: ${testingMatrix.inv()}")
}
