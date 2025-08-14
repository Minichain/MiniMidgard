import kotlin.math.pow
import kotlin.math.sqrt

fun Int.fractionOf(total: Int): Float =
  this.toFloat() / total.toFloat()

fun DoubleArray.normalizeVector(): DoubleArray {
  var vectorNormalized = DoubleArray(size)
  val vectorModule = module()
  if (vectorModule > 0) {
    for (i in 0 until size) {
      vectorNormalized[i] = this[i] / vectorModule
    }
  } else {
    vectorNormalized = this
  }
  return vectorNormalized
}

fun DoubleArray.module(): Double {
  var sum = 0.0
  forEachIndexed { i, _ ->
    sum += this[i].pow(2.0)
  }
  return sqrt(sum)
}
