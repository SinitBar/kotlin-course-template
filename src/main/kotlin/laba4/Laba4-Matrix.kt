package laba4

import java.lang.ArithmeticException

operator fun Double.times(other: Matrix): Matrix { // overload for scalar * matrix
    val newMatrix = Matrix(other.toTwoDimensionalArray())
    for (i in 0 until other.lineSize())
        for (j in 0 until other.columnSize()) newMatrix[i, j] = other[i, j] * this
    return newMatrix
}

fun copyArray(arr: Array<Array<Double>>): Array<Array<Double>> {
    val newArr: Array<Array<Double>> = Array(arr.size) { Array(arr[0].size) { 0.0 } }
    for (i in arr.indices) {
        for (j in 0 until arr[0].size) newArr[i][j] = arr[i][j]
    }
    return newArr
}

class Matrix(initialization: Array<Array<Double>>) {

    private var body: Array<Array<Double>> = copyArray(initialization)// two-dimensional array, dim(matrix) = m x n

    init {
        if (body.isEmpty()) throw IllegalArgumentException("can't create empty matrix")
        for (i in 1 until body.size) {
            if (body[i].size != body[0].size) throw IllegalArgumentException("cannot create matrix with data sizes")
        }
    }

    fun toTwoDimensionalArray(): Array<Array<Double>> = copyArray(body)
    fun size(): Int = columnSize() * lineSize()
    fun columnSize(): Int = body.size // amount of lines in the matrix, m
    fun lineSize(): Int = body[0].size // amount of columns in the matrix, n

    operator fun get(i: Int, j: Int): Double { // return matrix[i][j]
        if (i >= columnSize() || j >= lineSize() || i < 0 || j < 0) throw IllegalArgumentException("invalid index")
        return body[i][j]
    }

    operator fun set(i: Int, j: Int, value: Double) { // matrix[i][j] = double
        if (i >= columnSize() || j >= lineSize() || i < 0 || j < 0) throw IllegalArgumentException("invalid index")
        body[i][j] = value
    }

    fun getTransposed(): Matrix {
        val bodyArray = Array(columnSize()) { Array(lineSize()) { 0.0 } }
        for (i in 0 until columnSize())
            for (j in 0 until lineSize())
                bodyArray[i][j] = body[j][i]
        return Matrix(bodyArray)
    }

    fun getMinorMatrix(i: Int, j: Int): Matrix { // returns the subMatrix without i string and j row in data matrix
        if (lineSize() == 1 && columnSize() == 1) throw IllegalArgumentException("cannot get minor matrix from 1x1 matrix")
        if (lineSize() != columnSize()) throw IllegalArgumentException("minor doesn't exist for non-square matrices")
        if (i < 0 || j < 0 || i >= columnSize() || j >= lineSize()) throw IllegalArgumentException("invalid minor index")
        val minorArray = Array(columnSize() - 1) { Array(lineSize() - 1) { 0.0 } }
        for (n in 0 until columnSize())
            for (m in 0 until lineSize())
                if (i != n && j != m)
                    minorArray[if (n > i) n - 1 else n][if (m > j) m - 1 else m] = body[n][m]
        return Matrix(minorArray)
    }

    fun calcDeterminant(): Double {
        if (lineSize() != columnSize())
            throw IllegalArgumentException("cannot calculate determinant of non-square matrix")
        if (lineSize() <= 2) {
            return (
                    if (lineSize() == 1) body[0][0] // matrix 1 x 1
                    else (body[0][0] * body[1][1] - body[0][1] * body[1][0]) // matrix 2 x 2
                    )
        }
        var det = 0.0
        for (k in 0 until columnSize()) // determinant is being expanded on the first line
            det += (if (k.rem(2) == 1) -1 else 1) * body[0][k] * getMinor(0, k)
        return det
    }

    fun getMinor(i: Int, j: Int): Double {
        return getMinorMatrix(i, j).calcDeterminant()
    }

    fun getComplementMatrix(): Matrix {
        if (lineSize() == 1 && columnSize() == 1)
            return Matrix(Array(1) { Array(1) { 1.0 / body[0][0] } })
        if (lineSize() != columnSize()) throw IllegalArgumentException("complement matrix doesn't exist for non-square matrices")
        val complementArray = Array(columnSize()) { Array(lineSize()) { 0.0 } } // matrix of algebraic complements
        for (i in 0 until columnSize())
            for (j in 0 until lineSize()) {
                complementArray[i][j] =
                    (if ((i + j).rem(2) == 1) -1 else 1) * getMinorMatrix(i, j).calcDeterminant()
            }
        return Matrix(complementArray)
    }

    operator fun plus(other: Matrix): Matrix { // matrix + matrix = new matrix
        if (columnSize() != other.columnSize() || lineSize() != other.lineSize())
            throw IllegalArgumentException("cannot add matrices of different dimensions")
        val newArray = Array(columnSize()) { Array(lineSize()) { 0.0 } }
        for (i in 0 until columnSize())
            for (j in 0 until lineSize())
                newArray[i][j] = body[i][j] + other.body[i][j]
        return Matrix(newArray)
    }

    operator fun plusAssign(other: Matrix) { // matrix += matrix
        if (columnSize() != other.columnSize() || lineSize() != other.lineSize())
            throw IllegalArgumentException("cannot add matrices of different dimensions")
        for (i in 0 until columnSize()) {
            for (j in 0 until lineSize())
                body[i][j] += other.body[i][j]
        }
    }

    operator fun minus(other: Matrix): Matrix { // matrix - matrix = new matrix
        if (columnSize() != other.columnSize() || lineSize() != other.lineSize())
            throw IllegalArgumentException("cannot substitute matrices of different dimensions")
        val newArray = Array(columnSize()) { Array(lineSize()) { 0.0 } }
        for (i in 0 until columnSize()) {
            for (j in 0 until lineSize())
                newArray[i][j] = body[i][j] - other.body[i][j]
        }
        return Matrix(newArray)
    }

    operator fun minusAssign(other: Matrix) { // matrix -= matrix
        if (columnSize() != other.columnSize() || lineSize() != other.lineSize())
            throw IllegalArgumentException("cannot substitute matrices of different dimensions")
        for (i in 0 until columnSize()) {
            for (j in 0 until lineSize())
                body[i][j] -= other.body[i][j]
        }
    }

    operator fun times(other: Matrix): Matrix { // matrix * matrix = new matrix
        if (lineSize() != other.columnSize()) throw IllegalArgumentException("matrices of these sizes cannot be multiplied")
        val newArray = Array(columnSize()) { Array(other.lineSize()) { 0.0 } }
        for (i in 0 until columnSize())
            for (j in 0 until other.lineSize())
                for (k in 0 until lineSize())
                    newArray[i][j] += body[i][k] * other.body[k][j]
        return Matrix(newArray)
    }

    operator fun timesAssign(other: Matrix) { // matrix *= matrix
        if (lineSize() != other.columnSize()) throw IllegalArgumentException("matrices of these sizes cannot be multiplied")
        body = (this * other).body
    }

    operator fun div(other: Matrix): Matrix { // matrix / matrix = new matrix
        if (lineSize() != columnSize() || other.columnSize() != other.lineSize() ||
            columnSize() != other.columnSize()
        ) throw IllegalArgumentException("division cannot be performed with non-square matrices")
        if (columnSize() == 1 && other[0, 0] != 0.0)
            return Matrix(Array(1) { Array(1) { this[0, 0] / other[0, 0] } })
        else if (other[0, 0] == 0.0) throw ArithmeticException("it is forbidden to divide by zero")
        val det = other.calcDeterminant()
        if (det == 0.0) throw IllegalArgumentException(
            "the right matrix does not have an inverse matrix, division cannot be performed"
        )
        return (this * (other.getComplementMatrix().getTransposed() * (1 / det)))
    }

    operator fun divAssign(other: Matrix) { // matrix /= matrix
        if (lineSize() != columnSize() || other.columnSize() != other.lineSize() ||
            columnSize() != other.columnSize()
        ) throw IllegalArgumentException("division cannot be performed with non-square matrices")
        if (columnSize() == 1 && other[0, 0] != 0.0)
            this[0, 0] /= other[0, 0]
        else if (other[0, 0] == 0.0) throw ArithmeticException("it is forbidden to divide by zero")
        val det = other.calcDeterminant()
        if (det == 0.0) throw IllegalArgumentException(
            "the right matrix does not have an inverse matrix, division cannot be performed"
        )
        body = (this / other).body
    }

    operator fun times(scalar: Double): Matrix { // matrix * scalar = new matrix
        val newArray = Array(columnSize()) { Array(lineSize()) { 0.0 } }
        for (i in 0 until columnSize())
            for (j in 0 until lineSize()) newArray[i][j] = body[i][j] * scalar
        return Matrix(newArray)
    }

    operator fun timesAssign(scalar: Double) { // matrix *= scalar
        for (i in 0 until columnSize())
            for (j in 0 until lineSize()) body[i][j] *= scalar
    }

    operator fun div(scalar: Double): Matrix { // matrix / scalar = new matrix
        if (scalar == 0.0) throw ArithmeticException("it is forbidden to divide by zero")
        val newArray = Array(columnSize()) { Array(lineSize()) { 0.0 } }
        for (i in 0 until columnSize())
            for (j in 0 until lineSize()) newArray[i][j] = body[i][j] / scalar
        return Matrix(newArray)
    }

    operator fun divAssign(scalar: Double) { // matrix /= scalar
        if (scalar == 0.0) throw ArithmeticException("it is forbidden to divide by zero")
        for (i in 0 until columnSize())
            for (j in 0 until lineSize()) body[i][j] /= scalar
    }

    operator fun unaryMinus(): Matrix { // matrix = -matrix
        val newArray = Array(columnSize()) { Array(lineSize()) { 0.0 } }
        for (i in 0 until columnSize())
            for (j in 0 until lineSize()) newArray[i][j] = -body[i][j]
        return Matrix(newArray)
    }

    operator fun unaryPlus(): Matrix { // matrix = +matrix
        return this
    }

    override fun toString(): String {
        return buildString {
            for (i in 0 until columnSize())
                for (j in 0 until lineSize())
                    append(
                        "${body[i][j]}",
                        if (j == lineSize() - 1 && i != columnSize() - 1
                        ) System.lineSeparator() else {
                            if (j != lineSize() - 1 || i != columnSize() - 1) " " else ""
                        }
                    )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Matrix

        if (columnSize() != other.columnSize()) return false
        if (lineSize() != other.lineSize()) return false
        if (!body.contentDeepEquals(other.body)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = columnSize()
        result = 31 * result + lineSize()
        result = 31 * result + body.contentDeepHashCode()
        return result
    }
}