import java.io.File
import java.io.InputStream

class Serialization(private val filename: String = "data.txt") {
    fun serialize(data: String) {
        File(this.filename).writeText(data)
    }
}
class Deserialization(private val filename: String = "data.txt") {
    fun deserialize(): String {
        val inputStream: InputStream = File(this.filename).inputStream()
        return inputStream.bufferedReader().use { it.readText() }
    }
}

fun main(args: Array<String>) {
    val str = Deserialization().deserialize()
    print(str)
    Serialization().serialize(str)
}