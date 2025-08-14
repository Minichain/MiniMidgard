import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.glfwSetKeyCallback
import org.lwjgl.glfw.GLFWKeyCallback

object InputListener {

  lateinit var keyCallback: GLFWKeyCallback

  init {
    keyCallback = object : GLFWKeyCallback() {
      override fun invoke(window: Long, key: Int, scanCode: Int, action: Int, mods: Int) {
        when (key) {
          GLFW.GLFW_KEY_ESCAPE -> {
            GameStatus.stopGame()
          }
        }
      }
    }
    glfwSetKeyCallback(Window.window, keyCallback)
  }
}