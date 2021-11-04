package laba3Tests

import laba3.*
import org.junit.Assert
import org.junit.Test

/**
 * The main testing class
 */
class TestShapes {
    private val factory = ShapeFactory() // create one factory to test

    /**
     * The method tests the creation of object of the class Circle
     * @see Circle
     */
    @Test
    fun `Circle created right testing`() {
        val circle = factory.createCircle(1.0)
        Assert.assertEquals(1.0, circle.radius, 0.0)
    }

    /**
     * The method tests the function calcArea of interface CalcCircle implemented by class Circle
     * @see Circle.calcArea
     */
    @Test
    fun `Circle area calculation testing`() {
        val circle = factory.createCircle(1.0)
        Assert.assertEquals(Math.PI, circle.calcArea(), 0.0)
    }

    /**
     * The method tests the function calcPerimeter of interface CalcCircle implemented by class Circle
     * @see Circle.calcPerimeter
     */
    @Test
    fun `Circle perimeter calculation testing`() {
        val circle = factory.createCircle(1.0)
        Assert.assertEquals(2 * Math.PI, circle.calcPerimeter(), 0.0)
    }

    /**
     * The method tests the creation of object of the class Square
     * @see Square
     */
    @Test
    fun `Square created right testing`() {
        val square = factory.createSquare(1.0)
        Assert.assertEquals(1.0, square.sideLength, 0.0)
    }

    /**
     * The method tests the function calcArea of interface CalcSquare implemented by class Square
     * @see Square.calcArea
     */
    @Test
    fun `Square area calculation testing`() {
        val square = factory.createSquare(1.0)
        Assert.assertEquals(1.0, square.calcArea(), 0.0)
    }

    /**
     * The method tests the function calcPerimeter of interface CalcSquare implemented by class Square
     * @see Square.calcPerimeter
     */
    @Test
    fun `Square perimeter calculation testing`() {
        val square = factory.createSquare(1.0)
        Assert.assertEquals(4.0, square.calcPerimeter(), 0.0)
    }

    /**
     * The method tests the creation of object of the class Rectangle
     * @see Rectangle
     */
    @Test
    fun `Rectangle created right testing`() {
        val rectangle = factory.createRectangle(1.0, 2.0)
        Assert.assertEquals(1.0, rectangle.firstSideLength, 0.0)
        Assert.assertEquals(2.0, rectangle.secondSideLength, 0.0)
    }

    /**
     * The method tests the function calcArea of interface CalcRectangle implemented by class Rectangle
     * @see Rectangle.calcArea
     */
    @Test
    fun `Rectangle area calculation testing`() {
        val rectangle = factory.createRectangle(1.0, 2.0)
        Assert.assertEquals(2.0, rectangle.calcArea(), 0.0)
    }

    /**
     * The method tests the function calcPerimeter of interface CalcRectangle implemented by class Rectangle
     * @see Rectangle.calcPerimeter
     */
    @Test
    fun `Rectangle perimeter calculation testing`() {
        val rectangle = factory.createRectangle(1.0, 2.0)
        Assert.assertEquals(6.0, rectangle.calcPerimeter(), 0.0)
    }

    /**
     * The method tests the creation of object of the class Triangle
     * @see Triangle
     */
    @Test
    fun `Triangle created right testing`() {
        val triangle = factory.createTriangle(3.0, 4.0, 5.0)
        Assert.assertEquals(3.0, triangle.firstSideLength, 0.0)
        Assert.assertEquals(4.0, triangle.secondSideLength, 0.0)
        Assert.assertEquals(5.0, triangle.thirdSideLength, 0.0)
    }

    /**
     * The method tests the function calcArea of interface CalcTriangle implemented by class Triangle
     * @see Triangle.calcArea
     */
    @Test
    fun `Triangle area calculation testing`() {
        val triangle = factory.createTriangle(3.0, 4.0, 5.0)
        Assert.assertEquals(6.0, triangle.calcArea(), 0.0)
    }

    /**
     * The method tests the function calcPerimeter of interface CalcTriangle implemented by class Triangle
     * @see Triangle.calcPerimeter
     */
    @Test
    fun `Triangle perimeter calculation testing`() {
        val triangle = factory.createTriangle(3.0, 4.0, 5.0)
        Assert.assertEquals(12.0, triangle.calcPerimeter(), 0.0)
    }

    /**
     * The method tests random shapes and their area and perimeter calculation
     * @see ShapeFactory.createRandomShape
     */
    @Test
    fun `Random shapes testing`() {
        val shape = factory.createRandomShape()
        var area = 0.0
        var perimeter = 0.0
        when (shape) {
            is Circle -> {
                area = factory.createCircle(shape.radius).calcArea()
                perimeter = factory.createCircle(shape.radius).calcPerimeter()
            }
            is Square -> {
                area = factory.createSquare(shape.sideLength).calcArea()
                perimeter = factory.createSquare(shape.sideLength).calcPerimeter()
            }
            is Rectangle -> {
                area = factory.createRectangle(shape.firstSideLength, shape.secondSideLength).calcArea()
                perimeter = factory.createRectangle(shape.firstSideLength, shape.secondSideLength).calcPerimeter()
            }

            is Triangle -> {
                area = factory.createTriangle(
                    shape.firstSideLength, shape.secondSideLength,
                    shape.thirdSideLength
                ).calcArea()
                perimeter = factory.createTriangle(
                    shape.firstSideLength, shape.secondSideLength,
                    shape.thirdSideLength
                ).calcPerimeter()
            }
        }
        Assert.assertEquals(perimeter, shape.calcPerimeter(), 0.0)
        Assert.assertEquals(area, shape.calcArea(), 0.0)
    }
}