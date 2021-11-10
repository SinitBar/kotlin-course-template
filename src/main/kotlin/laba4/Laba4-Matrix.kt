package laba4

import java.lang.ArithmeticException

operator fun Double.times(other: Matrix): Matrix { // overload for scalar * matrix
    val newMatrix = Matrix(other.stringSize(), other.rowSize())
    for (i in 0 until other.stringSize())
        for (j in 0 until other.rowSize()) newMatrix[i, j] = other[i, j] * this
    return newMatrix
}

class Matrix(
    private var stringsAmount: Int, // m // made var to let them all be var
    private var rowsAmount: Int, // n // need to be var because of multiplication changes rows amount and body of left matrix (this)
    private var body: Array<Array<Double>> = Array( // two-dimensional array, dim(matrix) = m x n
        stringsAmount
    ) { Array(rowsAmount) { 0.0 } } // zero-filled matrix
) {
    init {
        if (stringsAmount <= 0 || rowsAmount <= 0) throw IllegalArgumentException("cannot create matrix with data size")
    }

    constructor(
        stringsAmount: Int,
        rowsAmount: Int,
        initialization: Array<Double>
    ) : this(stringsAmount, rowsAmount) {
        if (initialization.size != stringsAmount * rowsAmount)
            throw IllegalArgumentException("wrong amount of numbers, failed initialization")
        for (i in 0 until stringsAmount)
            for (j in 0 until rowsAmount)
                body[i][j] = initialization[i * rowsAmount + j]
    }

    fun size(): Int = stringsAmount * rowsAmount
    fun stringSize(): Int = stringsAmount
    fun rowSize(): Int = rowsAmount

    operator fun get(i: Int, j: Int): Double { // return matrix[i][j]
        if (i >= stringsAmount || j >= rowsAmount || i < 0 || j < 0) throw IllegalArgumentException("invalid index")
        return body[i][j]
    }

    operator fun set(i: Int, j: Int, value: Double) { // matrix[i][j] = double
        if (i >= stringsAmount || j >= rowsAmount || i < 0 || j < 0) throw IllegalArgumentException("invalid index")
        body[i][j] = value
    }

    fun getTransposed(): Matrix {
        val transposedMatrix = Matrix(stringsAmount, rowsAmount)
        for (i in 0 until stringsAmount)
            for (j in 0 until rowsAmount)
                transposedMatrix.body[i][j] = body[j][i]
        return transposedMatrix
    }

    fun getMinorMatrix(i: Int, j: Int): Matrix { // returns the subMatrix without i string and j row in data matrix
        if (rowsAmount == 1 && stringsAmount == 1) throw IllegalArgumentException("cannot get minor matrix from 1x1 matrix")
        if (rowsAmount != stringsAmount) throw IllegalArgumentException("minor doesn't exist for non-square matrices")
        if (i < 0 || j < 0 || i >= stringsAmount || j >= rowsAmount) throw IllegalArgumentException("invalid minor index")
        val minor = Matrix(rowsAmount - 1, rowsAmount - 1)
        for (n in 0 until stringsAmount)
            for (m in 0 until rowsAmount)
                if (i != n && j != m)
                    minor[if (n > i) n - 1 else n, if (m > j) m - 1 else m] = body[n][m]
        return minor
    }

    fun calcDeterminant(): Double {
        if (rowsAmount != stringsAmount)
            throw IllegalArgumentException("cannot calculate determinant of non-square matrix")
        if (rowsAmount <= 2) {
            return (
                    if (rowsAmount == 1) body[0][0] // matrix 1 x 1
                    else (body[0][0] * body[1][1] - body[0][1] * body[1][0]) // matrix 2 x 2
                    )
        }
        var det = 0.0
        for (k in 0 until stringsAmount) // determinant is being expanded on the first line
            det += (if (k.rem(2) == 1) -1 else 1) * body[0][k] * getMinor(0, k)
        return det
    }

    fun getMinor(i: Int, j: Int): Double {
        return getMinorMatrix(i, j).calcDeterminant()
    }

    fun getComplementMatrix(): Matrix {
        if (rowsAmount == 1 && stringsAmount == 1)
            return Matrix(stringsAmount, rowsAmount, doubleArrayOf(1.0 / body[0][0]).toTypedArray())
        if (rowsAmount != stringsAmount) throw IllegalArgumentException("complement matrix doesn't exist for non-square matrices")
        val complementMatrix = Matrix(stringsAmount, rowsAmount) // matrix of algebraic complements
        for (i in 0 until stringsAmount)
            for (j in 0 until rowsAmount) {
                complementMatrix[i, j] =
                    (if ((i + j).rem(2) == 1) -1 else 1) * getMinorMatrix(i, j).calcDeterminant()
            }
        return complementMatrix
    }

    operator fun plus(other: Matrix): Matrix { // matrix + matrix = new matrix
        if (stringsAmount != other.stringsAmount || rowsAmount != other.rowsAmount)
            throw IllegalArgumentException("cannot add matrices of different dimensions")
        val newMatrix = Matrix(stringsAmount, rowsAmount)
        for (i in 0 until stringsAmount)
            for (j in 0 until rowsAmount)
                newMatrix.body[i][j] = body[i][j] + other.body[i][j]
        return newMatrix
    }

    operator fun plusAssign(other: Matrix) { // matrix += matrix
        if (stringsAmount != other.stringsAmount || rowsAmount != other.rowsAmount)
            throw IllegalArgumentException("cannot add matrices of different dimensions")
        for (i in 0 until stringsAmount) {
            for (j in 0 until rowsAmount)
                body[i][j] += other.body[i][j]
        }
    }

    operator fun minus(other: Matrix): Matrix { // matrix - matrix = new matrix
        if (stringsAmount != other.stringsAmount || rowsAmount != other.rowsAmount)
            throw IllegalArgumentException("cannot substitute matrices of different dimensions")
        val newMatrix = Matrix(stringsAmount, rowsAmount)
        for (i in 0 until stringsAmount) {
            for (j in 0 until rowsAmount)
                newMatrix.body[i][j] = body[i][j] - other.body[i][j]
        }
        return newMatrix
    }

    operator fun minusAssign(other: Matrix) { // matrix -= matrix
        if (stringsAmount != other.stringsAmount || rowsAmount != other.rowsAmount)
            throw IllegalArgumentException("cannot substitute matrices of different dimensions")
        for (i in 0 until stringsAmount) {
            for (j in 0 until rowsAmount)
                body[i][j] -= other.body[i][j]
        }
    }

    operator fun times(other: Matrix): Matrix { // matrix * matrix = new matrix
        if (rowsAmount != other.stringsAmount) throw IllegalArgumentException("matrices of these sizes cannot be multiplied")
        val newMatrix = Matrix(stringsAmount, other.rowsAmount)
        for (i in 0 until stringsAmount)
            for (j in 0 until other.rowsAmount)
                for (k in 0 until rowsAmount)
                    newMatrix.body[i][j] += body[i][k] * other.body[k][j]
        return newMatrix
    }

    operator fun timesAssign(other: Matrix) { // matrix *= matrix
        if (rowsAmount != other.stringsAmount) throw IllegalArgumentException("matrices of these sizes cannot be multiplied")
        body = (this * other).body
        rowsAmount = other.rowsAmount
    }

    operator fun div(other: Matrix): Matrix { // matrix / matrix = new matrix
        if (rowsAmount != stringsAmount || other.stringsAmount != other.rowsAmount ||
            stringsAmount != other.stringsAmount
        ) throw IllegalArgumentException("division cannot be performed with non-square matrices")
        if (stringsAmount == 1 && other[0, 0] != 0.0)
            return Matrix(1, 1, doubleArrayOf(this[0, 0] / other[0, 0]).toTypedArray())
        else if (other[0, 0] == 0.0) throw ArithmeticException("it is forbidden to divide by zero")
        val det = other.calcDeterminant()
        if (det == 0.0) throw IllegalArgumentException(
            "the right matrix does not have an inverse matrix, division cannot be performed"
        )
        return (this * (other.getComplementMatrix().getTransposed() * (1 / det)))
    }

    operator fun divAssign(other: Matrix) { // matrix /= matrix
        if (rowsAmount != stringsAmount || other.stringsAmount != other.rowsAmount ||
            stringsAmount != other.stringsAmount
        ) throw IllegalArgumentException("division cannot be performed with non-square matrices")
        if (stringsAmount == 1 && other[0, 0] != 0.0)
            this[0, 0] /= other[0, 0]
        else if (other[0, 0] == 0.0) throw ArithmeticException("it is forbidden to divide by zero")
        val det = other.calcDeterminant()
        if (det == 0.0) throw IllegalArgumentException(
            "the right matrix does not have an inverse matrix, division cannot be performed"
        )
        body = (this / other).body
    }

    operator fun times(scalar: Double): Matrix { // matrix * scalar = new matrix
        val newMatrix = Matrix(stringsAmount, rowsAmount)
        for (i in 0 until stringsAmount)
            for (j in 0 until rowsAmount) newMatrix.body[i][j] = body[i][j] * scalar
        return newMatrix
    }

    operator fun timesAssign(scalar: Double) { // matrix *= scalar
        for (i in 0 until stringsAmount)
            for (j in 0 until rowsAmount) body[i][j] *= scalar
    }

    operator fun div(scalar: Double): Matrix { // matrix / scalar = new matrix
        if (scalar == 0.0) throw ArithmeticException("it is forbidden to divide by zero")
        val newMatrix = Matrix(stringsAmount, rowsAmount)
        for (i in 0 until stringsAmount)
            for (j in 0 until rowsAmount) newMatrix.body[i][j] = body[i][j] / scalar
        return newMatrix
    }

    operator fun divAssign(scalar: Double) { // matrix /= scalar
        if (scalar == 0.0) throw ArithmeticException("it is forbidden to divide by zero")
        for (i in 0 until stringsAmount)
            for (j in 0 until rowsAmount) body[i][j] /= scalar
    }

    operator fun unaryMinus(): Matrix { // matrix = -matrix
        val newMatrix = Matrix(stringsAmount, rowsAmount)
        for (i in 0 until stringsAmount)
            for (j in 0 until rowsAmount) newMatrix.body[i][j] = -body[i][j]
        return newMatrix
    }

    operator fun unaryPlus(): Matrix { // matrix = +matrix
        return this
    }

    override fun toString(): String {
        return buildString {
            for (i in 0 until stringsAmount)
                for (j in 0 until rowsAmount)
                    append(
                        "${body[i][j]}",
                        if (j == rowsAmount - 1 && i != stringsAmount - 1
                        ) System.lineSeparator() else {
                            if (j != rowsAmount - 1 || i != stringsAmount - 1) " " else ""
                        }
                    )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Matrix

        if (stringsAmount != other.stringsAmount) return false
        if (rowsAmount != other.rowsAmount) return false
        if (!body.contentDeepEquals(other.body)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = stringsAmount
        result = 31 * result + rowsAmount
        result = 31 * result + body.contentDeepHashCode()
        return result
    }
}