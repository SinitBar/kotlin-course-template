package laba2

import calculateFromString

fun main() {
    val expression = " + lg(0.1) + (2^2 - sin((-pi))) + cos(0) + ln(e) + tg(0)"
    println(calculateFromString(expression))
}