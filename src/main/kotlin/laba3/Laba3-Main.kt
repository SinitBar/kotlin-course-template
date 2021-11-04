package laba3

fun main() {
    val factory = ShapeFactory()
    val shapeList = generateShapes(factory)
    var maxPerimeter = 0.0
    var maxArea = 0.0
    var minPerimeter = shapeList[0].calcPerimeter()
    var minArea = shapeList[0].calcArea()
    var sumPerimeter = 0.0
    var sumArea = 0.0

    for (i in shapeList.indices) {
        val area = shapeList[i].calcArea()
        val perimeter = shapeList[i].calcPerimeter()
        println(
            "perimeter = $perimeter, area = $area, shape: " +
                    shapeList[i].toString().substringAfter('.').substringBefore('@')
        )
        maxPerimeter = if (perimeter > maxPerimeter) perimeter else maxPerimeter
        maxArea = if (area > maxArea) area else maxArea
        minPerimeter = if (perimeter < minPerimeter) perimeter else minPerimeter
        minArea = if (area < minArea) area else minArea
        sumPerimeter += perimeter
        sumArea += area
    }
    println()
    println("min perimeter = $minPerimeter, max perimeter = $maxPerimeter, sum perimeter = $sumPerimeter")
    println("min area = $minArea, max area = $maxArea, sum area = $sumArea")
}