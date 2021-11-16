package laba4Tests

import laba4.*
import org.junit.Assert
import org.junit.Test
import kotlin.jvm.Throws
import kotlin.test.assertFailsWith

/**
 * The main testing class
 */
class MatrixTesting {

    /**
     * The method tests the creation of object of the class Matrix size 1x1
     * @see Matrix
     */
    @Test
    fun `Create 1x1 matrix testing`() {
        val matrix = Matrix(Array(1) { Array(1) { 0.0 } })
        Assert.assertEquals(1, matrix.size())
    }

    /**
     * The method tests the creation of object of the class Matrix size 1x1 with zero value by default
     * @see Matrix
     */
    @Test
    fun `Create 1x1 matrix default zero testing`() {
        val matrix = Matrix(Array(1) { Array(1) { 0.0 } })
        Assert.assertEquals(0.0, matrix[0, 0], 0.0)
    }

    /**
     * The method tests the creation of object of the class Matrix of invalid size
     * @see Matrix
     */
    @Throws(IllegalArgumentException::class)
    @Test
    fun `Create matrix error testing`() {
        val exception = assertFailsWith<IllegalArgumentException> { Matrix(Array(0) { Array(1) { 0.0 } }) }
        println("caught exception: $exception")
        Assert.assertEquals("can't create empty matrix", exception.message)
    }

    /**
     * The method tests the creation of object of the class Matrix with data matrix like array of arrays
     * @see Matrix
     */
    @Test
    fun `Create 2x1 matrix initialized as Array of arrays of doubles testing`() {
        val matrix = Matrix(Array(2) { Array(1) { 2.0 } })
        Assert.assertEquals(2, matrix.columnSize())
        Assert.assertEquals(1, matrix.lineSize())
        Assert.assertEquals(2.0, matrix[0, 0], 0.0)
        Assert.assertEquals(2.0, matrix[1, 0], 0.0)
    }

    /**
     * The method tests the creation of object of the class Matrix with data matrix like array
     * using secondary constructor
     * @see Matrix
     */
    @Test
    fun `Create 2x2 matrix initialized as array of doubles testing`() {
        val matrix = Matrix(arrayOf(arrayOf(1.1, 2.2), arrayOf(3.3, 4.4)))
        Assert.assertEquals(1.1, matrix[0, 0], 0.0)
    }

    /**
     * The method tests the Matrix.columnSize() works correct
     * @see Matrix.columnSize
     */
    @Test
    fun `Matrix stringSize testing`() {
        val matrix = Matrix(arrayOf(arrayOf(1.1, 2.2), arrayOf(3.3, 4.4)))
        Assert.assertEquals(2, matrix.columnSize())
    }

    /**
     * The method tests the Matrix.stringSize() works correct
     * @see Matrix.lineSize
     */
    @Test
    fun `Matrix rowSize testing`() {
        val matrix = Matrix(arrayOf(arrayOf(1.1, 2.2), arrayOf(3.3, 4.4)))
        Assert.assertEquals(2, matrix.lineSize())
    }

    /**
     * The method tests the Matrix.get() aka Matrix[i, j] = ? works correct
     * @see Matrix.get
     */
    @Test
    fun `get Matrix testing`() {
        val matrix = Matrix(arrayOf(arrayOf(1.1, 2.2), arrayOf(3.3, 4.4)))
        Assert.assertEquals(2, matrix.lineSize())
    }

    /**
     * The method tests the Matrix.get() with invalid data index throws error
     * @see Matrix.get
     */
    @Throws(IllegalArgumentException::class)
    @Test
    fun `get Matrix invalid index testing`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            val matrix = Matrix(arrayOf(arrayOf(1.1, 2.2), arrayOf(3.3, 4.4)))
            matrix[3, 0]
        }
        println("caught exception: $exception")
        Assert.assertEquals("invalid index", exception.message)
    }

    /**
     * The method tests the Matrix.set() aka Matrix[i, j] = new value works correct
     * @see Matrix.get
     */
    @Test
    fun `set Matrix testing`() {
        val matrix = Matrix(arrayOf(arrayOf(1.1, 2.2), arrayOf(3.3, 4.4)))
        matrix[0, 1] = 5.0
        Assert.assertEquals(5.0, matrix[0, 1], 0.0)
    }

    /**
     * The method tests the Matrix.set() with invalid data index throws error
     * @see Matrix.set
     */
    @Throws(IllegalArgumentException::class)
    @Test
    fun `set Matrix invalid index testing`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            val matrix = Matrix(arrayOf(arrayOf(1.1, 2.2), arrayOf(3.3, 4.4)))
            matrix[3, 0] = 0.0
        }
        println("caught exception: $exception")
        Assert.assertEquals("invalid index", exception.message)
    }

    /**
     * The method tests the Matrix equals comparison works
     * @see Matrix.equals
     */
    @Test
    fun `equals matrix testing`() {
        val matrix1 = Matrix(Array(1) { Array(1) { 0.0 } })
        val matrix2 = Matrix(Array(1) { Array(1) { 0.0 } })
        Assert.assertEquals(matrix1, matrix2)
    }

    /**
     * The method tests the Matrix.getTransposed() calculates transposed 1x1 matrix correct
     * @see Matrix.getTransposed
     */
    @Test
    fun `matrix 1x1 getTransposed testing`() {
        val matrix = Matrix(Array(1) { Array(1) { 1.1 } })
        Assert.assertEquals(1.1, matrix.getTransposed()[0, 0], 0.0)
    }

    /**
     * The method tests the Matrix.getTransposed() calculates transposed 2x2 matrix correct
     * @see Matrix.getTransposed
     */
    @Test
    fun `matrix 2x2 getTransposed testing`() {
        val matrix = Matrix(arrayOf(arrayOf(1.1, 2.2), arrayOf(3.3, 4.4)))
        val transposed = matrix.getTransposed()
        Assert.assertEquals(matrix[0, 0], transposed[0, 0], 0.0)
        Assert.assertEquals(matrix[1, 1], transposed[1, 1], 0.0)
        Assert.assertEquals(matrix[1, 0], transposed[0, 1], 0.0)
        Assert.assertEquals(matrix[0, 1], transposed[1, 0], 0.0)
    }

    /**
     * The method tests the Matrix.getTransposed() calculates transposed 3x3 matrix correct
     * @see Matrix.getTransposed
     */
    @Test
    fun `matrix 3x3 getTransposed testing`() {
        val matrix = Matrix(
            arrayOf(
                arrayOf(1.0, -2.0, 3.0),
                arrayOf(4.0, 0.0, 6.0),
                arrayOf(-7.0, 8.0, 9.0)
            )
        )
        val transposed = matrix.getTransposed()
        Assert.assertEquals(matrix[0, 0], transposed[0, 0], 0.0)
        Assert.assertEquals(matrix[1, 1], transposed[1, 1], 0.0)
        Assert.assertEquals(matrix[2, 2], transposed[2, 2], 0.0)
        Assert.assertEquals(matrix[1, 0], transposed[0, 1], 0.0)
        Assert.assertEquals(matrix[0, 1], transposed[1, 0], 0.0)
        Assert.assertEquals(matrix[2, 0], transposed[0, 2], 0.0)
        Assert.assertEquals(matrix[0, 2], transposed[2, 0], 0.0)
        Assert.assertEquals(matrix[2, 1], transposed[1, 2], 0.0)
        Assert.assertEquals(matrix[1, 2], transposed[2, 1], 0.0)
    }

    /**
     * The method tests the Matrix.getMinorMatrix(i, j) throws error when matrix 1x1
     * @see Matrix.getMinorMatrix
     */
    @Throws(IllegalArgumentException::class)
    @Test
    fun `matrix 1x1 getMinorMatrix throws error testing`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            val matrix = Matrix(Array(1) { Array(1) { 1.1 } })
            matrix.getMinorMatrix(0, 0)
        }
        println("caught exception: $exception")
        Assert.assertEquals("cannot get minor matrix from 1x1 matrix", exception.message)
    }

    /**
     * The method tests the Matrix.getMinorMatrix(i, j) throws error when matrix is non-square
     * @see Matrix.getMinorMatrix
     */
    @Throws(IllegalArgumentException::class)
    @Test
    fun `non-square matrix getMinorMatrix throws error testing`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            val matrix = Matrix(arrayOf(arrayOf(1.1, 2.5)))
            matrix.getMinorMatrix(0, 0)
        }
        println("caught exception: $exception")
        Assert.assertEquals("minor doesn't exist for non-square matrices", exception.message)
    }

    /**
     * The method tests the Matrix.getMinorMatrix(i, j) throws error when index (i, j) is wrong
     * @see Matrix.getMinorMatrix
     */
    @Throws(IllegalArgumentException::class)
    @Test
    fun `minor with invalid index in matrix getMinorMatrix throws error testing`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            val matrix = Matrix(arrayOf(arrayOf(1.1, 2.2), arrayOf(3.3, 4.4)))
            matrix.getMinorMatrix(3, 0)
        }
        println("caught exception: $exception")
        Assert.assertEquals("invalid minor index", exception.message)
    }

    /**
     * The method tests the Matrix.getMinorMatrix(i, j) minor matrix size check
     * @see Matrix.getMinorMatrix
     */
    @Test
    fun `matrix 2x2 getMinorMatrix size testing`() {
        val matrix = Matrix(arrayOf(arrayOf(1.1, 2.2), arrayOf(3.3, 4.4)))
        Assert.assertEquals(1, matrix.getMinorMatrix(0, 0).size())
        Assert.assertEquals(1, matrix.getMinorMatrix(0, 1).size())
        Assert.assertEquals(1, matrix.getMinorMatrix(1, 0).size())
        Assert.assertEquals(1, matrix.getMinorMatrix(1, 1).size())
    }

    /**
     * The method tests the Matrix.getMinorMatrix(i, j) works correct for 2x2 matrix
     * @see Matrix.getMinorMatrix
     */
    @Test
    fun `matrix 2x2 getMinorMatrix testing`() {
        val matrix = Matrix(arrayOf(arrayOf(1.1, 2.2), arrayOf(3.3, 4.4)))
        Assert.assertEquals(4.4, matrix.getMinorMatrix(0, 0)[0, 0], 0.0)
        Assert.assertEquals(3.3, matrix.getMinorMatrix(0, 1)[0, 0], 0.0)
        Assert.assertEquals(2.2, matrix.getMinorMatrix(1, 0)[0, 0], 0.0)
        Assert.assertEquals(1.1, matrix.getMinorMatrix(1, 1)[0, 0], 0.0)
    }

    /**
     * The method tests the Matrix.getMinorMatrix(i, j) minor matrix size check when data matrix 3x3
     * @see Matrix.getMinorMatrix
     */
    @Test
    fun `matrix 3x3 getMinorMatrix size testing`() {
        val matrix = Matrix(
            arrayOf(
                arrayOf(1.0, -2.0, 3.0),
                arrayOf(4.0, 0.0, 6.0),
                arrayOf(-7.0, 8.0, 9.0)
            )
        )
        Assert.assertEquals(4, matrix.getMinorMatrix(0, 0).size())
        Assert.assertEquals(4, matrix.getMinorMatrix(0, 1).size())
        Assert.assertEquals(4, matrix.getMinorMatrix(1, 0).size())
        Assert.assertEquals(4, matrix.getMinorMatrix(1, 1).size())
        Assert.assertEquals(4, matrix.getMinorMatrix(2, 2).size())
        Assert.assertEquals(4, matrix.getMinorMatrix(0, 2).size())
        Assert.assertEquals(4, matrix.getMinorMatrix(2, 0).size())
        Assert.assertEquals(4, matrix.getMinorMatrix(2, 1).size())
        Assert.assertEquals(4, matrix.getMinorMatrix(1, 2).size())
    }

    /**
     * The method tests the Matrix.getMinorMatrix(i, j) works correct for 3x3 matrix
     * @see Matrix.getMinorMatrix
     */
    @Test
    fun `matrix 3x3 getMinorMatrix testing`() {
        val matrix = Matrix(
            arrayOf(
                arrayOf(1.0, -2.0, 3.0),
                arrayOf(4.0, 0.0, 6.0),
                arrayOf(-7.0, 8.0, 9.0)
            )
        )
        val minor11 = matrix.getMinorMatrix(1, 1)
        Assert.assertEquals(matrix[0, 0], minor11[0, 0], 0.0)
        Assert.assertEquals(matrix[0, 2], minor11[0, 1], 0.0)
        Assert.assertEquals(matrix[2, 0], minor11[1, 0], 0.0)
        Assert.assertEquals(matrix[2, 2], minor11[1, 1], 0.0)
    }

    /**
     * The method tests the Matrix.calcDeterminant() calculates determinant 1x1 correct
     * @see Matrix.calcDeterminant
     */
    @Test
    fun `matrix 1x1 calcDeterminant testing`() {
        val matrix = Matrix(Array(1) { Array(1) { 1.1 } })
        Assert.assertEquals(1.1, matrix.calcDeterminant(), 0.0)
    }

    /**
     * The method tests the Matrix.calcDeterminant() calculates determinant 2x2 correct
     * @see Matrix.calcDeterminant
     */
    @Test
    fun `matrix 2x2 calcDeterminant testing`() {
        val matrix = Matrix(arrayOf(arrayOf(1.1, 2.2), arrayOf(3.3, 4.4)))
        Assert.assertEquals(1.1 * 4.4 - 2.2 * 3.3, matrix.calcDeterminant(), 0.0)
    }

    /**
     * The method tests the Matrix.calcDeterminant() calculates determinant 3x3 correct
     * @see Matrix.calcDeterminant
     */
    @Test
    fun `matrix 3x3 calcDeterminant testing`() {
        val matrix = Matrix(
            arrayOf(
                arrayOf(1.0, -2.0, 3.0),
                arrayOf(4.0, 0.0, 6.0),
                arrayOf(-7.0, 8.0, 9.0)
            )
        )
        Assert.assertEquals(204.0, matrix.calcDeterminant(), 0.0)
    }

    /**
     * The method tests the Matrix.calcDeterminant() calculates determinant 4x4 correct
     * @see Matrix.calcDeterminant
     */
    @Test
    fun `matrix 4x4 calcDeterminant testing`() {
        val matrix = Matrix(
            arrayOf(
                arrayOf(3.0, -3.0, -5.0, 8.0),
                arrayOf(-3.0, 2.0, 4.0, -6.0),
                arrayOf(2.0, -5.0, -7.0, 5.0),
                arrayOf(-4.0, 3.0, 5.0, -6.0)
            )
        )
        Assert.assertEquals(18.0, matrix.calcDeterminant(), 0.0)
    }

    /**
     * The method tests the Matrix.getMinor() calculates determinant of minor correct
     * this function uses getMinorMatrix and calcDeterminant, so it's just extra check
     * @see Matrix.getMinor
     */
    @Test
    fun `matrix 4x4 getMinor testing`() {
        val matrix = Matrix(
            arrayOf(
                arrayOf(3.0, -3.0, -5.0, 8.0),
                arrayOf(-3.0, 2.0, 4.0, -6.0),
                arrayOf(2.0, -5.0, -7.0, 5.0),
                arrayOf(-4.0, 3.0, 5.0, -6.0)
            )
        )
        Assert.assertEquals(-2.0, matrix.getMinor(0, 0), 0.0)
    }

    /**
     * The method tests the Matrix.getComplementMatrix() returns right matrix 1x1
     * @see Matrix.getComplementMatrix
     */
    @Test
    fun `matrix 1x1 getComplementMatrix throws error testing`() {
        val matrix = Matrix(Array(1) { Array(1) { 1.1 } })
        val complementMatrix = matrix.getComplementMatrix()
        val rightAnswer = Matrix(Array(1) { Array(1) { 1.0 / 1.1 } })
        Assert.assertEquals(rightAnswer[0, 0], complementMatrix[0, 0], 0.0)
    }

    /**
     * The method tests the Matrix.getComplementMatrix() throws error when matrix is non-square
     * @see Matrix.getComplementMatrix
     */
    @Throws(IllegalArgumentException::class)
    @Test
    fun `non-square matrix getComplementMatrix throws error testing`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            val matrix = Matrix(arrayOf(arrayOf(1.1, 2.5)))
            matrix.getComplementMatrix()
        }
        println("caught exception: $exception")
        Assert.assertEquals("complement matrix doesn't exist for non-square matrices", exception.message)
    }

    /**
     * The method tests the Matrix.getComplementMatrix() calculates complement matrix 2x2 correct
     * @see Matrix.getComplementMatrix
     */
    @Test
    fun `matrix 2x2 getComplementMatrix testing`() {
        val matrix = Matrix(arrayOf(arrayOf(1.1, 2.2), arrayOf(3.3, 4.4)))
        val complement = matrix.getComplementMatrix()
        Assert.assertEquals(matrix[1, 1], complement[0, 0], 0.0)
        Assert.assertEquals(-matrix[1, 0], complement[0, 1], 0.0)
        Assert.assertEquals(-matrix[0, 1], complement[1, 0], 0.0)
        Assert.assertEquals(matrix[0, 0], complement[1, 1], 0.0)
    }

    /**
     * The method tests the Matrix.plus() throws error when matrices have different dimensions
     * @see Matrix.plus
     */
    @Throws(IllegalArgumentException::class)
    @Test
    fun `matrix plus dimension error testing`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            val matrix1 = Matrix(arrayOf(arrayOf(1.1, 2.2), arrayOf(3.3, 4.4)))
            val matrix2 = Matrix(arrayOf(arrayOf(1.2, 2.3)))
            matrix1 + matrix2
        }
        println("caught exception: $exception")
        Assert.assertEquals("cannot add matrices of different dimensions", exception.message)
    }

    /**
     * The method tests the Matrix.plus() creates new matrix
     * @see Matrix.plus
     */
    @Test
    fun `matrix plus creates new matrix testing`() {
        val matrix1 = Matrix(arrayOf(arrayOf(1.1, 2.2)))
        val matrix2 = Matrix(arrayOf(arrayOf(1.2, 2.3)))
        val matrix3 = matrix1 + matrix2
        Assert.assertTrue(matrix1 !== matrix3)
    }

    /**
     * The method tests the Matrix.plus() works correct
     * @see Matrix.plus
     */
    @Test
    fun `matrix plus testing`() {
        val matrix1 = Matrix(arrayOf(arrayOf(1.1, 2.2)))
        val matrix2 = Matrix(arrayOf(arrayOf(1.2, 2.3)))
        val matrix3 = matrix1 + matrix2
        Assert.assertEquals(2.3, matrix3[0, 0], 0.0)
        Assert.assertEquals(4.5, matrix3[0, 1], 0.0)
    }

    /**
     * The method tests the Matrix.plus() new matrix has right size
     * @see Matrix.plus
     */
    @Test
    fun `matrix size after plus testing`() {
        val matrix1 = Matrix(arrayOf(arrayOf(1.1, 2.2)))
        val matrix2 = Matrix(arrayOf(arrayOf(1.2, 2.3)))
        val matrix3 = matrix1 + matrix2
        Assert.assertEquals(2, matrix3.size())
    }

    /**
     * The method tests the Matrix.plusAssign() throws error when matrices have different dimensions
     * @see Matrix.plusAssign
     */
    @Throws(IllegalArgumentException::class)
    @Test
    fun `matrix plusAssign dimension error testing`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            val matrix1 = Matrix(arrayOf(arrayOf(1.1, 2.2), arrayOf(3.3, 4.4)))
            val matrix2 = Matrix(arrayOf(arrayOf(1.2, 2.3)))
            matrix1 += matrix2
        }
        println("caught exception: $exception")
        Assert.assertEquals("cannot add matrices of different dimensions", exception.message)
    }

    /**
     * The method tests the Matrix.plus() creates new matrix
     * @see Matrix.plus
     */
    @Test
    fun `matrix plusAssign creates new matrix testing`() {
        val matrix1 = Matrix(arrayOf(arrayOf(1.1, 2.2)))
        val matrix2 = Matrix(arrayOf(arrayOf(1.2, 2.3)))
        matrix1 += matrix2
        Assert.assertTrue(matrix1 === matrix1)
    }

    /**
     * The method tests the Matrix.plusAssign() works correct
     * @see Matrix.plusAssign
     */
    @Test
    fun `matrix plusAssign testing`() {
        val matrix1 = Matrix(arrayOf(arrayOf(1.1, 2.2)))
        val matrix2 = Matrix(arrayOf(arrayOf(1.2, 2.3)))
        matrix1 += matrix2
        Assert.assertEquals(2.3, matrix1[0, 0], 0.0)
        Assert.assertEquals(4.5, matrix1[0, 1], 0.0)
    }

    /**
     * The method tests the Matrix.minus() throws error when matrices have different dimensions
     * @see Matrix.minus
     */
    @Throws(IllegalArgumentException::class)
    @Test
    fun `matrix minus dimension error testing`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            val matrix1 = Matrix(arrayOf(arrayOf(1.1, 2.2), arrayOf(3.3, 4.4)))
            val matrix2 = Matrix(arrayOf(arrayOf(1.2, 2.3)))
            matrix1 - matrix2
        }
        println("caught exception: $exception")
        Assert.assertEquals("cannot substitute matrices of different dimensions", exception.message)
    }

    /**
     * The method tests the Matrix.minus() creates new matrix
     * @see Matrix.minus
     */
    @Test
    fun `matrix minus creates new matrix testing`() {
        val matrix1 = Matrix(arrayOf(arrayOf(1.1, 2.2)))
        val matrix2 = Matrix(arrayOf(arrayOf(1.2, 2.3)))
        val matrix3 = matrix1 - matrix2
        Assert.assertTrue(matrix1 !== matrix3)
    }

    /**
     * The method tests the Matrix.minus() works correct
     * @see Matrix.minus
     */
    @Test
    fun `matrix minus testing`() {
        val matrix1 = Matrix(arrayOf(arrayOf(1.1, 2.2)))
        val matrix2 = Matrix(arrayOf(arrayOf(1.2, 2.3)))
        val matrix3 = matrix1 - matrix2
        Assert.assertEquals(1.1 - 1.2, matrix3[0, 0], 0.0)
        Assert.assertEquals(2.2 - 2.3, matrix3[0, 1], 0.0)
    }

    /**
     * The method tests the Matrix.minus() new matrix has right size
     * @see Matrix.minus
     */
    @Test
    fun `matrix size after minus testing`() {
        val matrix1 = Matrix(arrayOf(arrayOf(1.1, 2.2)))
        val matrix2 = Matrix(arrayOf(arrayOf(1.2, 2.3)))
        val matrix3 = matrix1 - matrix2
        Assert.assertEquals(2, matrix3.size())
    }

    /**
     * The method tests the Matrix.minusAssign() throws error when matrices have different dimensions
     * @see Matrix.minusAssign
     */
    @Throws(IllegalArgumentException::class)
    @Test
    fun `matrix minusAssign dimension error testing`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            val matrix1 = Matrix(arrayOf(arrayOf(1.1, 2.2), arrayOf(3.3, 4.4)))
            val matrix2 = Matrix(arrayOf(arrayOf(1.2, 2.3)))
            matrix1 -= matrix2
        }
        println("caught exception: $exception")
        Assert.assertEquals("cannot substitute matrices of different dimensions", exception.message)
    }

    /**
     * The method tests the Matrix.minusAssign() works correct
     * @see Matrix.minusAssign
     */
    @Test
    fun `matrix minusAssign testing`() {
        val matrix1 = Matrix(arrayOf(arrayOf(1.1, 2.2)))
        val matrix2 = Matrix(arrayOf(arrayOf(1.2, 2.3)))
        matrix1 -= matrix2
        Assert.assertEquals(1.1 - 1.2, matrix1[0, 0], 0.0)
        Assert.assertEquals(2.2 - 2.3, matrix1[0, 1], 0.0)
    }

    /**
     * The method tests the Matrix.times() throws error when matrices have wrong dimensions
     * @see Matrix.times
     */
    @Throws(IllegalArgumentException::class)
    @Test
    fun `matrix times dimension error testing`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            val matrix1 = Matrix(arrayOf(arrayOf(1.1, 2.2), arrayOf(3.3, 4.4)))
            val matrix2 = Matrix(arrayOf(arrayOf(1.2, 2.3)))
            matrix1 * matrix2
        }
        println("caught exception: $exception")
        Assert.assertEquals("matrices of these sizes cannot be multiplied", exception.message)
    }

    /**
     * The method tests the Matrix.times() creates new matrix
     * @see Matrix.times
     */
    @Test
    fun `matrix times creates new matrix testing`() {
        val matrix1 = Matrix(arrayOf(arrayOf(1.1, 2.2)))
        val matrix2 = Matrix(arrayOf(arrayOf(1.2), arrayOf(2.3)))
        val matrix3 = matrix1 * matrix2
        Assert.assertTrue(matrix1 !== matrix3)
    }

    /**
     * The method tests the Matrix.times() works correct
     * @see Matrix.times
     */
    @Test
    fun `matrix times testing`() {
        val matrix1 = Matrix(arrayOf(arrayOf(1.1, 2.2)))
        val matrix2 = Matrix(arrayOf(arrayOf(1.2, 2.3), arrayOf(1.0, 1.0)))
        val matrix3 = matrix1 * matrix2
        Assert.assertEquals(1.1 * 1.2 + 2.2, matrix3[0, 0], 0.0)
        Assert.assertEquals(1.1 * 2.3 + 2.2, matrix3[0, 1], 0.0)
    }

    /**
     * The method tests the Matrix.times() new matrix has right size
     * @see Matrix.times
     */
    @Test
    fun `matrix size after times testing`() {
        val matrix1 = Matrix(arrayOf(arrayOf(1.1, 2.2)))
        val matrix2 = Matrix(arrayOf(arrayOf(1.2), arrayOf(2.3)))
        val matrix3 = matrix1 * matrix2
        Assert.assertEquals(1, matrix3.lineSize())
        Assert.assertEquals(1, matrix3.columnSize())
    }

    /**
     * The method tests the Matrix.timesAssign() throws error when matrices have wrong dimensions
     * @see Matrix.timesAssign
     */
    @Throws(IllegalArgumentException::class)
    @Test
    fun `matrix timesAssign dimension error testing`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            val matrix1 = Matrix(arrayOf(arrayOf(1.1, 2.2), arrayOf(3.3, 4.4)))
            val matrix2 = Matrix(arrayOf(arrayOf(1.2, 2.3)))
            matrix1 *= matrix2
        }
        println("caught exception: $exception")
        Assert.assertEquals("matrices of these sizes cannot be multiplied", exception.message)
    }

    /**
     * The method tests the Matrix.timesAssign() works correct
     * @see Matrix.timesAssign
     */
    @Test
    fun `matrix timesAssign testing`() {
        val matrix1 = Matrix(arrayOf(arrayOf(1.1, 2.2)))
        val matrix2 = Matrix(arrayOf(arrayOf(1.2, 2.3), arrayOf(1.0, 1.0)))
        matrix1 *= matrix2
        Assert.assertEquals(1.1 * 1.2 + 2.2, matrix1[0, 0], 0.0)
        Assert.assertEquals(1.1 * 2.3 + 2.2, matrix1[0, 1], 0.0)
    }

    /**
     * The method tests the Matrix.timesAssign() new matrix has right size
     * @see Matrix.timesAssign
     */
    @Test
    fun `matrix size after timesAssign testing`() {
        val matrix1 = Matrix(arrayOf(arrayOf(1.1, 2.2)))
        val matrix2 = Matrix(arrayOf(arrayOf(1.2), arrayOf(2.3)))
        matrix1 *= matrix2
        Assert.assertEquals(1, matrix1.size())
    }

    /**
     * The method tests the Matrix.div() throws error for non-square matrix
     * @see Matrix.div
     */
    @Throws(IllegalArgumentException::class)
    @Test
    fun `div error for non-square matrix testing`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            val matrix1 = Matrix(arrayOf(arrayOf(1.1, 2.2)))
            val matrix2 = Matrix(arrayOf(arrayOf(1.1, 2.2), arrayOf(3.3, 4.4)))
            matrix1 / matrix2
        }
        println("caught exception: $exception")
        Assert.assertEquals("division cannot be performed with non-square matrices", exception.message)
    }

    /**
     * The method tests the Matrix.div() throws error when divide by matrix with zero determinant
     * @see Matrix.div
     */
    @Throws(IllegalArgumentException::class)
    @Test
    fun `error when divide on zero-determinant matrix testing`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            val matrix1 = Matrix(arrayOf(arrayOf(1.1, 2.2), arrayOf(3.3, 4.4)))
            val matrix2 = Matrix(arrayOf(arrayOf(1.0, 1.0), arrayOf(4.0, 4.0)))
            matrix1 / matrix2
        }
        println("caught exception: $exception")
        Assert.assertEquals(
            "the right matrix does not have an inverse matrix, division cannot be performed",
            exception.message
        )
    }

    /**
     * The method tests the Matrix.div() works for 1x1 divide by 1x1 matrix
     * @see Matrix.div
     */
    @Test
    fun `1x1 divide by 1x1 matrix testing`() {
        val matrix1 = Matrix(Array(1) { Array(1) { 5.0 } })
        val matrix2 = Matrix(Array(1) { Array(1) { 1.0 } })
        val matrix3 = matrix1 / matrix2
        Assert.assertEquals(5.0, matrix3[0, 0], 0.0)
    }

    /**
     * The method tests the Matrix.div() throws error for 1x1 divide by 1x1 matrix when divider = 0
     * @see Matrix.div
     */
    @Throws(ArithmeticException::class)
    @Test
    fun `1x1 divide by 1x1 zero matrix throws error testing`() {
        val exception = assertFailsWith<ArithmeticException> {
            val matrix1 = Matrix(Array(1) { Array(1) { 5.0 } })
            val matrix2 = Matrix(Array(1) { Array(1) { 0.0 } })
            matrix1 / matrix2
        }
        println("caught exception: $exception")
        Assert.assertEquals("it is forbidden to divide by zero", exception.message)
    }

    /**
     * The method tests the Matrix.div() works for 2x2 divide by 2x2 matrix
     * @see Matrix.div
     */
    @Test
    fun `2x2 divide by 2x2 matrix testing`() {
        val matrix1 = Matrix(arrayOf(arrayOf(1.0, 1.0), arrayOf(1.0, 1.0)))
        val matrix2 = Matrix(arrayOf(arrayOf(3.0, 1.0), arrayOf(5.0, 2.0)))
        val matrix3 = matrix1 / matrix2
        val answer = Matrix(arrayOf(arrayOf(-3.0, 2.0), arrayOf(-3.0, 2.0)))
        Assert.assertEquals(answer, matrix3)
    }

    /**
     * The method tests the Matrix.div() works for 3x3 divide by 3x3 matrix
     * @see Matrix.div
     */
    @Test
    fun `3x3 divide by 3x3 matrix testing`() {
        val matrix1 = Matrix(
            arrayOf(
                arrayOf(1.0, -1.0, 2.0),
                arrayOf(0.0, 2.0, -1.0),
                arrayOf(1.0, 0.0, 1.0)
            )
        )
        val matrix2 = Matrix(
            arrayOf(
                arrayOf(1.0, -1.0, 2.0),
                arrayOf(0.0, 2.0, -1.0),
                arrayOf(1.0, 0.0, 1.0)
            )
        )
        val matrix3 = matrix1 / matrix2
        val answer = Matrix(
            arrayOf(
                arrayOf(1.0, 0.0, 0.0),
                arrayOf(0.0, 1.0, 0.0),
                arrayOf(0.0, 0.0, 1.0)
            )
        )
        Assert.assertEquals(answer, matrix3)
    }

    /**
     * The method tests the Matrix.divAssign() throws error for non-square matrix
     * @see Matrix.divAssign
     */
    @Throws(IllegalArgumentException::class)
    @Test
    fun `divAssign error for non-square matrix testing`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            val matrix1 = Matrix(arrayOf(arrayOf(1.1, 2.2)))
            val matrix2 = Matrix(arrayOf(arrayOf(1.2, 2.3), arrayOf(4.2, 3.1)))
            matrix1 /= matrix2
        }
        println("caught exception: $exception")
        Assert.assertEquals("division cannot be performed with non-square matrices", exception.message)
    }

    /**
     * The method tests the Matrix.divAssign() throws error when divide by matrix with zero determinant
     * @see Matrix.divAssign
     */
    @Throws(IllegalArgumentException::class)
    @Test
    fun `error when divAssign on zero-determinant matrix testing`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            val matrix1 = Matrix(arrayOf(arrayOf(1.1, 2.2), arrayOf(3.3, 4.4)))
            val matrix2 = Matrix(arrayOf(arrayOf(1.0, 1.0), arrayOf(4.0, 4.0)))
            matrix1 /= matrix2
        }
        println("caught exception: $exception")
        Assert.assertEquals(
            "the right matrix does not have an inverse matrix, division cannot be performed",
            exception.message
        )
    }

    /**
     * The method tests the Matrix.divAssign() works for 1x1 divide by 1x1 matrix
     * @see Matrix.divAssign
     */
    @Test
    fun `1x1 divideAssign by 1x1 matrix testing`() {
        val matrix1 = Matrix(Array(1) { Array(1) { 5.0 } })
        val matrix2 = Matrix(Array(1) { Array(1) { 1.0 } })
        matrix1 /= matrix2
        Assert.assertEquals(5.0, matrix1[0, 0], 0.0)
    }

    /**
     * The method tests the Matrix.divAssign() throws error for 1x1 divide by 1x1 matrix when divider = 0
     * @see Matrix.divAssign
     */
    @Throws(ArithmeticException::class)
    @Test
    fun `1x1 divideAssign by 1x1 zero matrix throws error testing`() {
        val exception = assertFailsWith<ArithmeticException> {
            val matrix1 = Matrix(Array(1) { Array(1) { 5.0 } })
            val matrix2 = Matrix(Array(1) { Array(1) { 0.0 } })
            matrix1 /= matrix2
        }
        println("caught exception: $exception")
        Assert.assertEquals("it is forbidden to divide by zero", exception.message)
    }

    /**
     * The method tests the Matrix.divAssign() works for 2x2 divide by 2x2 matrix
     * @see Matrix.divAssign
     */
    @Test
    fun `2x2 divideAssign by 2x2 matrix testing`() {
        val matrix1 = Matrix(arrayOf(arrayOf(1.0, 1.0), arrayOf(1.0, 1.0)))
        val matrix2 = Matrix(arrayOf(arrayOf(3.0, 1.0), arrayOf(5.0, 2.0)))
        matrix1 /= matrix2
        val answer = Matrix(arrayOf(arrayOf(-3.0, 2.0), arrayOf(-3.0, 2.0)))
        Assert.assertEquals(answer, matrix1)
    }

    /**
     * The method tests the Matrix.divAssign() works for 3x3 divide by 3x3 matrix
     * @see Matrix.divAssign
     */
    @Test
    fun `3x3 divideAssign by 3x3 matrix testing`() {
        val matrix1 = Matrix(
            arrayOf(
                arrayOf(1.0, -1.0, 2.0),
                arrayOf(0.0, 2.0, -1.0),
                arrayOf(1.0, 0.0, 1.0)
            )
        )
        val matrix2 = Matrix(
            arrayOf(
                arrayOf(1.0, -1.0, 2.0),
                arrayOf(0.0, 2.0, -1.0),
                arrayOf(1.0, 0.0, 1.0)
            )
        )
        matrix1 /= matrix2
        val answer = Matrix(
            arrayOf(
                arrayOf(1.0, 0.0, 0.0),
                arrayOf(0.0, 1.0, 0.0),
                arrayOf(0.0, 0.0, 1.0)
            )
        )
        Assert.assertEquals(answer, matrix1)
    }

    /**
     * The method tests the matrix * scalar works for 2x2 matrix
     * @see Matrix.times
     */
    @Test
    fun `2x2 matrix times scalar testing`() {
        val matrix2 = Matrix(arrayOf(arrayOf(3.0, 1.0), arrayOf(5.0, 2.0)))
        val scalar = 2.0
        val matrix1 = matrix2 * scalar
        val answer = Matrix(arrayOf(arrayOf(6.0, 2.0), arrayOf(10.0, 4.0)))
        Assert.assertEquals(answer, matrix1)
    }

    /**
     * The method tests the scalar * matrix works for 2x2 matrix
     * @see Matrix.times
     */
    @Test
    fun `2x2 scalar times matrix testing`() {
        val matrix2 = Matrix(arrayOf(arrayOf(3.0, 1.0), arrayOf(5.0, 2.0)))
        val scalar = 2.0
        val matrix1 = scalar * matrix2
        val answer = Matrix(arrayOf(arrayOf(6.0, 2.0), arrayOf(10.0, 4.0)))
        Assert.assertEquals(answer, matrix1)
    }

    /**
     * The method tests the matrix *= scalar works for 2x2 matrix
     * @see Matrix.timesAssign
     */
    @Test
    fun `2x2 scalar timesAssign matrix testing`() {
        val matrix1 = Matrix(arrayOf(arrayOf(3.0, 1.0), arrayOf(5.0, 2.0)))
        val scalar = 2.0
        matrix1 *= scalar
        val answer = Matrix(arrayOf(arrayOf(6.0, 2.0), arrayOf(10.0, 4.0)))
        Assert.assertEquals(answer, matrix1)
    }

    /**
     * The method tests the matrix = matrix / scalar works for 2x2 matrix
     * @see Matrix.div
     */
    @Test
    fun `2x2 scalar div matrix testing`() {
        val matrix1 = Matrix(arrayOf(arrayOf(4.0, 0.0), arrayOf(6.0, 8.0)))
        val scalar = 2.0
        val matrix2 = matrix1 / scalar
        val answer = Matrix(arrayOf(arrayOf(2.0, 0.0), arrayOf(3.0, 4.0)))
        Assert.assertEquals(answer, matrix2)
    }

    /**
     * The method tests the matrix /= scalar works for 2x2 matrix
     * @see Matrix.divAssign
     */
    @Test
    fun `2x2 scalar divAssign matrix testing`() {
        val matrix1 = Matrix(arrayOf(arrayOf(4.0, 0.0), arrayOf(6.0, 8.0)))
        val scalar = 2.0
        matrix1 /= scalar
        val answer = Matrix(arrayOf(arrayOf(2.0, 0.0), arrayOf(3.0, 4.0)))
        Assert.assertEquals(answer, matrix1)
    }

    /**
     * The method tests the -matrix works
     * @see Matrix.unaryMinus
     */
    @Test
    fun `-matrix testing`() {
        var matrix1 = Matrix(arrayOf(arrayOf(4.0, 0.0), arrayOf(6.0, 8.0)))
        matrix1 = -matrix1
        val answer = Matrix(arrayOf(arrayOf(-4.0, -0.0), arrayOf(-6.0, -8.0)))
        Assert.assertEquals(answer, matrix1)
    }

    /**
     * The method tests the +matrix works
     * @see Matrix.unaryPlus
     */
    @Test
    fun `+matrix testing`() {
        var matrix1 = Matrix(arrayOf(arrayOf(4.0, 0.0), arrayOf(6.0, 8.0)))
        matrix1 = +matrix1
        val answer = Matrix(arrayOf(arrayOf(4.0, 0.0), arrayOf(6.0, 8.0)))
        Assert.assertEquals(answer, matrix1)
    }

    /**
     * The method tests the matrix == matrix works
     * @see Matrix.equals
     */
    @Test
    fun `matrix equals testing`() {
        val matrix1 = Matrix(arrayOf(arrayOf(4.0, 0.0), arrayOf(6.0, 8.0)))
        val matrix2 = Matrix(arrayOf(arrayOf(4.0, 0.0), arrayOf(6.0, 8.0)))
        Assert.assertTrue(matrix1 == matrix2)
    }

    /**
     * The method tests the matrix.toString() prints matrix body
     * @see Matrix.toString
     */
    @Test
    fun `matrix to string testing`() {
        val matrix1 = Matrix(arrayOf(arrayOf(4.0, 0.0), arrayOf(6.0, 8.0)))
        val answer = "4.0 0.0" + System.lineSeparator() + "6.0 8.0"
        Assert.assertEquals(answer, matrix1.toString())
    }
}