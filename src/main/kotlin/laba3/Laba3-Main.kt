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

fun generateShapes(factory: ShapeFactory): Array<CalcShape> {
    return arrayOf(
        // 3 circles
        factory.createCircle(3.0),
        factory.createCircle(4.5),
        factory.createCircle(6.0),
        // 3 squares
        factory.createSquare(3.0),
        factory.createSquare(4.5),
        factory.createSquare(6.0),
        // 3 rectangles
        factory.createRectangle(3.0, 10.0),
        factory.createRectangle(4.5, 5.2),
        factory.createRectangle(6.0, 1.4),
        // 3 triangles
        factory.createTriangle(3.0, 4.0, 5.0),
        factory.createTriangle(9.0, 5.0, 6.0),
        factory.createTriangle(15.0, 15.0, 15.0),
        // 4 random-sized shapes of every type and 1 random shape, could be commented
        factory.createRandomCircle(),
        factory.createRandomSquare(),
        factory.createRandomRectangle(),
        factory.createRandomTriangle(),
        factory.createRandomShape()
    )
}