import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val scope = CoroutineScope(Dispatchers.IO)

fun runBackgroundTask() {
  scope.runTask01()
}

private var job01: Job? = null

private fun CoroutineScope.runTask01() {
  job01 = launch {
    var iteration = 0
    while (true) {
      println("TASK 01 This is running on a thread... iteration: $iteration")
//      println("Sending player coordinates. ${Player.worldCoordinates.print()}")
      delay(1000)
      iteration++
    }
  }
}

fun cancelScopes() {
  scope.cancel()
}