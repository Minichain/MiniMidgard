import org.lwjgl.system.MemoryStack

class Matrix(val rows: Int, val columns: Int, val array: FloatArray) {
  fun toBuffer() = MemoryStack.stackPush().use { stack ->
    stack.mallocFloat(rows * columns).put(array).flip()
  }
}