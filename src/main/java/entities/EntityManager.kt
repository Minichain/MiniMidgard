package entities

object EntityManager {

  val entities: MutableList<Entity> = mutableListOf()

  fun loadEntities() {
    entities.add(Player)
    val amountOfEnemies = 500
    for (i in 0 until amountOfEnemies) {
      entities.add(Poring())
    }
  }

  fun updateEntities(timeElapsedMillis: Long) {
    entities.forEach {
      it.update(timeElapsedMillis)
    }
  }

  fun renderEntities() {
    entities.forEach {
      it.render()
    }
  }
}