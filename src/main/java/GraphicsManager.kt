import org.lwjgl.opengl.*
import org.lwjgl.opengl.GL20.*

object GraphicsManager {

  private const val ARB_SHADERS: Boolean = true
  private val VERTEX_SHADER: Int = if (ARB_SHADERS) ARBVertexShader.GL_VERTEX_SHADER_ARB else GL_VERTEX_SHADER
  private val FRAGMENT_SHADER: Int = if (ARB_SHADERS) ARBFragmentShader.GL_FRAGMENT_SHADER_ARB else GL_FRAGMENT_SHADER

  private var programShader01: Int = 0

  var gpuCalls: Int = 0
    private set

  init {
    prepareOpenGL()
  }

  fun glBegin(mode: Int) {
    GL20.glBegin(mode)
    gpuCalls++
  }

  fun glEnd() {
    GL20.glEnd()
  }

  private fun prepareOpenGL() {
    GL.createCapabilities()
    //Load shaders
    programShader01 = loadShader("shader01")
  }

  fun prepareFrame() {
    gpuCalls = 0

    //Clear frame
    glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

//    glLoadIdentity() //Cleans out any matrix mode
//    glOrtho(0.0, Parameters.resolutionWidth.toDouble(), Parameters.resolutionHeight.toDouble(), 0.0, 1.0, -1.0)

    //Face Culling
//    glEnable(GL_CULL_FACE)
//    glCullFace(GL_BACK)
  }

  fun loadShader(shaderName: String): Int {
    val vertexShader: Int
    val fragmentShader: Int
    val programShader = glCreateProgram()

    try {
      vertexShader = createShader("src/main/resources/shaders/$shaderName.vert", VERTEX_SHADER)
      fragmentShader = createShader("src/main/resources/shaders/$shaderName.frag", FRAGMENT_SHADER)
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

      if (glGetProgrami(shader, GL_COMPILE_STATUS) == GL_FALSE) {
        throw RuntimeException("Error creating shader: " + getLogInfo(shader))
      }

      return shader
    } catch (e: java.lang.Exception) {
      glDeleteShader(shader)
      throw e
    }
  }

  fun updateShadersUniforms() {
    val windowWidthUniform = glGetUniformLocation(programShader01, "windowWidth")
    val windowHeightUniform = glGetUniformLocation(programShader01, "windowHeight")
    val textureUniform01 = glGetUniformLocation(programShader01, "ourTexture")

    glUseProgram(programShader01)
//    glUniform1f(windowWidthUniform, Window.width.toFloat())
//    glUniform1f(windowHeightUniform, Window.height.toFloat())
    glUniform1i(textureUniform01, 0)
  }

  fun useShader(shader: Int) {
    when (shader) {
      1 -> glUseProgram(programShader01)
      else -> glUseProgram(0)
    }
  }

  fun releaseCurrentShader() {
    useShader(0)
  }

  fun drawTexture(
    x: Int,
    y: Int,
    u: Float,
    v: Float,
    u2: Float,
    v2: Float,
    spriteWidth: Float,
    spriteHeight: Float,
    transparency: Float = 1f,
    red: Float = 1f,
    green: Float = 1f,
    blue: Float = 1f
  ) {
//    glColor4f(red, green, blue, transparency)
    glTexCoord2f(u, v)
    glVertex2f(x.toFloat(), y.toFloat())
    glTexCoord2f(u, v2)
    glVertex2f(x.toFloat(), y + spriteHeight)
    glTexCoord2f(u2, v2)
    glVertex2f(x + spriteWidth, y + spriteHeight)
    glTexCoord2f(u2, v)
    glVertex2f(x + spriteWidth, y.toFloat())
  }

  fun drawTriangle(color: Vector4, p1: Vector3, p2: Vector3, p3: Vector3) {
    glColor4f(color.x, color.y, color.z, color.a)
    glVertex3f(p1.x, p1.y, p1.z)
    glVertex3f(p2.x, p2.y, p2.z)
    glVertex3f(p3.x, p3.y, p3.z)
  }

  /**
   * Methods depending on if "ARB_SHADERS" is ENABLED / DISABLED
   */
  private fun glDeleteShader(shader: Int) {
    if (ARB_SHADERS) {
      ARBShaderObjects.glDeleteObjectARB(shader)
    } else {
      GL20.glDeleteShader(shader)
    }
  }

  private fun glCompileShader(shader: Int) {
    if (ARB_SHADERS) {
      ARBShaderObjects.glCompileShaderARB(shader)
    } else {
      GL20.glCompileShader(shader)
    }
  }

  private fun glShaderSource(shader: Int, file: String) {
    if (ARB_SHADERS) {
      ARBShaderObjects.glShaderSourceARB(shader, file)
    } else {
      GL20.glShaderSource(shader, file)
    }
  }

  private fun glCreateShader(shaderType: Int): Int {
    if (ARB_SHADERS) {
      return ARBShaderObjects.glCreateShaderObjectARB(shaderType)
    } else {
      return GL20.glCreateShader(shaderType)
    }
  }

  private fun getLogInfo(obj: Int): String {
    if (ARB_SHADERS) {
      return ARBShaderObjects.glGetInfoLogARB(obj, glGetProgrami(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB))
    } else {
      return glGetProgramInfoLog(obj, glGetProgrami(obj, GL_INFO_LOG_LENGTH))
    }
  }

  private fun glGetUniformLocation(shader: Int, uniformName: String): Int {
    if (ARB_SHADERS) {
      return ARBShaderObjects.glGetUniformLocationARB(shader, uniformName)
    } else {
      return GL20.glGetUniformLocation(shader, uniformName)
    }
  }

  private fun glUseProgram(shader: Int) {
    if (ARB_SHADERS) {
      ARBShaderObjects.glUseProgramObjectARB(shader)
    } else {
      GL20.glUseProgram(shader)
    }
  }

  private fun glUniform1f(uniformLocation: Int, uniformValue: Float) {
    if (ARB_SHADERS) {
      ARBShaderObjects.glUniform1fARB(uniformLocation, uniformValue)
    } else {
      GL20.glUniform1f(uniformLocation, uniformValue)
    }
  }

  private fun glUniform1i(uniformLocation: Int, uniformValue: Int) {
    if (ARB_SHADERS) {
      ARBShaderObjects.glUniform1iARB(uniformLocation, uniformValue)
    } else {
      GL20.glUniform1i(uniformLocation, uniformValue)
    }
  }

  private fun glUniform1fv(uniformLocation: Int, uniformValue: FloatArray) {
    if (ARB_SHADERS) {
      ARBShaderObjects.glUniform1fvARB(uniformLocation, uniformValue)
    } else {
      GL20.glUniform1fv(uniformLocation, uniformValue)
    }
  }

  private fun glUniform2fv(uniformLocation: Int, uniformValue: FloatArray) {
    if (ARB_SHADERS) {
      ARBShaderObjects.glUniform2fvARB(uniformLocation, uniformValue)
    } else {
      GL20.glUniform2fv(uniformLocation, uniformValue)
    }
  }

  private fun glUniform3fv(uniformLocation: Int, uniformValue: FloatArray) {
    if (ARB_SHADERS) {
      ARBShaderObjects.glUniform3fvARB(uniformLocation, uniformValue)
    } else {
      GL20.glUniform3fv(uniformLocation, uniformValue)
    }
  }

  private fun glUniform1b(uniformLocation: Int, uniformValue: Int) {
    if (ARB_SHADERS) {
      ARBShaderObjects.glUniform1iARB(uniformLocation, uniformValue)
    } else {
      GL20.glUniform1i(uniformLocation, uniformValue)
    }
  }

  private fun glValidateProgram(programShader: Int) {
    if (ARB_SHADERS) {
      ARBShaderObjects.glValidateProgramARB(programShader)
    } else {
      GL20.glValidateProgram(programShader)
    }
  }

  private fun glGetProgrami(programShader: Int, status: Int): Int {
    if (ARB_SHADERS) {
      return ARBShaderObjects.glGetObjectParameteriARB(programShader, status)
    } else {
      return GL20.glGetProgrami(programShader, status)
    }
  }

  private fun glAttachShader(programShader: Int, vertexShader: Int) {
    if (ARB_SHADERS) {
      ARBShaderObjects.glAttachObjectARB(programShader, vertexShader)
    } else {
      GL20.glAttachShader(programShader, vertexShader)
    }
  }

  private fun glLinkProgram(programShader: Int) {
    if (ARB_SHADERS) {
      ARBShaderObjects.glLinkProgramARB(programShader)
    } else {
      GL20.glLinkProgram(programShader)
    }
  }
}