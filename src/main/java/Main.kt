import org.lwjgl.glfw.GLFW
import java.io.File
import kotlin.system.exitProcess

fun main() {
  println("MiniMidgard START")
  System.setProperty("org.lwjgl.librarypath", File("natives/windows/x64").absolutePath)
  initializeGLFW()
  InputListener
  Game.startGame()
  println("OS Name " + System.getProperty("os.name"))
  println("OS Version " + System.getProperty("os.version"))
  runGameLoopUntilStopped()
  exit()
}

private fun initializeGLFW() {
  if (!GLFW.glfwInit()) {
    println("GLFW Failed to initialize!")
    exitProcess(0)
  }
}

private fun runGameLoopUntilStopped() {
  var timeElapsedNanos: Long = 0
  var lastUpdateTime = System.nanoTime()
  var maxTimeBetweenFrames: Long
  while (gameIsRunning()) {
    maxTimeBetweenFrames = (1000000000.0 / Parameters.fps).toLong()
    updateAndRender((timeElapsedNanos / 1000000.0).toLong())
    //Wait time until processing next frame. FPS locked.
    timeElapsedNanos = System.nanoTime() - lastUpdateTime
    while (timeElapsedNanos < maxTimeBetweenFrames) {
      timeElapsedNanos = System.nanoTime() - lastUpdateTime
    }
    lastUpdateTime = System.nanoTime()
  }


}

private fun gameIsRunning() =
  !GLFW.glfwWindowShouldClose(Window.window) && GameStatus.isRunning

private fun updateAndRender(timeElapsedMillis: Long) {
  Game.update(timeElapsedMillis)
  Game.render()
  GLFW.glfwSwapBuffers(Window.window)
  GLFW.glfwPollEvents()
}

private fun exit() {
  Game.stopGame()
  GLFW.glfwTerminate()
  exitProcess(0)
}
