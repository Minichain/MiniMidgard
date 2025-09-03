import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.GLFW_MOD_SHIFT
import org.lwjgl.glfw.GLFW.GLFW_PRESS
import org.lwjgl.glfw.GLFW.GLFW_RELEASE
import org.lwjgl.glfw.GLFW.GLFW_REPEAT
import org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback
import org.lwjgl.glfw.GLFW.glfwSetKeyCallback
import org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback
import org.lwjgl.glfw.GLFW.glfwSetScrollCallback
import org.lwjgl.glfw.GLFWCursorPosCallback
import org.lwjgl.glfw.GLFWKeyCallback
import org.lwjgl.glfw.GLFWMouseButtonCallback
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
          movingUp = action == GLFW_PRESS || action == GLFW_REPEAT
        }
        GLFW.GLFW_KEY_A -> {
          movingLeft = action == GLFW_PRESS || action == GLFW_REPEAT
        }
        GLFW.GLFW_KEY_S -> {
          movingDown = action == GLFW_PRESS || action == GLFW_REPEAT
        }
        GLFW.GLFW_KEY_D -> {
          movingRight = action == GLFW_PRESS || action == GLFW_REPEAT
        }
        GLFW.GLFW_KEY_INSERT -> {
          if (action == GLFW_PRESS || action == GLFW_REPEAT) {
            sitting = !sitting
          }
        }
      }
    }
  }

  private var scrollCallback: GLFWScrollCallback = object : GLFWScrollCallback() {
    override fun invoke(window: Long, p1: Double, p2: Double) {
      if (p2 > 0f) Camera.changeCameraDistance(-10.0f)
      else Camera.changeCameraDistance(10.0f)
    }
  }

  private var mouseRightButtonHold = false
  private var mouseRightButtonPlusShiftHold = false
  private var lastXMousePosition: Float? = null
  private var lastYMousePosition: Float? = null

  private var mouseCallback: GLFWMouseButtonCallback = object : GLFWMouseButtonCallback() {
    override fun invoke(window: Long, button: Int, action: Int, mods: Int) {
      when (button) {
        GLFW.GLFW_MOUSE_BUTTON_RIGHT -> {
          if (mods == GLFW_MOD_SHIFT) {
            println("mouseRightButtonPlusShiftHold")
            mouseRightButtonPlusShiftHold = action == GLFW_PRESS || action == GLFW_REPEAT
            if (action == GLFW_RELEASE) lastYMousePosition = null
          } else {
            mouseRightButtonHold = action == GLFW_PRESS || action == GLFW_REPEAT
            if (action == GLFW_RELEASE) lastXMousePosition = null
          }
        }
      }
    }
  }

  private var cursorPosCallback: GLFWCursorPosCallback = object : GLFWCursorPosCallback() {
    override fun invoke(window: Long, x: Double, y: Double) {
      if (mouseRightButtonHold) {
        if (lastXMousePosition == null) lastXMousePosition = x.toFloat()
        Camera.changeCameraPanAngle(0.01f * (lastXMousePosition!! - x.toFloat()))
        lastXMousePosition = x.toFloat()
      }
      if (mouseRightButtonPlusShiftHold) {
        if (lastYMousePosition == null) lastYMousePosition = y.toFloat()
        Camera.changeCameraTiltAngle(0.01f * (lastYMousePosition!! - y.toFloat()))
        lastYMousePosition = y.toFloat()
      }
    }
  }

  init {
    glfwSetKeyCallback(Window.window, keyCallback)
    glfwSetScrollCallback(Window.window, scrollCallback)
    glfwSetMouseButtonCallback(Window.window, mouseCallback)
    glfwSetCursorPosCallback(Window.window, cursorPosCallback)
  }
}