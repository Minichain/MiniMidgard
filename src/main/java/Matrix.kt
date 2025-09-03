import kotlin.math.tan
import org.lwjgl.system.MemoryStack

class Matrix(
  val rows: Int,
  val columns: Int,
  var values: Array<FloatArray>
) {

  fun toBuffer() = MemoryStack.stackPush().use { stack ->
    stack.mallocFloat(rows * columns).put(asConcatenatedFloatArray()).flip()
  }

  fun asConcatenatedFloatArray(): FloatArray =
    values.flatMap { it.asList() }.toFloatArray()

  companion object {
    fun makeViewMatrix(position: Vector3, facingVector: Vector3, upVector: Vector3): Matrix {
      val zAxis = -facingVector.normalized()
      val xAxis = upVector.cross(zAxis).normalized()
      val yAxis = zAxis.cross(xAxis)
      return Matrix(
        rows = 4,
        columns = 4,
        values = arrayOf(
          floatArrayOf(xAxis.x, yAxis.x, zAxis.x, 0f),
          floatArrayOf(xAxis.y, yAxis.y, zAxis.y, 0f),
          floatArrayOf(xAxis.z, yAxis.z, zAxis.z, 0f),
          floatArrayOf(-xAxis.dot(position), -yAxis.dot(position), -zAxis.dot(position), 1f)
        )
      )
    }

    fun makePerspectiveMatrix(fov: Float, aspect: Float, near: Float, far: Float): Matrix {
      val f = (1f / tan(Math.toRadians(fov.toDouble()) / 2f)).toFloat()
      return Matrix(
        rows = 4,
        columns = 4,
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
