import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

object FileUtils {

  @Throws(Exception::class)
  fun readFileAsString(filename: String): String {
    val source = StringBuilder()
    val fileInputStream = FileInputStream(filename)
    var exception: Exception? = null
    val reader: BufferedReader?
    try {
      reader = BufferedReader(InputStreamReader(fileInputStream, "UTF-8"))
      var innerExc: Exception? = null
      try {
        var line: String?
        while ((reader.readLine().also { line = it }) != null) {
          source.append(line).append('\n')
        }
      } catch (e: Exception) {
        exception = e
      } finally {
        try {
          reader.close()
        } catch (exc: Exception) {
          if (innerExc == null) {
            innerExc = exc
          } else {
            exc.printStackTrace()
          }
        }
      }

      if (innerExc != null) {
        throw innerExc
      }
    } catch (e: Exception) {
      exception = e
    } finally {
      try {
        fileInputStream.close()
      } catch (e: Exception) {
        if (exception == null) {
          exception = e
        } else {
          e.printStackTrace()
        }
      }

      if (exception != null) {
        throw exception
      }
    }

    return source.toString()
  }
}
