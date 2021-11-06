package laba4Tests

import laba4.*
import org.junit.Assert
import org.junit.Test
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
        val matrix = Matrix(1, 1)
        Assert.assertEquals(1, matrix.size())
    }

    /**
     * The method tests the creation of object of the class Matrix size 1x1 with zero value by default
     * @see Matrix
     */
    @Test
    fun `Create 1x1 matrix default zero testing`() {
        val matrix = Matrix(1, 1)
        Assert.assertEquals(0.0, matrix[0, 0], 0.0)
    }

    /**
     * The method tests the creation of object of the class Matrix of invalid size
     * @see Matrix
     */
    @Test
    fun `Create matrix error testing`() {
        val exception = assertFailsWith<IllegalStateException> { Matrix(0, 1) }
        Assert.assertEquals("cannot create matrix with data size", exception.message)
    }

    /**
     * The method tests the creation of object of the class Matrix with data matrix like array of arrays
     * @see Matrix
     */
    @Test
    fun `Create 2x1 matrix initialized as Array of arrays of doubles testing`() {
        val matrix = Matrix(2, 1, Array(2) { Array(1) { 2.0 } })
        Assert.assertEquals(2.0, matrix[0, 0], 0.0)
        Assert.assertEquals(2.0, matrix[1, 0], 0.0)
    }

    /**
     * The method tests the creation of object of the class Matrix with data matrix like array
     * using secondary constructor with wrong sizes
     * @see Matrix
     */
    @Test
    fun `Create 2x1 matrix initialized as array of doubles error wrong size testing`() {
        val exception = assertFailsWith<IllegalStateException> {
            Matrix(2, 1, doubleArrayOf(1.1, 2.2, 3.3, 4.4).toTypedArray())
        }
        Assert.assertEquals("wrong amount of numbers, failed initialization", exception.message)
    }

    /**
     * The method tests the creation of object of the class Matrix with data matrix like array
     * using secondary constructor
     * @see Matrix
     */
    @Test
    fun `Create 2x2 matrix initialized as array of doubles testing`() {
        val matrix = Matrix(2, 2, doubleArrayOf(1.1, 2.2, 3.3, 4.4).toTypedArray())
        Assert.assertEquals(1.1, matrix[0, 0], 0.0)
    }

    /**
     * The method tests the Matrix.size() works correct
     * @see Matrix.size
     */
    @Test
    fun `Matrix size testing`() {
        val matrix = Matrix(2, 2, doubleArrayOf(1.1, 2.2, 3.3, 4.4).toTypedArray())
        Assert.assertEquals(4, matrix.size())
    }

    /**
     * The method tests the Matrix.stringSize() works correct
     * @see Matrix.stringSize
     */
    @Test
    fun `Matrix stringSize testing`() {
        val matrix = Matrix(2, 2, doubleArrayOf(1.1, 2.2, 3.3, 4.4).toTypedArray())
        Assert.assertEquals(2, matrix.stringSize())
    }

    /**
     * The method tests the Matrix.stringSize() works correct
     * @see Matrix.rowSize
     */
    @Test
    fun `Matrix rowSize testing`() {
        val matrix = Matrix(2, 2, doubleArrayOf(1.1, 2.2, 3.3, 4.4).toTypedArray())
        Assert.assertEquals(2, matrix.rowSize())
    }

    /**
     * The method tests the Matrix.get() aka Matrix[i, j] = ? works correct
     * @see Matrix.get
     */
    @Test
    fun `get Matrix testing`() {
        val matrix = Matrix(2, 2, doubleArrayOf(1.1, 2.2, 3.3, 4.4).toTypedArray())
        Assert.assertEquals(2, matrix.rowSize())
    }

    /**
     * The method tests the Matrix.get() with invalid data index throws error
     * @see Matrix.get
     */
    @Test
    fun `get Matrix invalid index testing`() {
        val exception = assertFailsWith<IllegalStateException> {
            val matrix = Matrix(2, 2, doubleArrayOf(1.1, 2.2, 3.3, 4.4).toTypedArray())
            matrix[3, 0]
        }
        Assert.assertEquals("invalid index", exception.message)
    }

    /**
     * The method tests the Matrix.set() aka Matrix[i, j] = new value works correct
     * @see Matrix.get
     */
    @Test
    fun `set Matrix testing`() {
        val matrix = Matrix(2, 2, doubleArrayOf(1.1, 2.2, 3.3, 4.4).toTypedArray())
        matrix[0, 1] = 5.0
        Assert.assertEquals(5.0, matrix[0, 1], 0.0)
    }

    /**
     * The method tests the Matrix.set() with invalid data index throws error
     * @see Matrix.set
     */
    @Test
    fun `set Matrix invalid index testing`() {
        val exception = assertFailsWith<IllegalStateException> {
            val matrix = Matrix(2, 2, doubleArrayOf(1.1, 2.2, 3.3, 4.4).toTypedArray())
            matrix[3, 0] = 0.0
        }
        Assert.assertEquals("invalid index", exception.message)
    }

    /**
     * The method tests the Matrix equals comparison works
     * @see Matrix.equals
     */
    @Test
    fun `equals matrix testing`() {
        val matrix1 = Matrix(1, 1)
        val matrix2 = Matrix(1, 1)
        Assert.assertEquals(matrix1, matrix2)
    }

    /**
     * The method tests the Matrix.getTransposed() calculates transposed 1x1 matrix correct
     * @see Matrix.getTransposed
     */
    @Test
    fun `matrix 1x1 getTransposed testing`() {
        val matrix = Matrix(1, 1, doubleArrayOf(1.1).toTypedArray())
        Assert.assertEquals(1.1, matrix.getTransposed()[0, 0], 0.0)
    }

    /**
     * The method tests the Matrix.getTransposed() calculates transposed 2x2 matrix correct
     * @see Matrix.getTransposed
     */
    @Test
    fun `matrix 2x2 getTransposed testing`() {
        val matrix = Matrix(2, 2, doubleArrayOf(1.1, 2.2, 3.3, 4.4).toTypedArray())
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
            3, 3, doubleArrayOf(
                1.0, -2.0, 3.0,
                4.0, 0.0, 6.0,
                -7.0, 8.0, 9.0
            ).toTypedArray()
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
    @Test
    fun `matrix 1x1 getMinorMatrix throws error testing`() {
        val exception = assertFailsWith<IllegalStateException> {
            val matrix = Matrix(1, 1, doubleArrayOf(1.1).toTypedArray())
            matrix.getMinorMatrix(0, 0)
        }
        Assert.assertEquals("cannot get minor matrix from 1x1 matrix", exception.message)
    }

    /**
     * The method tests the Matrix.getMinorMatrix(i, j) throws error when matrix is non-square
     * @see Matrix.getMinorMatrix
     */
    @Test
    fun `non-square matrix getMinorMatrix throws error testing`() {
        val exception = assertFailsWith<IllegalStateException> {
            val matrix = Matrix(1, 2, doubleArrayOf(1.1, 2.5).toTypedArray())
            matrix.getMinorMatrix(0, 0)
        }
        Assert.assertEquals("minor doesn't exist for non-square matrices", exception.message)
    }

    /**
     * The method tests the Matrix.getMinorMatrix(i, j) throws error when index (i, j) is wrong
     * @see Matrix.getMinorMatrix
     */
    @Test
    fun `minor with invalid index in matrix getMinorMatrix throws error testing`() {
        val exception = assertFailsWith<IllegalStateException> {
            val matrix = Matrix(2, 2, doubleArrayOf(1.1, 2.5, 1.3, 4.7).toTypedArray())
            matrix.getMinorMatrix(3, 0)
        }
        Assert.assertEquals("invalid minor index", exception.message)
    }

    /**
     * The method tests the Matrix.getMinorMatrix(i, j) minor matrix size check
     * @see Matrix.getMinorMatrix
     */
    @Test
    fun `matrix 2x2 getMinorMatrix size testing`() {
        val matrix = Matrix(2, 2, doubleArrayOf(1.1, 2.2, 3.3, 4.4).toTypedArray())
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
        val matrix = Matrix(2, 2, doubleArrayOf(1.1, 2.2, 3.3, 4.4).toTypedArray())
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
            3, 3, doubleArrayOf(
                1.0, -2.0, 3.0,
                4.0, 0.0, 6.0,
                -7.0, 8.0, 9.0
            ).toTypedArray()
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
            3, 3, doubleArrayOf(
                1.0, -2.0, 3.0,
                4.0, 0.0, 6.0,
                -7.0, 8.0, 9.0
            ).toTypedArray()
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
        val matrix = Matrix(1, 1, doubleArrayOf(1.1).toTypedArray())
        Assert.assertEquals(1.1, matrix.calcDeterminant(), 0.0)
    }

    /**
     * The method tests the Matrix.calcDeterminant() calculates determinant 2x2 correct
     * @see Matrix.calcDeterminant
     */
    @Test
    fun `matrix 2x2 calcDeterminant testing`() {
        val matrix = Matrix(2, 2, doubleArrayOf(1.1, 2.2, 3.3, 4.4).toTypedArray())
        Assert.assertEquals(1.1 * 4.4 - 2.2 * 3.3, matrix.calcDeterminant(), 0.0)
    }

    /**
     * The method tests the Matrix.calcDeterminant() calculates determinant 3x3 correct
     * @see Matrix.calcDeterminant
     */
    @Test
    fun `matrix 3x3 calcDeterminant testing`() {
        val matrix = Matrix(
            3, 3, doubleArrayOf(
                1.0, -2.0, 3.0,
                4.0, 0.0, 6.0,
                -7.0, 8.0, 9.0
            ).toTypedArray()
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
            4, 4, doubleArrayOf(
                3.0, -3.0, -5.0, 8.0,
                -3.0, 2.0, 4.0, -6.0,
                2.0, -5.0, -7.0, 5.0,
                -4.0, 3.0, 5.0, -6.0
            ).toTypedArray()
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
            4, 4, doubleArrayOf(
                3.0, -3.0, -5.0, 8.0,
                -3.0, 2.0, 4.0, -6.0,
                2.0, -5.0, -7.0, 5.0,
                -4.0, 3.0, 5.0, -6.0
            ).toTypedArray()
        )
        Assert.assertEquals(-2.0, matrix.getMinor(0, 0), 0.0)
    }

    /**
     * The method tests the Matrix.getComplementMatrix() throws error when matrix 1x1
     * @see Matrix.getComplementMatrix
     */
    @Test
    fun `matrix 1x1 getComplementMatrix throws error testing`() {
        val exception = assertFailsWith<IllegalStateException> {
            val matrix = Matrix(1, 1, doubleArrayOf(1.1).toTypedArray())
            matrix.getComplementMatrix()
        }
        Assert.assertEquals("cannot get complement matrix for 1x1 matrix", exception.message)
    }

    /**
     * The method tests the Matrix.getComplementMatrix() throws error when matrix is non-square
     * @see Matrix.getComplementMatrix
     */
    @Test
    fun `non-square matrix getComplementMatrix throws error testing`() {
        val exception = assertFailsWith<IllegalStateException> {
            val matrix = Matrix(1, 2, doubleArrayOf(1.1, 2.5).toTypedArray())
            matrix.getComplementMatrix()
        }
        Assert.assertEquals("complement matrix doesn't exist for non-square matrices", exception.message)
    }

    /**
     * The method tests the Matrix.getComplementMatrix() calculates complement matrix 2x2 correct
     * @see Matrix.getComplementMatrix
     */
    @Test
    fun `matrix 2x2 getComplementMatrix testing`() {
        val matrix = Matrix(2, 2, doubleArrayOf(1.1, 2.2, 3.3, 4.4).toTypedArray())
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
    @Test
    fun `matrix plus dimension error testing`() {
        val exception = assertFailsWith<IllegalStateException> {
            val matrix1 = Matrix(2, 2, doubleArrayOf(1.1, 2.2, 3.3, 4.4).toTypedArray())
            val matrix2 = Matrix(1, 2, doubleArrayOf(1.2, 2.3).toTypedArray())
            matrix1 + matrix2
        }
        Assert.assertEquals("cannot add matrices of different dimensions", exception.message)
    }

    /**
     * The method tests the Matrix.plus() creates new matrix
     * @see Matrix.plus
     */
    @Test
    fun `matrix plus creates new matrix testing`() {
        val matrix1 = Matrix(1, 2, doubleArrayOf(1.1, 2.2).toTypedArray())
        val matrix2 = Matrix(1, 2, doubleArrayOf(1.2, 2.3).toTypedArray())
        val matrix3 = matrix1 + matrix2
        Assert.assertTrue(matrix1 !== matrix3)
    }

    /**
     * The method tests the Matrix.plus() works correct
     * @see Matrix.plus
     */
    @Test
    fun `matrix plus testing`() {
        val matrix1 = Matrix(1, 2, doubleArrayOf(1.1, 2.2).toTypedArray())
        val matrix2 = Matrix(1, 2, doubleArrayOf(1.2, 2.3).toTypedArray())
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
        val matrix1 = Matrix(1, 2, doubleArrayOf(1.1, 2.2).toTypedArray())
        val matrix2 = Matrix(1, 2, doubleArrayOf(1.2, 2.3).toTypedArray())
        val matrix3 = matrix1 + matrix2
        Assert.assertEquals(2, matrix3.size())
    }

    /**
     * The method tests the Matrix.plusAssign() throws error when matrices have different dimensions
     * @see Matrix.plusAssign
     */
    @Test
    fun `matrix plusAssign dimension error testing`() {
        val exception = assertFailsWith<IllegalStateException> {
            val matrix1 = Matrix(2, 2, doubleArrayOf(1.1, 2.2, 3.3, 4.4).toTypedArray())
            val matrix2 = Matrix(1, 2, doubleArrayOf(1.2, 2.3).toTypedArray())
            matrix1 += matrix2
        }
        Assert.assertEquals("cannot add matrices of different dimensions", exception.message)
    }

    /**
     * The method tests the Matrix.plus() creates new matrix
     * @see Matrix.plus
     */
    @Test
    fun `matrix plusAssign creates new matrix testing`() {
        val matrix1 = Matrix(1, 2, doubleArrayOf(1.1, 2.2).toTypedArray())
        val matrix2 = Matrix(1, 2, doubleArrayOf(1.2, 2.3).toTypedArray())
        matrix1 += matrix2
        Assert.assertTrue(matrix1 === matrix1)
    }

    /**
     * The method tests the Matrix.plusAssign() works correct
     * @see Matrix.plusAssign
     */
    @Test
    fun `matrix plusAssign testing`() {
        val matrix1 = Matrix(1, 2, doubleArrayOf(1.1, 2.2).toTypedArray())
        val matrix2 = Matrix(1, 2, doubleArrayOf(1.2, 2.3).toTypedArray())
        matrix1 += matrix2
        Assert.assertEquals(2.3, matrix1[0, 0], 0.0)
        Assert.assertEquals(4.5, matrix1[0, 1], 0.0)
    }

    /**
     * The method tests the Matrix.minus() throws error when matrices have different dimensions
     * @see Matrix.minus
     */
    @Test
    fun `matrix minus dimension error testing`() {
        val exception = assertFailsWith<IllegalStateException> {
            val matrix1 = Matrix(2, 2, doubleArrayOf(1.1, 2.2, 3.3, 4.4).toTypedArray())
            val matrix2 = Matrix(1, 2, doubleArrayOf(1.2, 2.3).toTypedArray())
            matrix1 - matrix2
        }
        Assert.assertEquals("cannot substitute matrices of different dimensions", exception.message)
    }

    /**
     * The method tests the Matrix.minus() creates new matrix
     * @see Matrix.minus
     */
    @Test
    fun `matrix minus creates new matrix testing`() {
        val matrix1 = Matrix(1, 2, doubleArrayOf(1.1, 2.2).toTypedArray())
        val matrix2 = Matrix(1, 2, doubleArrayOf(1.2, 2.3).toTypedArray())
        val matrix3 = matrix1 - matrix2
        Assert.assertTrue(matrix1 !== matrix3)
    }

    /**
     * The method tests the Matrix.minus() works correct
     * @see Matrix.minus
     */
    @Test
    fun `matrix minus testing`() {
        val matrix1 = Matrix(1, 2, doubleArrayOf(1.1, 2.2).toTypedArray())
        val matrix2 = Matrix(1, 2, doubleArrayOf(1.2, 2.3).toTypedArray())
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
        val matrix1 = Matrix(1, 2, doubleArrayOf(1.1, 2.2).toTypedArray())
        val matrix2 = Matrix(1, 2, doubleArrayOf(1.2, 2.3).toTypedArray())
        val matrix3 = matrix1 - matrix2
        Assert.assertEquals(2, matrix3.size())
    }

    /**
     * The method tests the Matrix.minusAssign() throws error when matrices have different dimensions
     * @see Matrix.minusAssign
     */
    @Test
    fun `matrix minusAssign dimension error testing`() {
        val exception = assertFailsWith<IllegalStateException> {
            val matrix1 = Matrix(2, 2, doubleArrayOf(1.1, 2.2, 3.3, 4.4).toTypedArray())
            val matrix2 = Matrix(1, 2, doubleArrayOf(1.2, 2.3).toTypedArray())
            matrix1 -= matrix2
        }
        Assert.assertEquals("cannot substitute matrices of different dimensions", exception.message)
    }

    /**
     * The method tests the Matrix.minusAssign() works correct
     * @see Matrix.minusAssign
     */
    @Test
    fun `matrix minusAssign testing`() {
        val matrix1 = Matrix(1, 2, doubleArrayOf(1.1, 2.2).toTypedArray())
        val matrix2 = Matrix(1, 2, doubleArrayOf(1.2, 2.3).toTypedArray())
        matrix1 -= matrix2
        Assert.assertEquals(1.1 - 1.2, matrix1[0, 0], 0.0)
        Assert.assertEquals(2.2 - 2.3, matrix1[0, 1], 0.0)
    }

    /**
     * The method tests the Matrix.times() throws error when matrices have wrong dimensions
     * @see Matrix.times
     */
    @Test
    fun `matrix times dimension error testing`() {
        val exception = assertFailsWith<IllegalStateException> {
            val matrix1 = Matrix(2, 2, doubleArrayOf(1.1, 2.2, 3.3, 4.4).toTypedArray())
            val matrix2 = Matrix(1, 2, doubleArrayOf(1.2, 2.3).toTypedArray())
            matrix1 * matrix2
        }
        Assert.assertEquals("matrices of these sizes cannot be multiplied", exception.message)
    }

    /**
     * The method tests the Matrix.times() creates new matrix
     * @see Matrix.times
     */
    @Test
    fun `matrix times creates new matrix testing`() {
        val matrix1 = Matrix(1, 2, doubleArrayOf(1.1, 2.2).toTypedArray())
        val matrix2 = Matrix(2, 1, doubleArrayOf(1.2, 2.3).toTypedArray())
        val matrix3 = matrix1 * matrix2
        Assert.assertTrue(matrix1 !== matrix3)
    }

    /**
     * The method tests the Matrix.times() works correct
     * @see Matrix.times
     */
    @Test
    fun `matrix times testing`() {
        val matrix1 = Matrix(1, 2, doubleArrayOf(1.1, 2.2).toTypedArray())
        val matrix2 = Matrix(2, 2, doubleArrayOf(1.2, 2.3, 1.0, 1.0).toTypedArray())
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
        val matrix1 = Matrix(1, 2, doubleArrayOf(1.1, 2.2).toTypedArray())
        val matrix2 = Matrix(2, 1, doubleArrayOf(1.2, 2.3).toTypedArray())
        val matrix3 = matrix1 * matrix2
        Assert.assertEquals(1, matrix3.size())
    }

    /**
     * The method tests the Matrix.timesAssign() throws error when matrices have wrong dimensions
     * @see Matrix.timesAssign
     */
    @Test
    fun `matrix timesAssign dimension error testing`() {
        val exception = assertFailsWith<IllegalStateException> {
            val matrix1 = Matrix(2, 2, doubleArrayOf(1.1, 2.2, 3.3, 4.4).toTypedArray())
            val matrix2 = Matrix(1, 2, doubleArrayOf(1.2, 2.3).toTypedArray())
            matrix1 *= matrix2
        }
        Assert.assertEquals("matrices of these sizes cannot be multiplied", exception.message)
    }

    /**
     * The method tests the Matrix.timesAssign() works correct
     * @see Matrix.timesAssign
     */
    @Test
    fun `matrix timesAssign testing`() {
        val matrix1 = Matrix(1, 2, doubleArrayOf(1.1, 2.2).toTypedArray())
        val matrix2 = Matrix(2, 2, doubleArrayOf(1.2, 2.3, 1.0, 1.0).toTypedArray())
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
        val matrix1 = Matrix(1, 2, doubleArrayOf(1.1, 2.2).toTypedArray())
        val matrix2 = Matrix(2, 1, doubleArrayOf(1.2, 2.3).toTypedArray())
        matrix1 *= matrix2
        Assert.assertEquals(1, matrix1.size())
    }

    /**
     * The method tests the Matrix.div() throws error for non-square matrix
     * @see Matrix.div
     */
    @Test
    fun `div error for non-square matrix testing`() {
        val exception = assertFailsWith<IllegalStateException> {
            val matrix1 = Matrix(1, 2, doubleArrayOf(1.1, 2.2).toTypedArray())
            val matrix2 = Matrix(2, 2, doubleArrayOf(1.2, 2.3, 4.2, 3.1).toTypedArray())
            matrix1 / matrix2
        }
        Assert.assertEquals("division cannot be performed with non-square matrices", exception.message)
    }

    /**
     * The method tests the Matrix.div() throws error when divide by matrix with zero determinant
     * @see Matrix.div
     */
    @Test
    fun `error when divide on zero-determinant matrix testing`() {
        val exception = assertFailsWith<IllegalStateException> {
            val matrix1 = Matrix(2, 2, doubleArrayOf(1.1, 2.2, 3.3, 4.4).toTypedArray())
            val matrix2 = Matrix(2, 2, doubleArrayOf(1.0, 1.0, 4.0, 4.0).toTypedArray())
            matrix1 / matrix2
        }
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
        val matrix1 = Matrix(1, 1, doubleArrayOf(5.0).toTypedArray())
        val matrix2 = Matrix(1, 1, doubleArrayOf(1.0).toTypedArray())
        val matrix3 = matrix1 / matrix2
        Assert.assertEquals(5.0, matrix3[0, 0], 0.0)
    }

    /**
     * The method tests the Matrix.div() throws error for 1x1 divide by 1x1 matrix when divider = 0
     * @see Matrix.div
     */
    @Test
    fun `1x1 divide by 1x1 zero matrix throws error testing`() {
        val exception = assertFailsWith<IllegalStateException> {
            val matrix1 = Matrix(1, 1, doubleArrayOf(5.0).toTypedArray())
            val matrix2 = Matrix(1, 1, doubleArrayOf(0.0).toTypedArray())
            matrix1 / matrix2
        }
        Assert.assertEquals("it is forbidden to divide by zero", exception.message)
    }

    /**
     * The method tests the Matrix.div() works for 2x2 divide by 2x2 matrix
     * @see Matrix.div
     */
    @Test
    fun `2x2 divide by 2x2 matrix testing`() {
        val matrix1 = Matrix(2, 2, doubleArrayOf(1.0, 1.0, 1.0, 1.0).toTypedArray())
        val matrix2 = Matrix(2, 2, doubleArrayOf(3.0, 1.0, 5.0, 2.0).toTypedArray())
        val matrix3 = matrix1 / matrix2
        val answer = Matrix(2, 2, doubleArrayOf(-3.0, 2.0, -3.0, 2.0).toTypedArray())
        Assert.assertEquals(answer, matrix3)
    }

    /**
     * The method tests the Matrix.div() works for 3x3 divide by 3x3 matrix
     * @see Matrix.div
     */
    @Test
    fun `3x3 divide by 3x3 matrix testing`() {
        val matrix1 = Matrix(
            3, 3, doubleArrayOf(
                1.0, -1.0, 2.0,
                0.0, 2.0, -1.0,
                1.0, 0.0, 1.0
            ).toTypedArray()
        )
        val matrix2 = Matrix(
            3, 3, doubleArrayOf(
                1.0, -1.0, 2.0,
                0.0, 2.0, -1.0,
                1.0, 0.0, 1.0
            ).toTypedArray()
        )
        val matrix3 = matrix1 / matrix2
        val answer = Matrix(
            3, 3, doubleArrayOf(
                1.0, 0.0, 0.0,
                0.0, 1.0, 0.0,
                0.0, 0.0, 1.0
            ).toTypedArray()
        )
        Assert.assertEquals(answer, matrix3)
    }

    /**
     * The method tests the Matrix.divAssign() throws error for non-square matrix
     * @see Matrix.divAssign
     */
    @Test
    fun `divAssign error for non-square matrix testing`() {
        val exception = assertFailsWith<IllegalStateException> {
            val matrix1 = Matrix(1, 2, doubleArrayOf(1.1, 2.2).toTypedArray())
            val matrix2 = Matrix(2, 2, doubleArrayOf(1.2, 2.3, 4.2, 3.1).toTypedArray())
            matrix1 /= matrix2
        }
        Assert.assertEquals("division cannot be performed with non-square matrices", exception.message)
    }

    /**
     * The method tests the Matrix.divAssign() throws error when divide by matrix with zero determinant
     * @see Matrix.divAssign
     */
    @Test
    fun `error when divAssign on zero-determinant matrix testing`() {
        val exception = assertFailsWith<IllegalStateException> {
            val matrix1 = Matrix(2, 2, doubleArrayOf(1.1, 2.2, 3.3, 4.4).toTypedArray())
            val matrix2 = Matrix(2, 2, doubleArrayOf(1.0, 1.0, 4.0, 4.0).toTypedArray())
            matrix1 /= matrix2
        }
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
        val matrix1 = Matrix(1, 1, doubleArrayOf(5.0).toTypedArray())
        val matrix2 = Matrix(1, 1, doubleArrayOf(1.0).toTypedArray())
        matrix1 /= matrix2
        Assert.assertEquals(5.0, matrix1[0, 0], 0.0)
    }

    /**
     * The method tests the Matrix.divAssign() throws error for 1x1 divide by 1x1 matrix when divider = 0
     * @see Matrix.divAssign
     */
    @Test
    fun `1x1 divideAssign by 1x1 zero matrix throws error testing`() {
        val exception = assertFailsWith<IllegalStateException> {
            val matrix1 = Matrix(1, 1, doubleArrayOf(5.0).toTypedArray())
            val matrix2 = Matrix(1, 1, doubleArrayOf(0.0).toTypedArray())
            matrix1 /= matrix2
        }
        Assert.assertEquals("it is forbidden to divide by zero", exception.message)
    }

    /**
     * The method tests the Matrix.divAssign() works for 2x2 divide by 2x2 matrix
     * @see Matrix.divAssign
     */
    @Test
    fun `2x2 divideAssign by 2x2 matrix testing`() {
        val matrix1 = Matrix(2, 2, doubleArrayOf(1.0, 1.0, 1.0, 1.0).toTypedArray())
        val matrix2 = Matrix(2, 2, doubleArrayOf(3.0, 1.0, 5.0, 2.0).toTypedArray())
        matrix1 /= matrix2
        val answer = Matrix(2, 2, doubleArrayOf(-3.0, 2.0, -3.0, 2.0).toTypedArray())
        Assert.assertEquals(answer, matrix1)
    }

    /**
     * The method tests the Matrix.divAssign() works for 3x3 divide by 3x3 matrix
     * @see Matrix.divAssign
     */
    @Test
    fun `3x3 divideAssign by 3x3 matrix testing`() {
        val matrix1 = Matrix(
            3, 3, doubleArrayOf(
                1.0, -1.0, 2.0,
                0.0, 2.0, -1.0,
                1.0, 0.0, 1.0
            ).toTypedArray()
        )
        val matrix2 = Matrix(
            3, 3, doubleArrayOf(
                1.0, -1.0, 2.0,
                0.0, 2.0, -1.0,
                1.0, 0.0, 1.0
            ).toTypedArray()
        )
        matrix1 /= matrix2
        val answer = Matrix(
            3, 3, doubleArrayOf(
                1.0, 0.0, 0.0,
                0.0, 1.0, 0.0,
                0.0, 0.0, 1.0
            ).toTypedArray()
        )
        Assert.assertEquals(answer, matrix1)
    }

    /**
     * The method tests the matrix * scalar works for 2x2 matrix
     * @see Matrix.times
     */
    @Test
    fun `2x2 matrix times scalar testing`() {
        val matrix2 = Matrix(2, 2, doubleArrayOf(3.0, 1.0, 5.0, 2.0).toTypedArray())
        val scalar = 2.0
        val matrix1 = matrix2 * scalar
        val answer = Matrix(2, 2, doubleArrayOf(6.0, 2.0, 10.0, 4.0).toTypedArray())
        Assert.assertEquals(answer, matrix1)
    }

    /**
     * The method tests the scalar * matrix works for 2x2 matrix
     * @see Matrix.times
     */
    @Test
    fun `2x2 scalar times matrix testing`() {
        val matrix2 = Matrix(2, 2, doubleArrayOf(3.0, 1.0, 5.0, 2.0).toTypedArray())
        val scalar = 2.0
        val matrix1 = scalar * matrix2
        val answer = Matrix(2, 2, doubleArrayOf(6.0, 2.0, 10.0, 4.0).toTypedArray())
        Assert.assertEquals(answer, matrix1)
    }

    /**
     * The method tests the matrix *= scalar works for 2x2 matrix
     * @see Matrix.timesAssign
     */
    @Test
    fun `2x2 scalar timesAssign matrix testing`() {
        val matrix1 = Matrix(2, 2, doubleArrayOf(3.0, 1.0, 5.0, 2.0).toTypedArray())
        val scalar = 2.0
        matrix1 *= scalar
        val answer = Matrix(2, 2, doubleArrayOf(6.0, 2.0, 10.0, 4.0).toTypedArray())
        Assert.assertEquals(answer, matrix1)
    }

    /**
     * The method tests the matrix = matrix / scalar works for 2x2 matrix
     * @see Matrix.div
     */
    @Test
    fun `2x2 scalar div matrix testing`() {
        val matrix1 = Matrix(2, 2, doubleArrayOf(4.0, 0.0, 6.0, 8.0).toTypedArray())
        val scalar = 2.0
        val matrix2 = matrix1 / scalar
        val answer = Matrix(2, 2, doubleArrayOf(2.0, 0.0, 3.0, 4.0).toTypedArray())
        Assert.assertEquals(answer, matrix2)
    }

    /**
     * The method tests the matrix /= scalar works for 2x2 matrix
     * @see Matrix.divAssign
     */
    @Test
    fun `2x2 scalar divAssign matrix testing`() {
        val matrix1 = Matrix(2, 2, doubleArrayOf(4.0, 0.0, 6.0, 8.0).toTypedArray())
        val scalar = 2.0
        matrix1 /= scalar
        val answer = Matrix(2, 2, doubleArrayOf(2.0, 0.0, 3.0, 4.0).toTypedArray())
        Assert.assertEquals(answer, matrix1)
    }

    /**
     * The method tests the -matrix works
     * @see Matrix.unaryMinus
     */
    @Test
    fun `-matrix testing`() {
        var matrix1 = Matrix(2, 2, doubleArrayOf(4.0, 0.0, 6.0, 8.0).toTypedArray())
        matrix1 = -matrix1
        val answer = Matrix(2, 2, doubleArrayOf(-4.0, -0.0, -6.0, -8.0).toTypedArray())
        Assert.assertEquals(answer, matrix1)
    }

    /**
     * The method tests the +matrix works
     * @see Matrix.unaryPlus
     */
    @Test
    fun `+matrix testing`() {
        var matrix1 = Matrix(2, 2, doubleArrayOf(4.0, 0.0, 6.0, 8.0).toTypedArray())
        matrix1 = +matrix1
        val answer = Matrix(2, 2, doubleArrayOf(4.0, 0.0, 6.0, 8.0).toTypedArray())
        Assert.assertEquals(answer, matrix1)
    }

    /**
     * The method tests the matrix == matrix works
     * @see Matrix.equals
     */
    @Test
    fun `matrix equals testing`() {
        val matrix1 = Matrix(2, 2, doubleArrayOf(4.0, 0.0, 6.0, 8.0).toTypedArray())
        val matrix2 = Matrix(2, 2, doubleArrayOf(4.0, 0.0, 6.0, 8.0).toTypedArray())
        Assert.assertTrue(matrix1 == matrix2)
    }

    /**
     * The method tests the matrix.toString() prints matrix body
     * @see Matrix.toString
     */
    @Test
    fun `matrix to string testing`() {
        val matrix1 = Matrix(2, 2, doubleArrayOf(4.0, 0.0, 6.0, 8.0).toTypedArray())
        val answer = "4.0 0.0" + System.lineSeparator() + "6.0 8.0"
        Assert.assertEquals(answer, matrix1.toString())
    }
}