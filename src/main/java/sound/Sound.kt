package sound

class Sound(
  buffer: Int,
  index: Int,
  soundType: SoundType
) {

  var buffer: Int
    private set
  var index: Int
    private set
  var soundType: SoundType
    private set

  init {
    this.buffer = buffer
    this.index = index
    this.soundType = soundType
  }

}