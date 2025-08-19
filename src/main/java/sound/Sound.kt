package sound

data class Sound(
  val name: String,
  val buffer: Int,
  val index: Int,
  val soundType: SoundType
)