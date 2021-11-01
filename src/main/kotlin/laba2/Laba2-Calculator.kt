import java.util.Stack
import kotlin.math.*

const val openBracketChar = '('
const val closeBracketChar = ')'
const val minusChar = '-'
const val plusChar = '+'
const val multiplyChar = '*'
const val divideChar = '/'
const val degreeChar = '^'
val firstDigitInNumberRangeChar = '0'..'9'

fun minusPlusProcessing(
    operatorsStack: Stack<String>,
    operandsStack: Stack<Double>,
    index: Int,
    expr: String,
) { // should do all operations while there is no (
    while (!operatorsStack.empty() && operatorsStack.peek() != openBracketChar.toString()) {
        if (operandsStack.size < 2) throw error("there is not enough operands, index: $index")
        val second = operandsStack.pop()
        operandsStack.push(
            when (operatorsStack.pop()) {
                minusChar.toString() -> operandsStack.pop() - second
                plusChar.toString() -> operandsStack.pop() + second
                multiplyChar.toString() -> operandsStack.pop() * second
                divideChar.toString() -> operandsStack.pop() / second
                degreeChar.toString() -> second.pow(operandsStack.pop())
                else -> throw error("never should happen, the program is bad if it is, index: $index")
            }
        )
    }
    if (operatorsStack.empty() || operatorsStack.peek() == openBracketChar.toString()) {
        operatorsStack.push(expr[index].toString())
    } else throw error("not enough brackets, index: $index") // situation like sin5, not sin(5) like should
}

fun multiplyDivideProcessing(
    operatorsStack: Stack<String>,
    operandsStack: Stack<Double>,
    index: Int,
    expr: String,
) { // should do all operations while there is no ( or + or -
    while (!operatorsStack.empty() && operatorsStack.peek() != openBracketChar.toString() &&
        operatorsStack.peek() != minusChar.toString() && operatorsStack.peek() != plusChar.toString()
    ) {
        if (operandsStack.size < 2) throw error("there is not enough operands, index: $index")
        val second = operandsStack.pop()
        operandsStack.push(
            when (operatorsStack.pop()) {
                multiplyChar.toString() -> operandsStack.pop() * second
                divideChar.toString() -> operandsStack.pop() / second
                degreeChar.toString() -> second.pow(operandsStack.pop())
                else -> throw error("never should happen, the program is bad if it is, index: $index")
            }
        )
    }
    if (operatorsStack.empty() || operatorsStack.peek() == openBracketChar.toString() ||
        operatorsStack.peek() == minusChar.toString() || operatorsStack.peek() == plusChar.toString()
    ) {
        operatorsStack.push(expr[index].toString())
    } else throw error("not enough brackets, index: $index") // another error shouldn't be
}

fun degreeProcessing(
    operatorsStack: Stack<String>,
    operandsStack: Stack<Double>,
    index: Int,
    expr: String,
) {
    while (!operatorsStack.empty() && (operatorsStack.peek() == "^")) {
        if (operandsStack.size < 2) throw error("there is not enough operands, index: $index")
        val degree = operandsStack.pop()
        operandsStack.push(operandsStack.pop().pow(degree))
    }
    if (operatorsStack.empty() || operatorsStack.peek() != "^") operatorsStack.push(expr[index].toString())
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
        if (expr[indexCopy + 1] in firstDigitInNumberRangeChar) indexCopy++ else break
        // finds continuous sequence of numbers
    }
    val number: Double? =
        expr.substring(index, indexCopy + 1).toDoubleOrNull() // if there are several dots, will return null
    operandsStack.push(number ?: throw error("unable to parse a number, index: $indexCopy"))
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
    if (indexCopy + string1.length >= expr.length) throw error("unknown function or word, index: $indexCopy")
    var word = ""
    word = buildString {
        for (i in string1.indices) append(expr[indexCopy + i], word) // string1 and string2 have similar length
    }
    if (word == string1 || (string2.isNotEmpty() && word == string2)) operatorsStack.push(word)
    else throw error("unknown function or word, index: $indexCopy")
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
                else throw error("unknown function or word, index: $indexCopy")
            } else throw error("unknown function or word, index: $indexCopy")
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
                tan(if (tan(second).isNaN()) throw error("wrong tg argument, index: $index") else operandsStack.pop())
            )
        }

        "ctg" -> {
            operandsStack.push(
                1 / tan(
                    if ((1 / tan(second)).isNaN()) throw error("wrong ctg argument, index: $index")
                    else operandsStack.pop()
                )
            )
        }

        "ln" -> {
            operandsStack.push(
                ln(if ((ln(second)).isNaN()) throw error("wrong ln argument, index: $index") else operandsStack.pop())
            )
        }

        "lg" -> {
            operandsStack.push(
                log10(
                    if ((log10(second)).isNaN()) throw error("wrong lg argument, index: $index")
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
    if (operatorsStack.empty()) throw error("open bracket is missing, index: $index")
    if (operandsStack.empty()) throw error("operand is missing, index: $index")
    var foundOpenBracket = false
    while (!operatorsStack.empty()) {
        val second = operandsStack.pop() // have to do it because operands in stack are in reversed order
        when (operatorsStack.pop()) {
            minusChar.toString() -> operandsStack.push(operandsStack.pop() - second)
            plusChar.toString() -> operandsStack.push(operandsStack.pop() + second)
            multiplyChar.toString() -> operandsStack.push(operandsStack.pop() * second)
            divideChar.toString() -> operandsStack.push(operandsStack.pop() / second)
            degreeChar.toString() -> operandsStack.push(second.pow(operandsStack.pop()))

            openBracketChar.toString() -> {
                foundOpenBracket = true
                openBracketProcessing(operatorsStack, operandsStack, index, second)
                break
            }

            else -> throw error("not enough open brackets, index: $index") // before sin in stack always (, otherwise wrong
        }
    }
    if (!foundOpenBracket) throw error("open bracket is missing, index: $index")
}

fun simplifyExpression(
    // simplify expression be dropping extra spaces and processing unary minuses and pluses
    expression: String,
): String {
    var result = expression.filter { !it.isWhitespace() } // deleting all spaces
    result = result.replace(
        "$openBracketChar$minusChar",
        "${openBracketChar}0$minusChar"
    ) // processing unary minus
    result = result.replace("$openBracketChar$plusChar", openBracketChar.toString()) // processing unary pluses
    result = if (result[0] == minusChar) result.padStart(1 + result.length, '0')
    else {
        if (result[0] == plusChar) result.drop(1) else result
    } // processing minus or plus in the beginning
    println("expression to calculate: $result")
    return result
}

fun calculateFromString(
    expression: String,
): Double {
    val expr: String = buildString {
        append(openBracketChar, simplifyExpression(expression), closeBracketChar) // simplified data expression
    }
    val calculated: Double // the result of calculation
    val operandsStack = Stack<Double>() // numbers and constants (pi and e)
    val operatorsStack = Stack<String>() // operations and functions
    var index = 0
    while (index < expr.length) { // go through chars in string
        when (expr[index]) {
            openBracketChar -> operatorsStack.push(openBracketChar.toString()) // open bracket always go to operators stack
            in firstDigitInNumberRangeChar -> index = numberProcessing(operandsStack, index, expr)
            minusChar, plusChar -> minusPlusProcessing(operatorsStack, operandsStack, index, expr)
            multiplyChar, divideChar -> multiplyDivideProcessing(operatorsStack, operandsStack, index, expr)
            degreeChar -> degreeProcessing(operatorsStack, operandsStack, index, expr)
            in "sctglpe".toCharArray() -> index = functionConstantProcessing(operatorsStack, operandsStack, index, expr)
            closeBracketChar -> closeBracketProcessing(operatorsStack, operandsStack, index)
        }
        index++
    }
    if (operandsStack.empty()) throw error("never should happen, no answer, index: $index")
    calculated = operandsStack.pop()
    if (!operandsStack.empty()) throw error("not enough operators, index: $index")
    if (!operatorsStack.empty()) throw error("not enough operands, index: $index")
    return calculated
}