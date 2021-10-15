import java.util.Stack
import kotlin.math.*

fun main() {
    val expression =" + lg(0.1) + (2^2 - sin((-pi))) + cos(0) + ln(e) "
    println(calculateFromString(expression))
}

fun simplifyExpression( // упрощает выражение, убирая лишние пробелы и обрабатывая унарные операторы
    expression: String,
): String {
    var result = expression.filter { !it.isWhitespace() } // убираются все пробелы
    result = result.replace("(-", "(0-") // обработка унарных минусов
    result = result.replace("(+", "(") // обработка унарных плюсов
    result = if (result[0] == '-') result.padStart(1 + result.length, '0')
    else {
        if (result[0] == '+') result.drop(1) else result
    } // обработка минуса или плюса в начале строки
    return result
}

fun calculateFromString(
    expression: String,
): Double {
    val expr: String = buildString {
        append('(', simplifyExpression(expression), ')') // упрощенное исходное выражение
    }
    val calculated: Double // результат вычисления выражения
    val operandsStack = Stack<Double>() // числа и константы
    val operatorsStack = Stack<String>() // операции и функции
    var index = 0
    while (index < expr.length) { // проход по чарам строки
        when (expr[index]) {
            '(' -> operatorsStack.push("(") // открывающаяся скобка всегда просто записывается в стек операторов

            in '0'..'9' -> { // если это число, то оно начинается на одну из цифр 0..9
                val startIndex = index

                while (index + 1 < expr.length) {
                    if (expr[index + 1] == '.') { // . может быть в числе
                        index++
                        continue
                    }

                    if (expr[index + 1] in '0'..'9') index++ else break // находится непрерывная последвательность цифр
                }
                val number: Double? = expr.substring(startIndex, index + 1).toDoubleOrNull() // если точек несколько,
                // то получится null
                operandsStack.push(number ?: throw error("unable to parse a number"))
            }

            '-', '+' -> {
                // надо выполнить все операции пока это не (
                while (!operatorsStack.empty() && operatorsStack.peek() != "(") {
                    if (operandsStack.size < 2) throw error("there is not enough operands")
                    val second = operandsStack.pop()
                    operandsStack.push(
                        when (operatorsStack.pop()){
                            "-" -> operandsStack.pop() - second
                            "+" -> operandsStack.pop() + second
                            "*" -> operandsStack.pop() * second
                            "/" -> operandsStack.pop() / second
                            "^" -> second.pow(operandsStack.pop())
                            else -> throw error("never should happen, the program is bad if it is")
                        }
                    )
                }

                if (operatorsStack.empty() || operatorsStack.peek() == "(") {
                    operatorsStack.push(expr[index].toString())
                } else throw error("not enough brackets") // ситуация вроде sin5, а не sin(5)
            }

            '*', '/' -> {
                // надо выполнить все операции пока это не ( или + или -
                while (!operatorsStack.empty() && operatorsStack.peek() != "(" &&
                    operatorsStack.peek() != "-" && operatorsStack.peek() != "+"
                ) {
                    if (operandsStack.size < 2) throw error("there is not enough operands")
                    val second = operandsStack.pop()
                    operandsStack.push(
                        when (operatorsStack.pop()){
                            "*" -> operandsStack.pop() * second
                            "/" -> operandsStack.pop() / second
                            "^" -> second.pow(operandsStack.pop())
                            else -> throw error("never should happen, the program is bad if it is")
                        }
                    )
                }

                if (operatorsStack.empty() || operatorsStack.peek() == "(" ||
                    operatorsStack.peek() == "-" || operatorsStack.peek() == "+"
                ) {
                    operatorsStack.push(expr[index].toString())
                } else throw error("not enough brackets") // что-то другое быть не так не должно
            }

            '^' -> {
                while (!operatorsStack.empty() && (operatorsStack.peek() == "^")) {
                    if (operandsStack.size < 2) throw error("there is not enough operands")
                    val degree = operandsStack.pop()
                    operandsStack.push(operandsStack.pop().pow(degree))
                }
                if (operatorsStack.empty() || operatorsStack.peek() != "^") operatorsStack.push(expr[index].toString())
            }

            in "sctglpe".toCharArray() -> { // буквы, с которых могут начинаться слова функций или pi и e числа
                when (expr[index]) {

                    's' -> { // sin or error
                        val operator: String = if (index + 2 < expr.length) {
                                if (expr[index + 1] == 'i' && expr[index + 2] == 'n') "sin"
                                else throw error("unknown function or word")
                        } else throw error("unknown function or word")
                        index += 2
                        operatorsStack.push(operator)
                    }

                    'c' -> { // cos or ctg or error
                        val operator: String = if (index + 2 < expr.length) {
                                if (expr[index + 1] == 'o' && expr[index + 2] == 's') "cos"
                                else {
                                    if (expr[index + 1] == 't' && expr[index + 2] == 'g') "ctg"
                                    else throw error("unknown function or word")
                                }
                        } else throw error("unknown function or word")
                        index += 2
                        operatorsStack.push(operator)
                    }

                    't' -> { // tg or error
                        val operator: String = if (index + 1 < expr.length) {
                            if (expr[++index] == 'g') "tg"
                            else throw error("unknown function or word")
                        } else throw error("unknown function or word")
                        operatorsStack.push(operator)
                    }

                    'l' -> { // ln or lg or error
                        val operator: String = if (index + 1 < expr.length) {
                            if (expr[index + 1] == 'g') "lg"
                            else {
                                if (expr[index + 1] == 'n') "ln"
                                else throw error("unknown function or word")
                            }
                        } else throw error("unknown function or word")
                        index++
                        operatorsStack.push(operator)
                    }

                    'p' -> { // pi
                        if (index + 1 < expr.length) {
                            if (expr[++index] == 'i') operandsStack.push(PI)
                            else throw error("unknown function or word")
                        } else throw error("unknown function or word")
                    }

                    'e' -> operandsStack.push(E)
                }

            }

            ')' -> {
                if (operatorsStack.empty()) throw error("open bracket is missing")
                if (operandsStack.empty()) throw error("operand is missing")
                var foundOpenBracket = false
                while (!operatorsStack.empty()) {
                    val second = operandsStack.pop() // приходится, так как в стеке операнды лежат задом наперед
                    when (operatorsStack.pop()){
                        "-" -> operandsStack.push(operandsStack.pop() - second)
                        "+" -> operandsStack.push(operandsStack.pop() + second)
                        "*" -> operandsStack.push(operandsStack.pop() * second)
                        "/" -> operandsStack.push(operandsStack.pop() / second)
                        "^" -> operandsStack.push(second.pow(operandsStack.pop()))

                        "(" -> {
                            operandsStack.push(second) // в случае, если это скобка, со стека операндов ничего не требуется
                            // а с него было взято сразу для случаев бинарных операторов и функций
                            foundOpenBracket = true

                            if (operatorsStack.empty()) break
                            // если до этой скобки была функция, надо ее выполнить, ее аргумент - последний в стеке чисел
                            when (operatorsStack.peek()) {

                                "sin" -> {
                                    operandsStack.push(sin(operandsStack.pop()))
                                    operatorsStack.pop()
                                }

                                "cos" -> {
                                    operandsStack.push(cos(operandsStack.pop()))
                                    operatorsStack.pop()
                                }

                                "tg" -> {
                                    operandsStack.push(
                                        tan(
                                            if (tan(second).isNaN()) {
                                                throw error("wrong tg argument")
                                            } else
                                                operandsStack.pop()
                                        )
                                    )
                                    operatorsStack.pop()
                                }

                                "ctg" -> {
                                    operandsStack.push(
                                        1 / tan(
                                            if ((1 / tan(second)).isNaN()) {
                                                throw error("wrong ctg argument")
                                            } else
                                                operandsStack.pop()
                                        )
                                    )
                                    operatorsStack.pop()
                                }

                                "ln" -> {
                                    operandsStack.push(
                                        ln(
                                            if ((ln(second)).isNaN()) {
                                                throw error("wrong ln argument")
                                            } else
                                                operandsStack.pop()
                                        )
                                    )
                                    operatorsStack.pop()
                                }

                                "lg" -> {
                                    operandsStack.push(
                                        log10(
                                            if ((log10(second)).isNaN()) {
                                                throw error("wrong lg argument")
                                            } else
                                                operandsStack.pop()
                                        )
                                    )
                                    operatorsStack.pop()
                                }
                                else -> break // перед найденной ( есть другие операторы, не функции
                            }
                            break // если открывающаяся скобка была найдена, дальше искать не нужно
                            // к тому же, она могла быть последним имеющимся оператором
                        }
                        else -> throw error("not enough open brackets") // перед sin в стеке всегда будет (, иначе неверно
                    }

                }
                if (!foundOpenBracket) throw error("open bracket is missing")
            }
        }
        index++
    }

    if (operandsStack.empty()) throw error("never should happen, no answer")

    calculated = operandsStack.pop()

    if (!operandsStack.empty()) throw error("not enough operators")
    if (!operatorsStack.empty()) throw error("not enough operands")

    return calculated
}