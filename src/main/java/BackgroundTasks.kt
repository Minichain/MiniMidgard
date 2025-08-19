import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun runBackgroundTask() {
  CoroutineScope(Dispatchers.IO).launch {
    var iteration = 0
    while (true) {
      println("This is running on a thread... iteration: $iteration")
      delay(1000)
      iteration++
    }
  }
}