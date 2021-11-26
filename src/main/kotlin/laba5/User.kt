package laba5

class User(val firstName: String, val lastName: String) {
    init {
        if (firstName.isBlank() || lastName.isBlank())
            throw IllegalArgumentException("user's name and last name shouldn't be empty")
    }
}