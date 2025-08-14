import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWWindowPosCallback
import org.lwjgl.opengl.GL11.GL_FALSE
import org.lwjgl.opengl.GL11.glViewport

object Window {

  var window: Long
    private set
  var resolution = Parameters.resolution
    private set
  private val position = IntArray(2)

  init {
    glfwWindowHint(GLFW_RESIZABLE, GL_FALSE)
    window = glfwCreateWindow(resolution.width, resolution.height, "MiniMidgard", 0, 0)
    glfwShowWindow(window)
    glfwMakeContextCurrent(window)
    glfwPollEvents()

    val windowPosCallback = object : GLFWWindowPosCallback() {
      override fun invoke(window: Long, x: Int, y: Int) {
        setWindowPosition(x, y)
      }
    }
    glfwSetWindowPosCallback(window, windowPosCallback)
  }

  private fun setWindowPosition(x: Int, y: Int) {
    glfwSetWindowPos(window, x, y)
    position[0] = x
    position[1] = y
  }

  private fun setWindowSize(resolution: Resolution) {
    glfwSetWindowSize(window, resolution.width, Window.resolution.height)
    glViewport(0, 0, resolution.width, resolution.height)
    this.resolution = resolution
  }
}