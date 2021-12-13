package laba7

import laba3.ShapeFactory

fun main() {
    val factory = ShapeFactory()
    val triangle = factory.createTriangle(3.0, 4.0, 5.0)
    val circle = factory.createCircle(7.4)
    val square = factory.createSquare(4.9)
    val rectangle = factory.createRectangle(4.0, 6.0)
    val jsonSerialization = JSONSerialization()
    val filepath =
        "C:\\Users\\varka\\Documents\\3 курс, 1 сем\\kotlin\\kotlin-course-template\\src\\main\\kotlin\\laba7\\test.txt"
    val filepath2 =
        "C:\\Users\\varka\\Documents\\3 курс, 1 сем\\kotlin\\kotlin-course-template\\src\\main\\kotlin\\laba7\\test2.txt"
    // demonstrate encoding-decoding:
    val encoded = jsonSerialization.encodeToString(triangle)
    println(encoded)
    val decoded = jsonSerialization.decodeFromString(encoded)
    println("do encoded and decoded equal: ${triangle == decoded}")
    // demonstrate encoding-decoding from file:
    jsonSerialization.encodeToFile(triangle, filepath)
    val decodedFromFile = jsonSerialization.decodeFromFile(filepath)
    println("do encoded to file and decoded from that file equal: ${triangle == decodedFromFile[0]}")

    // now create a list of figures:
    val shapeList = listOf(triangle, rectangle, circle, square)
    //now write shapeList to the file by filepath2 (file test2):
    jsonSerialization.encodeToFileAll(shapeList, filepath2)
    // then decode them back:
    val decodedShapeList = jsonSerialization.decodeFromFile(filepath2)
    println("analyses of equals figure lists:")
    for (i in decodedShapeList.indices) {
        println(shapeList[i] == decodedShapeList[i])
    }

}
