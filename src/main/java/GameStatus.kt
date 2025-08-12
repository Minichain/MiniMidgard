object GameStatus {
  var isRunning: Boolean
    private set
  var isPaused: Boolean
    private set

  /** Time game has been running in milliseconds **/
  var runningTime: Long
    private set

  init {
    isRunning = true
    isPaused = false
    runningTime = 0L
  }

  fun stopGame() {
    isRunning = false
  }
}