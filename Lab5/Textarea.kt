import java.io.File
import java.nio.charset.Charset
import java.util.*
import kotlin.collections.ArrayList

class IO {
  fun write(data: String, fileName: String = "data.txt", directory: String = "data", charset: Charset = Charsets.UTF_8) {
    if(!File(directory).exists()) File(directory).mkdir()
    else File(directory, fileName).writeText(data, charset)
  }
}
interface Button {
  val fileToSave: String
  fun modify(): String
}
class TextPlainButton(private val textarea: String): Button {
  override val fileToSave = "data.txt"
  override fun modify(): String = this.textarea
}
class TextTrimButton(private val textarea: String): Button {
  override val fileToSave = "trim.txt"
  override fun modify(): String = this.textarea.replace("\\s".toRegex(), "")
}
class TextEncodeButton(private val textarea: String): Button {
  override val fileToSave = "encode.txt"
  override fun modify(): String = Base64.getEncoder().encodeToString(this.textarea.toByteArray())
}
class UserComponent {
  private val queue = ArrayList<Button>()
  fun addToQueue(button: Button): UserComponent = apply { queue.add(button) }
  fun processButtons(): UserComponent = apply {
    queue.forEach { IO().write(it.modify(), it.fileToSave) }
    queue.clear()
  }
}

fun main(args: Array<String>) {
  try {
    val plainText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
    UserComponent()
            .addToQueue(TextEncodeButton(plainText))
            .addToQueue(TextTrimButton(plainText))
            .addToQueue(TextPlainButton(plainText))
            .processButtons()
  } catch (e: Exception) {
    println("EXCEPTION: ${e.message}")
  }
}
