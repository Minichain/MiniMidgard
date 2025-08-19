
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
    fun fromVectors(facingVector: DoubleArray, cameraDirection: DoubleArray): Orientation {
      val playerFacingVector2d = doubleArrayOf(facingVector[0], facingVector[2]).normalizeVector()
      val playerFacingVectorLeft2d = doubleArrayOf(playerFacingVector2d[1], -playerFacingVector2d[0])
      val cameraFacingVector2d = doubleArrayOf(cameraDirection[0], cameraDirection[2]).normalizeVector()
      val dotProduct01 = playerFacingVector2d.dot(cameraFacingVector2d)
      val dotProduct02 = playerFacingVectorLeft2d.dot(cameraFacingVector2d)
      return if (dotProduct01 > 0.92) {
        Up
      } else if (0.92 > dotProduct01 && dotProduct01 > 0.38) {
        if (dotProduct02 > 0.0) UpLeft
        else UpRight
      } else if (0.38 > dotProduct01 && dotProduct01 > -0.38) {
        if (dotProduct02 > 0.0) Left
        else Right
      } else if (-0.38 > dotProduct01 && dotProduct01 > -0.92) {
        if (dotProduct02 > 0.0) DownLeft
        else DownRight
      } else {
        Down
      }
    }
  }
}
