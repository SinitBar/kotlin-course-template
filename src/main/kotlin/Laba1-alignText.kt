fun main(/*args: Array<String>*/) {
    // Try adding program arguments at Run/Debug configuration
    // println("Program arguments: ${args.joinToString()}")
    print(alignText(/*"para para pam pa bam"*/
        """ Над косточкой сидит бульдог,
        |Привязанный к столбу.
        |Подходит таксик маленький,
        |С морщинками на лбу.
        |«Послушайте, бульдог, бульдог! -
        |Сказал незваный гость.-
        |Позвольте мне, бульдог, бульдог,
        |Докушать эту кость».
        |Рычит бульдог на таксика:
        |«Не дам вам ничего!»
        |Бежит бульдог за таксиком,
        |А таксик от него.
        |Бегут они вокруг столба.
        |Как лев, бульдог рычит.
        |И цепь стучит вокруг столба,
        |Вокруг столба стучит.
        |Теперь бульдогу косточку
        |Не взять уже никак...
        |А таксик, взявши косточку,
        |Сказал бульдогу так:
        |«Пора мне на свидание,
        |Уж восемь без пяти.
        |Как поздно! До свидания!
        |Сидите на цепи!»""",
        80, Alignment.JUSTIFY))
}

enum class Alignment {
    LEFT,
    RIGHT,
    CENTER,
    JUSTIFY,
}

fun alignText(
    text: String,
    lineWidth: Int = 120,
    alignment: Alignment = Alignment.LEFT,
): String {
    var aligned: String = text.trimIndent().trimMargin()
    aligned = aligned.replace(Regex("\n"), " ")
    val symbols: CharArray = charArrayOf('\"', '.', ',', ';', ':', '-', '(', ')', '!', '?', '»', '«')
    val maxLineWidth = if (aligned.indexOfAny(symbols) != -1 ) {
        if (aligned.indexOf("...") != -1) 4 else 2
    } else 1
    // если есть хотя бы один знак препинания, чтобы соблюсти правила, нужно иметь ширину строки хотя бы = 2
    // если есть многоточие, то по той же причине ширина должна быть 4 или больше
    if (lineWidth < maxLineWidth)
        return "The line width is too short for this text"

    var last: Int = lineWidth - 1 // последний индекс строки в исходной строке
    val strings: ArrayList<String> = ArrayList() // разделенный на строки текст

    if (last == 0) {
        aligned.forEach {
            strings.add(it.toString())
        }
    }

    while (last < aligned.length && last > 0) {
        // если текущий или следующий - пробел, то все супер, у нас есть строка
        // если это чар, то надо проверить, были ли в строке вообще пробелы
        // если нет, то переносим и имеем строку, если это чар. Если знак препинания, то переносим
        // еще один предыдущий символ вместе со знаком препинания
        // если да, то надо найти последний и на нем обрезать строку
        last = if (aligned[last] != ' ' && aligned[last + 1] != ' '
            && aligned.take(last + 1).lastIndexOf(' ') != -1
        ) {
            aligned.take(last + 1).lastIndexOf(' ')
        } else {
            if (aligned[last + 1] in symbols) {
                // многоточие - тоже знак препинания, и он должен быть неразрывен с собой и предыдущим символом
                if (aligned[last] == '.' && aligned[last + 1] == '.') {
                    if (aligned[last - 1] == '.') last - 3 else last - 2
                } else last - 1
            } else {
                if (aligned[last + 1] == ' ') last + 1 else last
            }
        }

        strings.add(aligned.take(last + 1).trim())
        aligned = aligned.drop(last + 1)
        last = lineWidth - 1
    }

    aligned = when (alignment) {

        Alignment.LEFT -> {
            if (aligned.isBlank()) buildString {
                for (i in 0 until strings.size)
                    append(strings[i], "\n")
            } else buildString {
                    for (i in 0 until strings.size)
                        append(strings[i], "\n")
                append(aligned)
            }
        }

        Alignment.RIGHT -> {
            // в начало строк добавляются необходимые пробелы
            if (aligned.isBlank()) buildString {
                for (i in 0 until strings.size)
                    append(" ".repeat(lineWidth - strings[i].length), strings[i], "\n")
            } else buildString {
                for (i in 0 until strings.size)
                    append(" ".repeat(lineWidth - strings[i].length), strings[i], "\n")
                append(" ".repeat(lineWidth - aligned.length), aligned)
            }
        }

        Alignment.CENTER -> {
            // если пробелов в строке по краям должно быть нечетное количество, спереди будет на 1 больше
            if (aligned.isBlank()) buildString {
                for (i in 0 until strings.size)
                    append(" ".repeat(if ((lineWidth - strings[i].length).rem(2) == 1) {
                        (lineWidth - strings[i].length) / 2 + 1
                    } else {
                        (lineWidth - strings[i].length) / 2
                    }), strings[i], " ".repeat((lineWidth - strings[i].length) / 2), "\n")
            } else buildString {
                    for (i in 0 until strings.size)
                        append(" ".repeat(if ((lineWidth - strings[i].length).rem(2) == 1) {
                            (lineWidth - strings[i].length) / 2 + 1
                        } else {
                            (lineWidth - strings[i].length) / 2
                        }), strings[i], " ".repeat((lineWidth - strings[i].length) / 2), "\n")
                    append(" ".repeat(if((lineWidth - aligned.length).rem(2) == 1) {
                        (lineWidth - aligned.length) / 2 + 1
                    } else {
                        (lineWidth - aligned.length) / 2
                    }), aligned, " ".repeat((lineWidth - aligned.length) / 2), "\n")
            }
        }

        Alignment.JUSTIFY -> {
            var index = 0

            while (index < strings.size) {
                index = if (strings[index].indexOf(' ') == -1) {
                    index++; continue
                } else index
                // если в строке нет пробелов, выравнивать нечего
                val addingSpaces: Int = (lineWidth - strings[index].length) / (strings[index].split(" ").size - 1)
                // количество пробелов, добавляемое к каждому пробелу
                var extraSpaces: Int = (lineWidth - strings[index].length).rem(strings[index].split(" ").size - 1)
                // еще несколько пробелов, которые равномерно добавятся
                strings[index] = strings[index].replace(Regex(" ")) {
                    if (it.value == " ") {
                        if (extraSpaces > 0) {
                        extraSpaces--
                        " ".repeat(addingSpaces + 2)
                        } else " ".repeat(addingSpaces + 1)
                    } else it.value
                }
                index++
            }

            if (aligned.isBlank()) buildString {
                for (i in 0 until strings.size) append(strings[i], "\n")
            } else buildString {
                for (i in 0 until strings.size)
                    append(strings[i], "\n")
                append(aligned)
            }
        }

    }
    return aligned
}