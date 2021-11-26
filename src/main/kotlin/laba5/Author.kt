package laba5

class Author(val firstName: String, val lastName: String) {
    init {
        if (firstName.isBlank() || lastName.isBlank())
            throw IllegalArgumentException("author's name and last name shouldn't be empty")
    }
}