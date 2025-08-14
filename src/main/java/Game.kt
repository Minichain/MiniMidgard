object Game {

  var player: Player? = null

  fun startGame() {
    println("Starting game...")
  }

  fun update(timeElapsedMillis: Long) {

  }

  fun render() {
    GraphicsManager.prepareFrame()
    GraphicsManager.updateShadersUniforms()
    if (player == null) {
      player = Player()
    }
    player?.drawSprite()
  }

  fun stopGame() {
    println("Stopping game...")
  }
}