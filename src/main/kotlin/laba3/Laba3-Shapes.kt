package laba3

import kotlin.math.sqrt
import kotlin.random.Random

enum class Shapes {
    CIRCLE,
    SQUARE,
    RECTANGLE,
    TRIANGLE
}

interface CalcShape {
    fun calcArea(): Double
    fun calcPerimeter(): Double
}

interface CalcCircle : CalcShape {
    val radius: Double

    override fun calcArea(): Double {
        return Math.PI * radius * radius
    }

    override fun calcPerimeter(): Double {
        return 2 * Math.PI * radius
    }
}

interface CalcSquare : CalcShape {
    val sideLength: Double

    override fun calcArea(): Double {
        return sideLength * sideLength
    }

    override fun calcPerimeter(): Double {
        return sideLength * 4
    }
}

interface CalcRectangle : CalcShape {
    val firstSideLength: Double
    val secondSideLength: Double

    override fun calcArea(): Double {
        return firstSideLength * secondSideLength
    }

    override fun calcPerimeter(): Double {
        return firstSideLength * 2 + secondSideLength * 2
    }
}

interface CalcTriangle : CalcShape {
    val firstSideLength: Double
    val secondSideLength: Double
    val thirdSideLength: Double

    override fun calcArea(): Double {
        val p = calcPerimeter() / 2 // want to calculate area using Heron's formula
        return sqrt(p * (p - firstSideLength) * (p - secondSideLength) * (p - thirdSideLength))
    }

    override fun calcPerimeter(): Double {
        return firstSideLength + secondSideLength + thirdSideLength
    }
}

class Circle(override var radius: Double) : CalcCircle {
    init {
        if (this.radius <= 0) throw IllegalArgumentException("radius should be a positive number")
    }
}

class Square(override var sideLength: Double) : CalcSquare {
    init {
        if (this.sideLength <= 0) throw IllegalArgumentException("square side should be a positive number")
    }
}

class Rectangle(
    override val firstSideLength: Double,
    override val secondSideLength: Double
) : CalcRectangle {
    init {
        if (this.firstSideLength <= 0 || this.secondSideLength <= 0)
            throw IllegalArgumentException("rectangle sides should be positive")
    }
}

class Triangle(
    override val firstSideLength: Double,
    override val secondSideLength: Double,
    override val thirdSideLength: Double
) : CalcTriangle {
    init {
        if (this.firstSideLength <= 0 || this.secondSideLength <= 0 || this.thirdSideLength <= 0)
            throw IllegalArgumentException("triangle sides should be positive")
        if (firstSideLength >= secondSideLength + thirdSideLength
            || secondSideLength >= firstSideLength + thirdSideLength
            || thirdSideLength >= firstSideLength + secondSideLength
        ) throw IllegalArgumentException("triangle with data length of the sides doesn't exist")
    }
}

interface ShapeFactoryInterface {
    fun createCircle(radius: Double): Circle
    fun createSquare(sideLength: Double): Square
    fun createRectangle(firstSideLength: Double, secondSideLength: Double): Rectangle

    fun createTriangle(
        firstSideLength: Double,
        secondSideLength: Double,
        thirdSideLength: Double
    ): Triangle

    fun createRandomCircle(): Circle
    fun createRandomSquare(): Square
    fun createRandomRectangle(): Rectangle
    fun createRandomTriangle(): Triangle

    fun createRandomShape(): CalcShape
}

class ShapeFactory : ShapeFactoryInterface {
    override fun createCircle(radius: Double): Circle {
        return Circle(radius)
    }

    override fun createSquare(sideLength: Double): Square {
        return Square(sideLength)
    }

    override fun createRectangle(firstSideLength: Double, secondSideLength: Double): Rectangle {
        return Rectangle(firstSideLength, secondSideLength)
    }

    override fun createTriangle(
        firstSideLength: Double,
        secondSideLength: Double,
        thirdSideLength: Double
    ): Triangle {
        return Triangle(firstSideLength, secondSideLength, thirdSideLength)
    }

    override fun createRandomCircle(): Circle {
        return createCircle(Random.nextDouble() * 100)
    }

    override fun createRandomSquare(): Square {
        return createSquare(Random.nextDouble() * 100)
    }

    override fun createRandomRectangle(): Rectangle {
        return createRectangle(
            Random.nextDouble() * 100, // Random.nextDouble(Double.MIN_VALUE, Double.MAX_VALUE),
            Random.nextDouble() * 100 // Random.nextDouble(Double.MIN_VALUE, Double.MAX_VALUE)
        )
    }

    override fun createRandomTriangle(): Triangle { // get two random side lengths and one cos-value
        val cos = Random.nextDouble(0.0, 1.0) - Random.nextDouble(0.0, 1.0)
        // cos is defined like that to include 0 and exclude -1 and 1
        val side1 = Random.nextDouble() * 100
        val side2 = Random.nextDouble() * 100
        val side3 = sqrt(side1 * side1 + side2 * side2 - 2 * side1 * side2 * cos) // using cos theorem
        return (createTriangle(side1, side2, side3))
    }

    override fun createRandomShape(): CalcShape {
        return when (Shapes.values().random()) {
            Shapes.CIRCLE -> createRandomCircle()
            Shapes.SQUARE -> createRandomSquare()
            Shapes.RECTANGLE -> createRandomRectangle()
            Shapes.TRIANGLE -> createRandomTriangle()
        }
    }
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
