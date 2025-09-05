import entities.EntityManager
import entities.Player
import org.lwjgl.opengl.GL11.GL_DEPTH_TEST
import org.lwjgl.opengl.GL11.glDisable
import org.lwjgl.opengl.GL11.glEnable
import sound.SoundManager
import ui.TextRendering

object Game {

  private var fps: Float = 0f

  fun startGame() {
    println("Starting game...")
    GraphicsManager
    EntityManager.loadEntities()
    SoundManager.playSound(SoundManager.music01)
  }

  fun update(timeElapsedMillis: Long) {
    fps = 1000f / timeElapsedMillis.toFloat()
    Camera.update(timeElapsedMillis)
    EntityManager.updateEntities(timeElapsedMillis)
    Scene.update(timeElapsedMillis)
  }

  fun render() {
    GraphicsManager.prepareFrame()
    GraphicsManager.updateShadersUniforms()
    GraphicsManager.useShader(1)

    glEnable(GL_DEPTH_TEST)
    Scene.render()
    EntityManager.renderEntities()
    glDisable(GL_DEPTH_TEST)

    //Debug text
    GraphicsManager.useShader(2)
    TextRendering.renderText(-250f, -100f, "Mouse coordinates: ${InputListener.currentMouseCoordinates}", 4f)
    TextRendering.renderText(-250f, -50f, "FPS: $fps", 4f)
    TextRendering.renderText(-250f, 0f, "Entities: ${EntityManager.entities.size}", 4f)
    TextRendering.renderText(-250f, 50f, "Player world coordinates: ${Player.worldCoordinates}", 4f)
    TextRendering.renderText(-250f, 100f, "Camera world coordinates: ${Camera.cameraPosition}", 4f)

    GraphicsManager.releaseCurrentShader()
  }

  fun stopGame() {
    println("Stopping game...")
  }
}