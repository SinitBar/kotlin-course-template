package laba5

import java.time.LocalDate

enum class Genre {
    FANTASY,
    DETECTIVE,
    NOVEL,
    LITMMORPG,
    HISTORIC,
    TEXTBOOK,
    BUSINESS
}

class Book(val title: String, val authors: List<Author>, val genres: List<Genre>, val publishYear: Int) {
    init {
        if (title.isBlank())
            throw IllegalArgumentException("book requires not empty title")
        if (authors.isEmpty())
            throw IllegalArgumentException("book requires not empty list of authors")
        if (genres.isEmpty())
            throw IllegalArgumentException("book requires not empty list of genres")
        if (publishYear < 1 || publishYear > LocalDate.now().year)
            throw IllegalArgumentException("wrong year of publishing")
    }
}
