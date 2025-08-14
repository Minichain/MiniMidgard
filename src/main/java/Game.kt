object Game {

  fun startGame() {
    println("Starting game...")
    GraphicsManager.prepareOpenGL()
    EntityManager.loadEntities()
  }

  fun update(timeElapsedMillis: Long) {
    EntityManager.updateEntities(timeElapsedMillis)
    Scene.update(timeElapsedMillis)
    Camera.update(timeElapsedMillis)
  }

  fun render() {
    GraphicsManager.prepareFrame()
    GraphicsManager.updateShadersUniforms()
    GraphicsManager.useShader(1)

    Scene.render()
    EntityManager.renderEntities()

    GraphicsManager.releaseCurrentShader()
  }

  fun stopGame() {
    println("Stopping game...")
  }
}