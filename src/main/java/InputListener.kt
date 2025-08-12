import org.lwjgl.glfw.GLFW.glfwSetKeyCallback
import org.lwjgl.glfw.GLFWKeyCallback

object InputListener {

  lateinit var keyCallback: GLFWKeyCallback

  init {
    keyCallback = object : GLFWKeyCallback() {
      override fun invoke(window: Long, key: Int, scanCode: Int, action: Int, mods: Int) {
        GameStatus.stopGame()
      }
    }
    glfwSetKeyCallback(Window.window, keyCallback)
  }
}