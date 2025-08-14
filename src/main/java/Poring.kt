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

class Poring {

  private val sprite: Texture = Texture.loadTexture("src/main/resources/sprites/poring.png")

  private val coordinates = Coordinates(0.5f, 0.0f)

  enum class EnemyState {
    Standing,
    Walking
  }

  private var enemyState = EnemyState.Standing


  private fun getSpriteXOffset(): Int =
    when (enemyState) {
      EnemyState.Standing -> 0
      EnemyState.Walking -> 0
    }

  private fun getSpriteYOffset(): Int =
    when (enemyState) {
      EnemyState.Standing -> 0
      EnemyState.Walking -> 0
    }

  private fun getSpriteWidth(): Int =
    when (enemyState) {
      EnemyState.Standing -> 56
      EnemyState.Walking -> 56
    }

  private fun getSpriteHeight(): Int =
    when (enemyState) {
      EnemyState.Standing -> 58
      EnemyState.Walking -> 58
    }

  private val size: Float = 8f

  private var spriteFrame = 0

  private fun getSpriteCount(): Int =
    when (enemyState) {
      EnemyState.Standing -> 4
      EnemyState.Walking -> 4
    }

  fun render() {

    spriteFrame = (spriteFrame + 1) % getSpriteCount()

    val u = getSpriteXOffset().fractionOf(sprite.height) + spriteFrame.toFloat() * getSpriteWidth().fractionOf(sprite.width)
    val v2 = 1f - getSpriteYOffset().fractionOf(sprite.height)
    val v = v2 - getSpriteHeight().fractionOf(sprite.height)
    val u2 = u + getSpriteWidth().fractionOf(sprite.width)

    val vertices: FloatArray = floatArrayOf(
      coordinates.x + size * getSpriteWidth().fractionOf(sprite.width) / 2f, coordinates.y + size * getSpriteHeight().fractionOf(sprite.height) / 2f, 0.0f,
      1f, 1f, 1f,
      u2, v2,

      coordinates.x + size * getSpriteWidth().fractionOf(sprite.width) / 2f, coordinates.y + size * -getSpriteHeight().fractionOf(sprite.height) / 2f, 0.0f,
      1f, 1f, 1f,
      u2, v,

      coordinates.x + size * -getSpriteWidth().fractionOf(sprite.width) / 2f, coordinates.y + size * -getSpriteHeight().fractionOf(sprite.height) / 2f, 0.0f,
      1f, 1f, 1f,
      u, v,

      coordinates.x + size * -getSpriteWidth().fractionOf(sprite.width) / 2f, coordinates.y + size * getSpriteHeight().fractionOf(sprite.height) / 2f, 0.0f,
      1f, 1f, 1f,
      u, v2
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