import org.lwjgl.opengl.GL11.GL_BLEND
import org.lwjgl.opengl.GL11.GL_FLOAT
import org.lwjgl.opengl.GL11.GL_TEXTURE_2D
import org.lwjgl.opengl.GL11.GL_TRIANGLES
import org.lwjgl.opengl.GL11.GL_UNSIGNED_INT
import org.lwjgl.opengl.GL11.glDisable
import org.lwjgl.opengl.GL11.glDrawElements
import org.lwjgl.opengl.GL13.GL_TEXTURE0
import org.lwjgl.opengl.GL13.glActiveTexture
import org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER
import org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER
import org.lwjgl.opengl.GL15.GL_STATIC_DRAW
import org.lwjgl.opengl.GL15.glBindBuffer
import org.lwjgl.opengl.GL15.glBufferData
import org.lwjgl.opengl.GL15.glGenBuffers
import org.lwjgl.opengl.GL20.glEnableVertexAttribArray
import org.lwjgl.opengl.GL20.glVertexAttribPointer
import org.lwjgl.opengl.GL30.glBindVertexArray
import org.lwjgl.opengl.GL30.glGenVertexArrays

open class Sprite {
  fun render(sprite: Texture, x1: Float, y1: Float, x2: Float, y2: Float, u1: Float, v1: Float, u2: Float, v2: Float) {
    val vertices: FloatArray = floatArrayOf(
      x2, y2, 0.0f,
      1f, 1f, 1f,
      u2, v2,

      x2, y1, 0.0f,
      1f, 1f, 1f,
      u2, v1,

      x1, y1, 0.0f,
      1f, 1f, 1f,
      u1, v1,

      x1, y2, 0.0f,
      1f, 1f, 1f,
      u1, v2
    )

    val indices: IntArray = intArrayOf(
      0, 1, 3,   // first triangle
      1, 2, 3    // second triangle
    )

    val vao = glGenVertexArrays()
    val vbo = glGenBuffers()
    val ebo = glGenBuffers()

    glBindVertexArray(vao)

    glBindBuffer(GL_ARRAY_BUFFER, vbo)
    glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW)

    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo)
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW)

    glVertexAttribPointer(0, 3, GL_FLOAT, false, 8 * Float.SIZE_BYTES, 0)
    glEnableVertexAttribArray(0)

    glVertexAttribPointer(1, 3, GL_FLOAT, false, 8 * Float.SIZE_BYTES, (3 * Float.SIZE_BYTES).toLong())
    glEnableVertexAttribArray(1)

    glVertexAttribPointer(2, 2, GL_FLOAT, false, 8 * Float.SIZE_BYTES, (6 * Float.SIZE_BYTES).toLong())
    glEnableVertexAttribArray(2)

    glBindBuffer(GL_ARRAY_BUFFER, 0)
    glBindVertexArray(0)

    GraphicsManager.useShader(1)
    glActiveTexture(GL_TEXTURE0)
    sprite.bind()
    glBindVertexArray(vao)
    glDrawElements(GL_TRIANGLES, indices.size, GL_UNSIGNED_INT, 0)

    glBindVertexArray(0)

    GraphicsManager.glEnd()
    glDisable(GL_BLEND)
    glDisable(GL_TEXTURE_2D)
    GraphicsManager.releaseCurrentShader()
  }
}