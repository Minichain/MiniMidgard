import org.lwjgl.BufferUtils
import org.lwjgl.openal.AL
import org.lwjgl.openal.AL10
import org.lwjgl.openal.AL10.alBufferData
import org.lwjgl.openal.AL10.alGenBuffers
import org.lwjgl.openal.ALC
import org.lwjgl.openal.ALC10
import org.lwjgl.openal.ALC10.*
import org.lwjgl.stb.STBVorbis
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryStack.stackPop
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.system.libc.LibCStdlib
import sound.Sound
import sound.SoundType
import java.nio.FloatBuffer
import java.nio.IntBuffer

object SoundManager {

  private var device: Long? = null
  private var context: Long? = null

  private val listOfSounds: MutableList<Sound> = mutableListOf()

  /** Sounds **/
  lateinit var music01: Sound
    private set
  lateinit var poringEffect01: Sound
    private set

  private lateinit var source: IntBuffer
  private var listenerPos: FloatBuffer =
    BufferUtils
      .createFloatBuffer(3)
      .put(
        FloatArray(2).apply {
          this[0] = 0f
          this[1] = 0f
        }
      )

  init {
    val defaultDeviceName: String? = alcGetString(0, ALC10.ALC_DEFAULT_DEVICE_SPECIFIER)
    alcOpenDevice(defaultDeviceName).let { newDevice ->
      device = newDevice
      alcCreateContext(newDevice, IntArray(1)).let { newContext ->
        context = newContext

        alcMakeContextCurrent(newContext)

        val alcCapabilities = ALC.createCapabilities(newDevice)
        val alCapabilities = AL.createCapabilities(alcCapabilities)

        if (alCapabilities.OpenAL10) {
          //OpenAL 1.0 is supported
        }

        loadSounds()

        setupSources()
        setupListener()
      }
    }
  }

  private fun loadSounds() {
    loadSound("peaceful_forest", SoundType.Music)?.let { sound ->
      music01 = sound
      listOfSounds.add(sound)
    }
    loadSound("poring_move", SoundType.Effect)?.let { sound ->
      poringEffect01 = sound
      listOfSounds.add(sound)
    }
    source = BufferUtils.createIntBuffer(listOfSounds.size)
  }

  private fun loadSound(soundName: String, soundType: SoundType): Sound? {
    val soundPath = when (soundType) {
      SoundType.Music -> "music/$soundName"
      SoundType.Effect -> "effect/$soundName"
      SoundType.Ambience -> "ambience/$soundName"
    }
    return loadSound(soundPath)?.let { sound ->
      Sound(sound, listOfSounds.size, soundType)
    }
  }

  private fun loadSound(soundName: String): Int? {
    println("Loading sound... $soundName")
    val fileName = "src/main/resources/sounds/$soundName.ogg"
    stackPush()
    val channelsBuffer = MemoryStack.stackMallocInt(1)
    stackPush()
    val sampleRateBuffer = MemoryStack.stackMallocInt(1)

    STBVorbis.stb_vorbis_decode_filename(fileName, channelsBuffer, sampleRateBuffer)?.let { rawAudioBuffer ->
      //Retrieve the extra information that was stored in the buffers by the function
      val channels = channelsBuffer.get()
      val sampleRate = sampleRateBuffer.get()
      println("Loading sound... fileName: $fileName")
      println("Loading sound... sampleRate: $sampleRate")

      //Free the space we allocated earlier
      stackPop()
      stackPop()

      //Find the correct OpenAL format
      var format = -1
      if (channels == 1) {
        format = AL10.AL_FORMAT_MONO16
      } else if (channels == 2) {
        format = AL10.AL_FORMAT_STEREO16
      }

      //Request space for the buffer
      val bufferPointer = alGenBuffers()

      //Send the data to OpenAL
      alBufferData(bufferPointer, format, rawAudioBuffer, sampleRate)

      //Free the memory allocated by STB
      LibCStdlib.free(rawAudioBuffer)

      return bufferPointer
    }

    return null
  }

  private fun setupSources() {
    AL10.alGenSources(source)
    listOfSounds.forEach { sound ->
      AL10.alSourcei(source.get(sound.index), AL10.AL_BUFFER, sound.buffer)
      AL10.alSourcef(source.get(sound.index), AL10.AL_GAIN, 0.15f)
    }
  }

  private fun setupListener() {
    AL10.alListenerfv(AL10.AL_POSITION, listenerPos)
  }

  fun playSound(sound: Sound) {
    if (isPlaying(sound)) {
      return
    }
    AL10.alSourcePlay(source.get(sound.index))
  }

  fun isPlaying(soundBuffer: Sound): Boolean =
    AL10.alGetSourcei(source.get(soundBuffer.index), AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING
}