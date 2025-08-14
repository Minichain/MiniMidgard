import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.glfwSetKeyCallback
import org.lwjgl.glfw.GLFW.glfwSetScrollCallback
import org.lwjgl.glfw.GLFWKeyCallback
import org.lwjgl.glfw.GLFWScrollCallback

object InputListener {

  //TODO This is here for testing purposes. Remove later.
  var movingUp: Boolean = false
  var movingDown: Boolean = false
  var movingLeft: Boolean = false
  var movingRight: Boolean = false
  var sitting: Boolean = false

  private var keyCallback: GLFWKeyCallback = object : GLFWKeyCallback() {
    override fun invoke(window: Long, key: Int, scanCode: Int, action: Int, mods: Int) {
      when (key) {
        GLFW.GLFW_KEY_ESCAPE -> {
          GameStatus.stopGame()
        }
        GLFW.GLFW_KEY_W -> {
          movingUp = action == 1 || action == 2
        }
        GLFW.GLFW_KEY_A -> {
          movingLeft = action == 1 || action == 2
        }
        GLFW.GLFW_KEY_S -> {
          movingDown = action == 1 || action == 2
        }
        GLFW.GLFW_KEY_D -> {
          movingRight = action == 1 || action == 2
        }
        GLFW.GLFW_KEY_INSERT -> {
          if (action == 1 || action == 2) {
            sitting = !sitting
          }
        }
      }
    }
  }

  private var scrollCallback: GLFWScrollCallback = object : GLFWScrollCallback() {
    override fun invoke(window: Long, p1: Double, p2: Double) {
      if (p2 > 0.0) Camera.increaseZoom()
      else Camera.decreaseZoom()
    }
  }

  init {
    glfwSetKeyCallback(Window.window, keyCallback)
    glfwSetScrollCallback(Window.window, scrollCallback)
  }
}