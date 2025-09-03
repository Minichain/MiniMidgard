import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL30.glBindVertexArray
import org.lwjgl.opengl.GL30.glDeleteVertexArrays
import org.lwjgl.opengl.GL30.glGenVertexArrays

object GraphicsManager {

  private var programShader01: Int = 0
  private var programShader02: Int = 0

  var gpuCalls: Int = 0
    private set

  private var vao: Int
  private var vbo: Int
  private var ebo: Int

  init {
    prepareOpenGL()
    vao = glGenVertexArrays()
    vbo = glGenBuffers()
    ebo = glGenBuffers()
  }

  fun prepareOpenGL() {
    GL.createCapabilities()
    programShader01 = loadShader("shader01")
    programShader02 = loadShader("shader02")
    println("OpenGL renderer: ${glGetString(GL_RENDERER)}")
    println("OpenGL vendor: ${glGetString(GL_VENDOR)}")
    println("OpenGL version: ${glGetString(GL_VERSION)}")
  }

  fun prepareFrame() {
    gpuCalls = 0
    glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
    glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
  }

  fun loadShader(shaderName: String): Int {
    val vertexShader: Int
    val fragmentShader: Int
    val programShader = glCreateProgram()

    try {
      vertexShader = createShader("src/main/resources/shaders/$shaderName.vert", GL_VERTEX_SHADER)
      fragmentShader = createShader("src/main/resources/shaders/$shaderName.frag", GL_FRAGMENT_SHADER)
    } catch (e: Exception) {
      e.printStackTrace()
      return 0
    }

    if (vertexShader == 0 || fragmentShader == 0 || programShader == 0) return 0

    /**
     * If the vertex and fragment shaders setup successfully,
     * attach them to the shader program, link the shader program,
     * and validate.
     */
    glAttachShader(programShader, vertexShader)
    glAttachShader(programShader, fragmentShader)

    glLinkProgram(programShader)
    if (glGetProgrami(programShader, GL_LINK_STATUS) == GL_FALSE) {
      println(getLogInfo(programShader))
      return 0
    }

    glValidateProgram(programShader)
    if (glGetProgrami(programShader, GL_VALIDATE_STATUS) == GL_FALSE) {
      println(getLogInfo(programShader))
      return 0
    }

    return programShader
  }

  private fun createShader(filename: String, shaderType: Int): Int {
    var shader = 0
    try {
      shader = glCreateShader(shaderType)

      if (shader == 0) return 0

      glShaderSource(shader, FileUtils.readFileAsString(filename))
      glCompileShader(shader)

      if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
        throw RuntimeException("Error creating shader: " + getLogInfo(shader))
      }

      return shader
    } catch (e: java.lang.Exception) {
      glDeleteShader(shader)
      throw e
    }
  }

  fun updateShadersUniforms() {
    updateShader01Uniforms()
    updateShader02Uniforms()
  }

  private fun updateShader01Uniforms() {
    val windowWidthUniform = glGetUniformLocation(programShader01, "windowWidth")
    val windowHeightUniform = glGetUniformLocation(programShader01, "windowHeight")
    val perspectiveMatrixUniform = glGetUniformLocation(programShader01, "perspectiveMatrix")
    val textureUniform01 = glGetUniformLocation(programShader01, "ourTexture")

    glUseProgram(programShader01)
    glUniform1f(windowWidthUniform, Window.resolution.width.toFloat())
    glUniform1f(windowHeightUniform, Window.resolution.height.toFloat())
    glUniformMatrix4fv(perspectiveMatrixUniform, false, Camera.perspectiveMatrix.toBuffer())
    glUniform1i(textureUniform01, 0)
  }

  private fun updateShader02Uniforms() {
    val windowWidthUniform = glGetUniformLocation(programShader02, "windowWidth")
    val windowHeightUniform = glGetUniformLocation(programShader02, "windowHeight")
    val textureUniform01 = glGetUniformLocation(programShader02, "ourTexture")

    glUseProgram(programShader02)
    glUniform1f(windowWidthUniform, Window.resolution.width.toFloat())
    glUniform1f(windowHeightUniform, Window.resolution.height.toFloat())
    glUniform1i(textureUniform01, 0)
  }

  fun useShader(shader: Int) {
    when (shader) {
      1 -> glUseProgram(programShader01)
      2 -> glUseProgram(programShader02)
      else -> glUseProgram(0)
    }
  }

  fun releaseCurrentShader() {
    useShader(0)
  }

  private fun getLogInfo(obj: Int): String =
    glGetProgramInfoLog(obj, glGetProgrami(obj, GL_INFO_LOG_LENGTH))

  fun render(
    texture: Texture,
    vertex1: Vector3,
    vertex2: Vector3,
    vertex3: Vector3,
    vertex4: Vector3,
    u1: Float, v1: Float,
    u2: Float, v2: Float
  ) {
    val vertices: FloatArray = floatArrayOf(
      vertex1.x, vertex1.y, vertex1.z,
      1f, 1f, 1f,
      u2, v2,

      vertex2.x, vertex2.y, vertex2.z,
      1f, 1f, 1f,
      u2, v1,

      vertex3.x, vertex3.y, vertex3.z,
      1f, 1f, 1f,
      u1, v1,

      vertex4.x, vertex4.y, vertex4.z,
      1f, 1f, 1f,
      u1, v2
    )

    val indices: IntArray = intArrayOf(
      0, 1, 3,   // first triangle
      1, 2, 3    // second triangle
    )

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

    glActiveTexture(GL_TEXTURE0)
    texture.bind()
    glBindVertexArray(vao)
    glDrawElements(GL_TRIANGLES, indices.size, GL_UNSIGNED_INT, 0)

    glDisable(GL_BLEND)
    glDisable(GL_TEXTURE_2D)
  }

  fun deleteBuffers() {
    glDeleteVertexArrays(vao)
    glDeleteBuffers(vbo)
    glDeleteBuffers(ebo)
  }
}