
object Scene {

  val tiles: MutableList<Tile> = mutableListOf()

  init {
    for (i in -24 until 24) {
      for (j in -24 until 24) {
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