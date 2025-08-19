object EntityManager {

  val entities: MutableList<Entity> = mutableListOf()

  fun loadEntities() {
    entities.add(Player)
    val amountOfEnemies = 10
    for (i in 0 until amountOfEnemies) {
      entities.add(Poring())
    }
  }

  fun updateEntities(timeElapsedMillis: Long) {
    entities.forEach { it.update(timeElapsedMillis) }
    entities.sortByDescending { it.cameraCoordinates[1] }
  }

  fun renderEntities() {
    entities.forEach { it.render() }
  }
}