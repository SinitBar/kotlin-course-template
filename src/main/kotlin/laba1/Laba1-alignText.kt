package laba1

enum class Alignment {
    LEFT,
    RIGHT,
    CENTER,
    JUSTIFY,
}

const val spaceStr = " "
const val spaceChar = ' '
val symbols: Set<Char> = setOf('\"', '.', ',', ';', ':', '-', '(', ')', '!', '?', '»', '«')

fun parseTextToStrings(
    text: String,
    strings: ArrayList<String>,
    lineWidth: Int,
): String { // returns alignedCopy, changes strings
    var textCopy = text
    var last: Int = lineWidth - 1 // the last index in the parsed string of the data text
    if (last == 0) { // lineWidth = 1, can only write one char in the string
        textCopy.forEach {
            strings.add(it.toString())
        }
    }
    while (last < textCopy.length && last > 0) {
        // if current or next is the space, have a formatted string
        // if it is a char need to check if there were spaces at all
        // If it is not, parse the string and have a formatted string, but if it is a punctuation mark
        // than take it to the next string with previous letter
        // if there were spaces need to find the last and parse the string there
        last =
            if (last + 1 == textCopy.length)  last - 1 // if last is the last symbol of the text
            else {
                if (textCopy[last] != spaceChar && textCopy[if (last + 1 < lineWidth ) last + 1 else last] != spaceChar
                    && textCopy.take(last + 1).lastIndexOf(spaceChar) != -1
                ) {
                    textCopy.take(last + 1).lastIndexOf(spaceChar)
                } else {
                    if (textCopy[last + 1] in symbols && lineWidth > 3) { // ellipsis case
                        val dot = '.'
                        if (textCopy[last] == dot && textCopy[last + 1] == dot) { // only possible symbol for last when last+1 is a dot
                                if (textCopy[last - 1] == dot) last - 3 else last - 2
                            } else last - 1
                    }else {
                        if (textCopy[last + 1] == spaceChar) last + 1 else last
                    }
                }
            }
        strings.add(textCopy.take(last + 1).trim())
        textCopy = textCopy.drop(last + 1)
        last = lineWidth - 1
    }
    return textCopy
}

fun alignToTheLeft( // build one string from strings
    lastString: String,
    strings: ArrayList<String>,
): String {
    return buildString {
        for (i in 0 until strings.size)
            append(strings[i], System.lineSeparator())
        if (lastString.isNotBlank()) append(lastString)
    }
}

fun alignToTheRight( // add spaces to the beginning of data strings and return them like one string
    lastString: String,
    strings: ArrayList<String>,
    lineWidth: Int,
): String {
    return buildString {
        for (i in 0 until strings.size)
            append(spaceStr.repeat(lineWidth - strings[i].length), strings[i], System.lineSeparator())
        if (lastString.isNotBlank()) append(spaceStr.repeat(lineWidth - lastString.length), lastString)
    }
}

fun insertSpaces( // insert spaces around data string
    dataString: String,
    lineWidth: Int,
    extraSpaces: Int,
): String {
    return buildString {
        append(
            spaceStr.repeat(
                if ((lineWidth - extraSpaces).rem(2) == 1) {
                    (lineWidth - extraSpaces) / 2 + 1
                } else {
                    (lineWidth - extraSpaces) / 2
                }
            ), dataString, spaceStr.repeat((lineWidth - extraSpaces) / 2), System.lineSeparator()
        )
    }
}

fun alignToTheCenter( // Add spaces to the beginning and to the ending of data strings and return them like one string
    lastString: String,
    strings: ArrayList<String>,
    lineWidth: Int,
): String {
    // if amount of spaces is odd, in the beginning one more space than in the ending
    return buildString {
            for (i in 0 until strings.size) append(insertSpaces(strings[i], lineWidth, strings[i].length))
            if (lastString.isNotBlank()) append(insertSpaces(lastString, lineWidth, lastString.length))
    }
}

fun alignJustify( // Add spaces between words in data strings and return them like one string
    aligned: String,
    strings: ArrayList<String>,
    lineWidth: Int,
): String {
    var index = 0
    return buildString {
        while (index < strings.size) {
            index = if (strings[index].indexOf(spaceChar) == -1) {
                index++; continue // if there is no spaces in the string, there is nothing to justify
            } else index
            val addingSpaces: Int = (lineWidth - strings[index].length) / (strings[index].split(spaceStr).size - 1)
            // number of the spaces adding to every single space
            var extraSpaces: Int = (lineWidth - strings[index].length).rem(strings[index].split(spaceStr).size - 1)
            // the last spaces adding one to each of the first spaces
            strings[index] = strings[index].replace(Regex(spaceStr)) {
                if (it.value == spaceStr) {
                    if (extraSpaces > 0) {
                        extraSpaces--
                        spaceStr.repeat(addingSpaces + 2)
                    } else spaceStr.repeat(addingSpaces + 1)
                } else it.value
            }
            index++
        }
        buildString {
            for (i in 0 until strings.size) append(strings[i], System.lineSeparator())
            if (aligned.isNotBlank()) append(aligned)
        }
    }
}

fun alignText(
    text: String,
    lineWidth: Int = 120,
    alignment: Alignment = Alignment.LEFT,
): String {
    if (text.isEmpty()) throw error("the size of the data text is too small")
    if (lineWidth < 1) throw error("required width is too small")
    var aligned: String = text.trimMargin().trimIndent().replace("\n"/*System.lineSeparator()*/, spaceStr)
    val strings: ArrayList<String> = ArrayList() // text parsed into strings
    aligned = parseTextToStrings(aligned, strings, lineWidth)

    aligned = when (alignment) {
        Alignment.LEFT -> alignToTheLeft(aligned, strings)
        Alignment.RIGHT -> alignToTheRight(aligned, strings, lineWidth)
        Alignment.CENTER -> alignToTheCenter(aligned, strings, lineWidth)
        Alignment.JUSTIFY -> alignJustify(aligned, strings, lineWidth)
    }
    return aligned
}