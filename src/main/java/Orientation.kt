import kotlin.math.atan2

enum class Orientation {
  Down,
  DownLeft,
  Left,
  UpLeft,
  Up,
  UpRight,
  Right,
  DownRight;

  companion object {
    fun from2dVector(x: Double, y: Double): Orientation {
      val normalizedDegrees = (Math.toDegrees(atan2(y, x)) + 360) % 360
      return when {
        normalizedDegrees !in 22.5..<337.5 -> Right
        normalizedDegrees < 67.5 -> UpRight
        normalizedDegrees < 112.5 -> Up
        normalizedDegrees < 157.5 -> UpLeft
        normalizedDegrees < 202.5 -> Left
        normalizedDegrees < 247.5 -> DownLeft
        normalizedDegrees < 292.5 -> Down
        else -> DownRight
      }
    }
  }
}
