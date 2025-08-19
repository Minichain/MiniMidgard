
object Scene {

  val tiles: MutableList<Tile> = mutableListOf()

  init {
    for (i in -24 until 24) {
      for (j in -24 until 24) {
        tiles.add(Tile(i, j))
      }
    }
  }

  fun update(timeElapsedMillis: Long) {
    tiles.forEach {
      it.update()
    }
  }

  fun render() {
    tiles.forEach {
      it.render()
    }
  }
}