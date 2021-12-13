package laba7

import kotlinx.serialization.decodeFromString
import laba3.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.*
import java.io.*

class JSONSerialization {
    val json = Json {
        prettyPrint = true
        serializersModule = SerializersModule {
            polymorphic(CalcShape::class) {
                subclass(Circle::class)
                subclass(Square::class)
                subclass(Rectangle::class)
                subclass(Triangle::class)
            }
        }
    }

    fun encodeToString(shape: CalcShape): String = json.encodeToString(shape) // encode shape to string

    fun encodeToFile(shape: CalcShape, filename: String) { // encode shape to file
        try {
            File(filename).bufferedWriter().use { it.write(encodeToString(shape)) }
        } catch (exception: IOException) {
            println("problem with file: ${exception.message}")
        }
    }

    fun decodeFromString(string: String): CalcShape = json.decodeFromString(string) // decode string to shape

    fun decodeFromFile(fullfilename: String): CalcShape { // decode string from file to shape
        var decoded: CalcShape? = null
        try {
            val filetext = File(fullfilename).bufferedReader().readText()
            decoded = decodeFromString(filetext)
        } catch (exception: IOException) {
            println("file not found: ${exception.message}")
        } catch (exception: IllegalArgumentException) {
            println("file data doesn't represent a shape: ${exception.message}")
        } finally {
            decoded ?: throw IllegalStateException("decodeFromString returned null")
        }
        return decoded
    }
}
