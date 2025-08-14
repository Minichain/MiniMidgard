
object Scene {

  val tiles: MutableList<Tile> = mutableListOf()

  init {
    for (i in -8 until 8) {
      for (j in -8 until 8) {
        tiles.add(
          Tile(
            DoubleArray(3).apply {
              this[0] = Tile.width.toDouble() * i
              this[1] = Tile.height.toDouble() * j
              this[2] = 0.0
            }
          )
        )
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