package laba7

import laba3.ShapeFactory

fun main() {
    val factory = ShapeFactory()
    val shape = factory.createTriangle(3.0, 4.0, 5.0)
    val jsonSerialization = JSONSerialization()
    val filepath =
        "C:\\Users\\varka\\Documents\\3 курс, 1 сем\\kotlin\\kotlin-course-template\\src\\main\\kotlin\\laba7\\test.txt"
    // demonstrate encoding-decoding:
    val encoded = jsonSerialization.encodeToString(shape)
    println(encoded)
    val decoded = jsonSerialization.decodeFromString(encoded)
    println("do encoded and decoded equal: ${shape == decoded}")
    // demonstrate encoding-decoding from file:
    jsonSerialization.encodeToFile(shape, filepath)
    val decodedFromFile = jsonSerialization.decodeFromFile(filepath)
    println("do encoded to file and decoded from that file equal: ${shape == decodedFromFile}")
}
