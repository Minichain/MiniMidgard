import org.lwjgl.glfw.GLFW.glfwCreateWindow
import org.lwjgl.glfw.GLFW.glfwMakeContextCurrent
import org.lwjgl.glfw.GLFW.glfwPollEvents
import org.lwjgl.glfw.GLFW.glfwShowWindow

object Window {

  var window: Long
    private set
  var width: Int
    private set
  var height: Int
    private set

  init {
    width = 1280
    height = 720
    window = glfwCreateWindow(Parameters.resolutionWidth, Parameters.resolutionHeight, "MiniMidgard", 0, 0)
    glfwShowWindow(window)
    glfwMakeContextCurrent(window)
    glfwPollEvents()
  }

  fun updateResolution(newWidth: Int, newHeight: Int) {

  }
}