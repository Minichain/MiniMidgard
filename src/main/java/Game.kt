object Game {

  var player: Player? = null
  var poring: Poring? = null

  fun startGame() {
    println("Starting game...")
  }

  fun update(timeElapsedMillis: Long) {

  }

  fun render() {
    GraphicsManager.prepareFrame()
    GraphicsManager.updateShadersUniforms()
    if (player == null) {
      poring = Poring()
      player = Player()
    }
    player?.render()
    poring?.render()
  }

  fun stopGame() {
    println("Stopping game...")
  }
}