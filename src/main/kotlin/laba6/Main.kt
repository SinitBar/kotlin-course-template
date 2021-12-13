package laba6

import laba3.*

fun main() {
    val factory = ShapeFactory()
    val shapeList = generateShapes(factory)
    val collector = ShapeCollector<CalcShape>()
    for (shape in shapeList)
        collector.add(shape)

    var listOfAllSorted = collector.getAllSorted(ShapeComparators.CompareByAreaAscending)
    println("\n shapes sorted by area ascending: \n")
    printList(listOfAllSorted)
    listOfAllSorted = collector.getAllSorted(ShapeComparators.CompareByAreaDescending)
    println("\n shapes sorted by area descending: \n")
    printList(listOfAllSorted)
    listOfAllSorted = collector.getAllSorted(ShapeComparators.CompareByPerimeterAscending)
    println("\n shapes sorted by perimeter ascending: \n")
    printList(listOfAllSorted)
    listOfAllSorted = collector.getAllSorted(ShapeComparators.CompareByPerimeterDescending)
    println("\n shapes sorted by perimeter descending: \n")
    printList(listOfAllSorted)
    val typed = collector.getAllByClass(Square::class.java)
    println("\n chosen squares from list of shapes: \n")
    printList(typed)
    val typed2 = collector.getAllByClass(Triangle::class.java)
    println("\n chosen triangles from list of shapes: \n")
    printList(typed2)

    val collector2 = ShapeCollector<Circle>()
    val c1 = factory.createCircle(3.0)
    val c2 = factory.createCircle(4.5)
    val c3 = factory.createCircle(6.0)

    collector2.addAll(listOf(c1, c2, c3))

    var l = collector2.getAllSorted(ShapeComparators.CompareCirclesByRadiusAscending)
    println("\n circles sorted by radius ascending: \n")
    printList(l)
    l = collector2.getAllSorted(ShapeComparators.CompareCirclesByRadiusDescending)
    println("\n circles sorted by radius descending: \n")
    printList(l)
}

fun printList(list: List<CalcShape>) {
    for (element in list)
        println(
            "shape: ${
                element.toString().substringAfter('.').substringBefore('@')
            }, area = ${element.calcArea()}, perimeter = ${element.calcPerimeter()}"
        )
}