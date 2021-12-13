package laba3

import kotlin.math.sqrt
import kotlin.random.Random
import kotlinx.serialization.Serializable

enum class Shapes {
    CIRCLE,
    SQUARE,
    RECTANGLE,
    TRIANGLE
}

interface CalcShape {
    fun calcArea(): Double
    fun calcPerimeter(): Double
    override fun equals(other: Any?): Boolean
}

@Serializable
class Circle(val radius: Double) : CalcShape {
    init {
        if (this.radius <= 0) throw IllegalArgumentException("radius should be a positive number")
    }

    override fun calcArea(): Double {
        return Math.PI * radius * radius
    }

    override fun calcPerimeter(): Double {
        return 2 * Math.PI * radius
    }

    override fun equals(other: Any?): Boolean {
        return (other != null && other is Circle && other.radius == radius)
    }

    override fun hashCode(): Int {
        return radius.hashCode()
    }
}

@Serializable
class Square(var sideLength: Double) : CalcShape {
    init {
        if (this.sideLength <= 0) throw IllegalArgumentException("square side should be a positive number")
    }

    override fun calcArea(): Double {
        return sideLength * sideLength
    }

    override fun calcPerimeter(): Double {
        return sideLength * 4
    }

    override fun equals(other: Any?): Boolean {
        return (other != null && other is Square && other.sideLength == sideLength)
    }

    override fun hashCode(): Int {
        return sideLength.hashCode()
    }
}

@Serializable
class Rectangle(
    val firstSideLength: Double,
    val secondSideLength: Double
) : CalcShape {
    init {
        if (this.firstSideLength <= 0 || this.secondSideLength <= 0)
            throw IllegalArgumentException("rectangle sides should be positive")
    }

    override fun calcArea(): Double {
        return firstSideLength * secondSideLength
    }

    override fun calcPerimeter(): Double {
        return firstSideLength * 2 + secondSideLength * 2
    }

    override fun equals(other: Any?): Boolean {
        return (other != null && other is Rectangle &&
                other.firstSideLength == firstSideLength && other.secondSideLength == secondSideLength
                )
    }

    override fun hashCode(): Int {
        var result = firstSideLength.hashCode()
        result = 31 * result + secondSideLength.hashCode()
        return result
    }
}

@Serializable
class Triangle(
    val firstSideLength: Double,
    val secondSideLength: Double,
    val thirdSideLength: Double
) : CalcShape {
    init {
        if (this.firstSideLength <= 0 || this.secondSideLength <= 0 || this.thirdSideLength <= 0)
            throw IllegalArgumentException("triangle sides should be positive")
        if (firstSideLength >= secondSideLength + thirdSideLength
            || secondSideLength >= firstSideLength + thirdSideLength
            || thirdSideLength >= firstSideLength + secondSideLength
        ) throw IllegalArgumentException("triangle with data length of the sides doesn't exist")
    }

    override fun calcArea(): Double {
        val p = calcPerimeter() / 2 // want to calculate area using Heron's formula
        return sqrt(p * (p - firstSideLength) * (p - secondSideLength) * (p - thirdSideLength))
    }

    override fun calcPerimeter(): Double {
        return firstSideLength + secondSideLength + thirdSideLength
    }

    override fun equals(other: Any?): Boolean {
        return (other != null && other is Triangle &&
                other.firstSideLength == firstSideLength && other.secondSideLength == secondSideLength &&
                other.thirdSideLength == thirdSideLength
                )
    }

    override fun hashCode(): Int {
        var result = firstSideLength.hashCode()
        result = 31 * result + secondSideLength.hashCode()
        result = 31 * result + thirdSideLength.hashCode()
        return result
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