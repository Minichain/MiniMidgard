import org.lwjgl.opengl.GL11.*
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

object Game {

  private lateinit var testingTexture: Texture

  fun startGame() {
    println("Starting game...")
  }

  fun update(timeElapsedMillis: Long) {

  }

  fun render() {
    GraphicsManager.prepareFrame()
    GraphicsManager.updateShadersUniforms()
    renderTestingTexture()
//    renderTestingTriangles()
  }

  //TESTING
  fun renderTestingTexture() {
    if (!this::testingTexture.isInitialized) {
      testingTexture = Texture.loadTexture("src/main/resources/sprites/body_male_novice.png")
    }

    val vertices: FloatArray = floatArrayOf(
      // positions          // colors           // texture coords
      0.5f,  0.5f, 0.0f,    1.0f, 0.0f, 0.0f,   1.0f, 1.0f,   // top right
      0.5f, -0.5f, 0.0f,    0.0f, 1.0f, 0.0f,   1.0f, 0.0f,   // bottom right
      -0.5f, -0.5f, 0.0f,   0.0f, 0.0f, 1.0f,   0.0f, 0.0f,   // bottom left
      -0.5f,  0.5f, 0.0f,   1.0f, 1.0f, 0.0f,   0.0f, 1.0f    // top left
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

//    glActiveTexture(GL_TEXTURE0)
    GraphicsManager.useShader(1)
    glActiveTexture(GL_TEXTURE0)
    testingTexture.bind()
    glBindVertexArray(vao)
    glDrawElements(GL_TRIANGLES, indices.size, GL_UNSIGNED_INT, 0)

    glBindVertexArray(0)

//    GraphicsManager.glEnd()
//    glDisable(GL_BLEND)
//    glDisable(GL_TEXTURE_2D)
//    GraphicsManager.releaseCurrentShader()
  }

  //TESTING
  fun renderTestingTriangles() {

    val vertices: FloatArray = floatArrayOf(
      0.5f,  0.5f, 0.0f,
      0.5f, -0.5f, 0.0f,
      -0.5f, -0.5f, 0.0f,
      -0.5f,  0.5f, 0.0f,
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

    glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.SIZE_BYTES, 0)
    glEnableVertexAttribArray(0)

    glBindBuffer(GL_ARRAY_BUFFER, 0)
    glBindVertexArray(0)

    GraphicsManager.useShader(1)
    glBindVertexArray(vao)
    glDrawElements(GL_TRIANGLES, indices.size, GL_UNSIGNED_INT, 0)

    glBindVertexArray(0)

//    glEnable(GL_BLEND)
//    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
//    GraphicsManager.glBegin(GL_TRIANGLES)

//    GraphicsManager.drawTriangle(
//      Vector4(255f, 0f, 0f, 255f),
//      Vector3(-0.5f, -0.5f, 0.0f),
//      Vector3(0.5f, -0.5f, 0.0f),
//      Vector3(0.0f, 0.5f, 0.0f)
//    )

//    GraphicsManager.glEnd()
//    glDisable(GL_BLEND)
//    GraphicsManager.releaseCurrentShader()
  }

  fun stopGame() {
    println("Stopping game...")
  }
}