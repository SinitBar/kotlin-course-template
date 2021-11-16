package laba4

fun main() {
    val matrix0 = Matrix(Array(2) { Array(2) { 1.0 } })
    // will be filled by ones
    println("get matrix[i, j]: ${matrix0[0, 1]}")
    matrix0[0, 1] = 1.5
    println("matrix[i, j] = number: ${matrix0[0, 1]}")
    val matr2 = Matrix(Array(2) { Array(2) { 0.0 } })
    println("initialized by default is fill matrix with zeros: ")
    println(matr2.toString())
    val matrix3 = Matrix(arrayOf(arrayOf(1.1, 2.2), arrayOf(3.3, 4.4)))
    // Array<Double>(4, {1.1; 2.2; 3.3; 4.4}))
    println("second way to initialize:")
    println(matrix3.toString())
    println("matrix.toString():")
    println(matrix3.toString())
    println("matrix.size() (all elements): ${matrix3.size()}")
    println("amount of elements in the string: ${matrix3.lineSize()}")
    println("amount of elements in row: ${matrix3.columnSize()}")
    val matrix4 = Matrix(Array(2) { Array(2) { 0.0 } })
    println("equals: ${matr2 == matrix4}")
    println("unary minus:")
    println((-matrix3 + matrix0).toString())
    println("unary plus:")
    println((+matrix0).toString())
    var matrix2 = Matrix(arrayOf(arrayOf(1.0, 1.0), arrayOf(4.0, 4.0)))
    println("calculate determinant: ${matrix2.calcDeterminant()}")
    matrix2 = matrix3 * 2.0
    println("matrix * scalar:")
    println(matrix2.toString())
    matrix2 = matrix3 / 2.0
    println("matrix / scalar:")
    println(matrix2.toString())
    matrix2 = 2.0 * matrix3
    println("scalar * matrix:")
    println(matrix2.toString())
    matrix3 *= 2.0
    println("matrix *= scalar:")
    println(matrix3.toString())
    matrix3 /= 2.0
    println("matrix /= scalar:")
    println(matrix3.toString())
    val matrix5 = matrix0 + matrix3
    println("matrix + matrix:")
    println(matrix5)
    val matrix6 = matrix0 - matrix3
    println("matrix - matrix:")
    println(matrix6)
    matrix5 += matrix2
    println("matrix += matrix")
    println(matrix5)
    matrix5 -= matrix2
    println("matrix -= matrix")
    println(matrix5)
    val matrix7 = matrix5 * matrix3
    println("matrix = matrix * matrix:")
    println(matrix7)
    matrix7 *= matrix5
    println("matrix *= matrix:")
    println(matrix7)
    println("transposed:")
    println(matrix7.getTransposed().toString())
    println("get minor 0 1 matrix:")
    println(matrix7.getMinorMatrix(0, 1).toString())
    println("get minor 0 1: ${matrix7.getMinor(0, 1)}")
    println("get complement matrix:")
    println(matrix0.getComplementMatrix().toString())
    val matrix = Matrix(
        arrayOf(
            arrayOf(1.0, -1.0, 2.0),
            arrayOf(0.0, 2.0, -1.0),
            arrayOf(1.0, 0.0, 1.0)
        )
    )
    val matrix1 = Matrix(
        arrayOf(
            arrayOf(1.0, -1.0, 2.0),
            arrayOf(0.0, 2.0, -1.0),
            arrayOf(1.0, 0.0, 1.0)
        )
    )
    println("matrix / matrix:")
    println((matrix / matrix1).toString())
    matrix /= matrix1
    println("matrix /= matrix:")
    println(matrix.toString())
    val array = arrayOf(arrayOf(1.0, 2.0), arrayOf(3.0, 4.0))
    val newMatrix = Matrix(array) // demonstrate matrix independence from initial array
    println("before changing the initial array: ${newMatrix[0,0]}")
    array[0][0] = -5.0
    println("after changing the initial array: ${newMatrix[0,0]}")
}