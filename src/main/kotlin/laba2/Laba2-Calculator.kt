import java.util.Stack
import kotlin.math.*

enum class OPERATORS(val char: Char) {
    OPEN_BRACKET_CHAR('('),
    CLOSE_BRACKET_CHAR(')'),
    MINUS_CHAR('-'),
    PLUS_CHAR('+'),
    MULTIPLY_CHAR('*'),
    DIVIDE_CHAR('/'),
    DEGREE_CHAR('^'),
}

val FIRST_DIGIT_IN_NUMBER_RANGE_CHAR = '0'..'9'

fun minusPlusProcessing(
    operatorsStack: Stack<String>,
    operandsStack: Stack<Double>,
    index: Int,
    expr: String,
) { // should do all operations while there is no (
    while (!operatorsStack.empty() && operatorsStack.peek() != OPERATORS.OPEN_BRACKET_CHAR.char.toString()) {
        if (operandsStack.size < 2)
            throw IllegalArgumentException("there is not enough operands, index: $index")
        val second = operandsStack.pop()
        operandsStack.push(
            when (operatorsStack.pop()) {
                OPERATORS.MINUS_CHAR.char.toString() -> operandsStack.pop() - second
                OPERATORS.PLUS_CHAR.char.toString() -> operandsStack.pop() + second
                OPERATORS.MULTIPLY_CHAR.char.toString() -> operandsStack.pop() * second
                OPERATORS.DIVIDE_CHAR.char.toString() -> operandsStack.pop() / second
                OPERATORS.DEGREE_CHAR.char.toString() -> second.pow(operandsStack.pop())
                else -> throw IllegalArgumentException(
                    "never should happen, the program is bad if it is, index: $index"
                )
            }
        )
    }
    if (operatorsStack.empty() || operatorsStack.peek() == OPERATORS.OPEN_BRACKET_CHAR.char.toString()) {
        operatorsStack.push(expr[index].toString())
    } else throw IllegalArgumentException("not enough brackets, index: $index")
// situation like sin5, not sin(5) like should
}

fun multiplyDivideProcessing(
    operatorsStack: Stack<String>,
    operandsStack: Stack<Double>,
    index: Int,
    expr: String,
) { // should do all operations while there is no ( or + or -
    while (!operatorsStack.empty() && operatorsStack.peek() != OPERATORS.OPEN_BRACKET_CHAR.char.toString() &&
        operatorsStack.peek() != OPERATORS.MINUS_CHAR.char.toString() &&
        operatorsStack.peek() != OPERATORS.PLUS_CHAR.char.toString()
    ) {
        if (operandsStack.size < 2) throw IllegalArgumentException("there is not enough operands, index: $index")
        val second = operandsStack.pop()
        operandsStack.push(
            when (operatorsStack.pop()) {
                OPERATORS.MULTIPLY_CHAR.char.toString() -> operandsStack.pop() * second
                OPERATORS.DIVIDE_CHAR.char.toString() -> operandsStack.pop() / second
                OPERATORS.DEGREE_CHAR.char.toString() -> second.pow(operandsStack.pop())
                else -> throw IllegalStateException("never should happen, the program is bad if it is, index: $index")
            }
        )
    }
    if (operatorsStack.empty() || operatorsStack.peek() == OPERATORS.OPEN_BRACKET_CHAR.char.toString() ||
        operatorsStack.peek() == OPERATORS.MINUS_CHAR.char.toString() ||
        operatorsStack.peek() == OPERATORS.PLUS_CHAR.char.toString()
    ) {
        operatorsStack.push(expr[index].toString())
    } else throw IllegalArgumentException("not enough brackets, index: $index") // another error shouldn't be
}

fun degreeProcessing(
    operatorsStack: Stack<String>,
    operandsStack: Stack<Double>,
    index: Int,
    expr: String,
) {
    while (!operatorsStack.empty() && (operatorsStack.peek() == OPERATORS.DEGREE_CHAR.char.toString())) {
        if (operandsStack.size < 2)
            throw IllegalArgumentException("there is not enough operands, index: $index")
        val degree = operandsStack.pop()
        operandsStack.push(operandsStack.pop().pow(degree))
    }
    if (operatorsStack.empty() || operatorsStack.peek() != OPERATORS.DEGREE_CHAR.char.toString())
        operatorsStack.push(expr[index].toString())
}

fun numberProcessing(
    operandsStack: Stack<Double>,
    index: Int,
    expr: String,
): Int { // if it is a number it starts from one of 0..9
    var indexCopy = index
    while (indexCopy + 1 < expr.length) {
        if (expr[indexCopy + 1] == '.') { // dot could be in number
            indexCopy++
            continue
        }
        if (expr[indexCopy + 1] in FIRST_DIGIT_IN_NUMBER_RANGE_CHAR) indexCopy++ else break
        // finds continuous sequence of numbers
    }
    val number: Double? =
        expr.substring(index, indexCopy + 1).toDoubleOrNull() // if there are several dots, will return null
    operandsStack.push(number ?: throw IllegalArgumentException("unable to parse a number, index: $indexCopy"))
    return indexCopy
}

fun pushParsedFunction(
    // try parse one or one of two data functions with similar length and push to operatorsStack
    expr: String,
    indexCopy: Int,
    operatorsStack: Stack<String>,
    string1: String,
    string2: String = "",
): Int {
    if (indexCopy + string1.length >= expr.length)
        throw IllegalArgumentException("unknown function or word, index: $indexCopy")
    var word = ""
    word = buildString {
        for (i in string1.indices) append(expr[indexCopy + i], word) // string1 and string2 have similar length
    }
    if (word == string1 || (string2.isNotEmpty() && word == string2)) operatorsStack.push(word)
    else throw IllegalArgumentException("unknown function or word, index: $indexCopy")
    return indexCopy + string1.length - 1
}

fun functionConstantProcessing(
    operatorsStack: Stack<String>,
    operandsStack: Stack<Double>,
    index: Int,
    expr: String,
): Int { // letters which could be first in the names of functions or pi and e constants
    var indexCopy = index
    when (expr[indexCopy]) {

        's' -> indexCopy = pushParsedFunction(expr, indexCopy, operatorsStack, "sin")
        'c' -> indexCopy = pushParsedFunction(expr, indexCopy, operatorsStack, "cos", "ctg")
        't' -> indexCopy = pushParsedFunction(expr, indexCopy, operatorsStack, "tg")
        'l' -> indexCopy = pushParsedFunction(expr, indexCopy, operatorsStack, "lg", "ln")
        'e' -> operandsStack.push(E)

        'p' -> { // pi
            if (indexCopy + 1 < expr.length) {
                if (expr[++indexCopy] == 'i') operandsStack.push(PI)
                else throw IllegalArgumentException("unknown function or word, index: $indexCopy")
            } else throw IllegalArgumentException("unknown function or word, index: $indexCopy")
        }
    }
    return indexCopy
}

fun openBracketProcessing(
    operatorsStack: Stack<String>,
    operandsStack: Stack<Double>,
    index: Int,
    second: Double,
) {
    operandsStack.push(second) // ( need no operand but already took one for cases of binary operations, should put back
    if (operatorsStack.empty()) return
    // if before the bracket there was a function should calc it, it's argument - the last in operands stack
    when (operatorsStack.peek()) {
        "sin" -> operandsStack.push(sin(operandsStack.pop()))
        "cos" -> operandsStack.push(cos(operandsStack.pop()))

        "tg" -> {
            operandsStack.push(
                tan(
                    if (tan(second).isNaN()) throw IllegalArgumentException("wrong tg argument, index: $index")
                    else operandsStack.pop()
                )
            )
        }

        "ctg" -> {
            operandsStack.push(
                1 / tan(
                    if ((1 / tan(second)).isNaN()) throw IllegalArgumentException("wrong ctg argument, index: $index")
                    else operandsStack.pop()
                )
            )
        }

        "ln" -> {
            operandsStack.push(
                ln(
                    if ((ln(second)).isNaN()) throw IllegalArgumentException("wrong ln argument, index: $index")
                    else operandsStack.pop()
                )
            )
        }

        "lg" -> {
            operandsStack.push(
                log10(
                    if ((log10(second)).isNaN()) throw IllegalArgumentException("wrong lg argument, index: $index")
                    else operandsStack.pop()
                )
            )
        }

        else -> return // there are other operators (not functions) before found (
    }
    operatorsStack.pop()
    return // if found ( no need to continue searching, it could be the last operator at all
}

fun closeBracketProcessing(
    operatorsStack: Stack<String>,
    operandsStack: Stack<Double>,
    index: Int,
) {
    if (operatorsStack.empty()) throw IllegalArgumentException("open bracket is missing, index: $index")
    if (operandsStack.empty()) throw IllegalArgumentException("operand is missing, index: $index")
    var foundOpenBracket = false
    while (!operatorsStack.empty()) {
        val second = operandsStack.pop() // have to do it because operands in stack are in reversed order
        when (operatorsStack.pop()) {
            OPERATORS.MINUS_CHAR.char.toString() -> operandsStack.push(operandsStack.pop() - second)
            OPERATORS.PLUS_CHAR.char.toString() -> operandsStack.push(operandsStack.pop() + second)
            OPERATORS.MULTIPLY_CHAR.char.toString() -> operandsStack.push(operandsStack.pop() * second)
            OPERATORS.DIVIDE_CHAR.char.toString() -> operandsStack.push(operandsStack.pop() / second)
            OPERATORS.DEGREE_CHAR.char.toString() -> operandsStack.push(second.pow(operandsStack.pop()))

            OPERATORS.OPEN_BRACKET_CHAR.char.toString() -> {
                foundOpenBracket = true
                openBracketProcessing(operatorsStack, operandsStack, index, second)
                break
            }

            else -> throw IllegalArgumentException("not enough open brackets, index: $index")
            // before sin in stack always (, otherwise wrong
        }
    }
    if (!foundOpenBracket) throw IllegalArgumentException("open bracket is missing, index: $index")
}

fun simplifyExpression(
    // simplify expression be dropping extra spaces and processing unary minuses and pluses
    expression: String,
): String {
    var result = expression.filter { !it.isWhitespace() } // deleting all spaces
    result = result.replace(
        "${OPERATORS.OPEN_BRACKET_CHAR.char}${OPERATORS.MINUS_CHAR.char}",
        "${OPERATORS.OPEN_BRACKET_CHAR.char}0${OPERATORS.MINUS_CHAR.char}"
    ) // processing unary minus
    result = result.replace(
        "${OPERATORS.OPEN_BRACKET_CHAR.char}${OPERATORS.PLUS_CHAR.char}",
        OPERATORS.OPEN_BRACKET_CHAR.char.toString()
    ) // processing unary pluses
    result = if (result[0] == OPERATORS.MINUS_CHAR.char) result.padStart(1 + result.length, '0')
    else {
        if (result[0] == OPERATORS.PLUS_CHAR.char) result.drop(1) else result
    } // processing minus or plus in the beginning
    println("expression to calculate: $result")
    return result
}

fun calculateFromString(
    expression: String,
): Double {
    val expr: String = buildString {
        append(
            OPERATORS.OPEN_BRACKET_CHAR.char,
            simplifyExpression(expression),
            OPERATORS.CLOSE_BRACKET_CHAR.char
        ) // simplified data expression
    }
    val calculated: Double // the result of calculation
    val operandsStack = Stack<Double>() // numbers and constants (pi and e)
    val operatorsStack = Stack<String>() // operations and functions
    var index = 0
    while (index < expr.length) { // go through chars in string
        when (expr[index]) {
            OPERATORS.OPEN_BRACKET_CHAR.char -> operatorsStack.push(OPERATORS.OPEN_BRACKET_CHAR.char.toString())
            // open bracket always go to operators stack
            in FIRST_DIGIT_IN_NUMBER_RANGE_CHAR -> index = numberProcessing(operandsStack, index, expr)
            OPERATORS.MINUS_CHAR.char, OPERATORS.PLUS_CHAR.char -> minusPlusProcessing(
                operatorsStack, operandsStack,
                index, expr
            )
            OPERATORS.MULTIPLY_CHAR.char, OPERATORS.DIVIDE_CHAR.char -> multiplyDivideProcessing(
                operatorsStack,
                operandsStack,
                index,
                expr
            )
            OPERATORS.DEGREE_CHAR.char -> degreeProcessing(operatorsStack, operandsStack, index, expr)
            in "sctglpe".toCharArray() -> index = functionConstantProcessing(operatorsStack, operandsStack, index, expr)
            OPERATORS.CLOSE_BRACKET_CHAR.char -> closeBracketProcessing(operatorsStack, operandsStack, index)
        }
        index++
    }
    if (operandsStack.empty()) throw error("never should happen, no answer, index: $index")
    calculated = operandsStack.pop()
    if (!operandsStack.empty()) throw IllegalArgumentException("not enough operators, index: $index")
    if (!operatorsStack.empty()) throw IllegalArgumentException("not enough operands, index: $index")
    return calculated
}