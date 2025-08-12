import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL13.GL_TEXTURE0
import org.lwjgl.opengl.GL13.glActiveTexture
import org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER
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
//    renderTestingTexture()
    renderTestingTriangles()
  }

  //TESTING
  fun renderTestingTexture() {
    if (!this::testingTexture.isInitialized) {
      testingTexture = Texture.loadTexture("src/main/resources/sprites/body_male_novice.png")
    }
    GraphicsManager.useShader(1)
    glActiveTexture(GL_TEXTURE0)
    testingTexture.bind()

//    glBindVertexArray(VAO)
    glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0)

    glEnable(GL_BLEND)
    glEnable(GL_TEXTURE_2D)
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
    GraphicsManager.glBegin(GL_QUADS)

//    println("AdriLog: testingTexture.width: ${testingTexture.width}")
//    println("AdriLog: testingTexture.height: ${testingTexture.height}")

//    val width = testingTexture.width.toFloat() / 25.0.toFloat()
//    val height = testingTexture.height.toFloat() / 25.0.toFloat()

    val u: Float = 0f
    val v: Float = 0f
    val u2 = 1f / 16f
    val v2 = 1f / 9f


    GraphicsManager.drawTexture(0, 0, u, v, u2, v2, 54f, 100f)

    GraphicsManager.glEnd()
    glDisable(GL_BLEND)
    glDisable(GL_TEXTURE_2D)
    GraphicsManager.releaseCurrentShader()
  }

  //TESTING
  fun renderTestingTriangles() {
    val verticesTriangle01: FloatArray = floatArrayOf(
      1.0f, 1.0f, 0.0f,
      0.0f, 1.0f, 0.0f,
      1.0f, 0.0f, 0.0f
    )

    val verticesTriangle02: FloatArray = floatArrayOf(
      -1.0f, -1.0f, 0.0f,
      0.0f, -1.0f, 0.0f,
      -1.0f, 0.0f, 0.0f
    )

    val vao = glGenVertexArrays()
    val vbo = glGenBuffers()
    glBindVertexArray(vao)
    glBindBuffer(GL_ARRAY_BUFFER, vbo)
    glBufferData(GL_ARRAY_BUFFER, verticesTriangle01, GL_STATIC_DRAW)

    //TODO??
    glVertexAttribPointer(0, 3, GL_FLOAT, false, 12, 0)
    glEnableVertexAttribArray(0)

    glBindBuffer(GL_ARRAY_BUFFER, 0)
    glBindVertexArray(0)




    GraphicsManager.useShader(1)
    glBindVertexArray(vao)
    glDrawArrays(GL_TRIANGLES, 0, 3)

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