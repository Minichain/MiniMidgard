/*
 * The MIT License (MIT)
 *
 * Copyright © 2014-2017, Heiko Brumme
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
import org.lwjgl.opengl.GL20
import org.lwjgl.stb.STBImage.*
import org.lwjgl.system.MemoryStack
import java.nio.ByteBuffer

/**
 * This class represents a texture.
 *
 * @author Heiko Brumme
 */
class Texture {
  private var id: Int? = null
  var width: Int = 0
    private set
  var height: Int = 0
    private set

  init {
    id = GL20.glGenTextures()
  }

  fun bind() {
    id?.let { GL20.glBindTexture(GL20.GL_TEXTURE_2D, it) }
  }

  /**
   * Uploads image data with specified width and height.
   *
   * @param width  Width of the image
   * @param height Height of the image
   * @param data   Pixel data of the image
   */
  fun uploadData(width: Int, height: Int, data: ByteBuffer?) {
    uploadData(GL20.GL_RGBA, width, height, GL20.GL_RGBA, data)
  }

  /**
   * Uploads image data with specified internal format, width, height and
   * image format.
   *
   * @param internalFormat Internal format of the image data
   * @param width          Width of the image
   * @param height         Height of the image
   * @param format         Format of the image data
   * @param data           Pixel data of the image
   */
  fun uploadData(internalFormat: Int, width: Int, height: Int, format: Int, data: ByteBuffer?) {
    GL20.glTexImage2D(GL20.GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, GL20.GL_UNSIGNED_BYTE, data)
  }

  /**
   * Delete the texture.
   */
  fun delete() {
    id?.let { GL20.glDeleteTextures(it) }
  }

  companion object {
    /**
     * Creates a texture with specified width, height and data.
     *
     * @param width  Width of the texture
     * @param height Height of the texture
     * @param data   Picture Data in RGBA format
     *
     * @return Texture from the specified data
     */
    fun createTexture(width: Int, height: Int, data: ByteBuffer?): Texture {
      val texture = Texture()
      texture.width = width
      texture.height = height

      texture.bind()

      GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_S, GL20.GL_CLAMP_TO_BORDER)
      GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_T, GL20.GL_CLAMP_TO_BORDER)
      GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MIN_FILTER, GL20.GL_NEAREST)
      GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MAG_FILTER, GL20.GL_NEAREST)

      texture.uploadData(GL20.GL_RGBA, width, height, GL20.GL_RGBA, data)

      return texture
    }

    /**
     * Load texture from file.
     *
     * @param path File path of the texture
     *
     * @return Texture from specified file
     */
    fun loadTexture(path: String): Texture {
      val image: ByteBuffer?
      val width: Int
      val height: Int
      MemoryStack.stackPush().use { stack ->
        /* Prepare image buffers */
        val w = stack.mallocInt(1)
        val h = stack.mallocInt(1)
        val comp = stack.mallocInt(1)

        /* Load image */
        stbi_set_flip_vertically_on_load(true)
        image = stbi_load(path, w, h, comp, 4)
        if (image == null) {
          throw RuntimeException("Failed to load a texture file!" + System.lineSeparator() + stbi_failure_reason())
        }

        /* Get width and height of image */
        width = w.get()
        height = h.get()
        println("Texture loaded successfully. width: $width, height: $height")
      }
      return createTexture(width, height, image)
    }
  }
}