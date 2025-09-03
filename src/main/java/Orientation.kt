
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
    fun fromVectors(facingVector: Vector3, cameraDirection: Vector3): Orientation {
      val playerFacingVector2d = Vector2(facingVector.x, facingVector.z).normalized()
      val playerFacingVectorLeft2d = Vector2(playerFacingVector2d.y, -playerFacingVector2d.x)
      val cameraFacingVector2d = Vector2(cameraDirection.x, cameraDirection.z).normalized()
      val dotProduct01 = playerFacingVector2d.dot(cameraFacingVector2d)
      val dotProduct02 = playerFacingVectorLeft2d.dot(cameraFacingVector2d)
      return if (dotProduct01 > 0.92f) {
        Up
      } else if (0.92f > dotProduct01 && dotProduct01 > 0.38f) {
        if (dotProduct02 > 0.0) UpRight
        else UpLeft
      } else if (0.38f > dotProduct01 && dotProduct01 > -0.38f) {
        if (dotProduct02 > 0.0) Right
        else Left
      } else if (-0.38f > dotProduct01 && dotProduct01 > -0.92f) {
        if (dotProduct02 > 0.0f) DownRight
        else DownLeft
      } else {
        Down
      }
    }
  }
}
