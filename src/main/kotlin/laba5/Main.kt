package laba5

fun main() {
    val lib = Library() // create library
    lib.registerUser("Sin", "Bar") // register first user
    // add books
    lib.addBook(Book("Matan-Satan", listOf(Author("Satan", "Demidovich")), listOf(Genre.TEXTBOOK), 2005))
    lib.addBook(Book("Fire faculty", listOf(Author("Alex", "Kosh")), listOf(Genre.FANTASY), 2020))
    lib.addBook(
        Book(
            "Head of the clan of the sleepless",
            listOf(Author("Dem", "Michailov")),
            listOf(Genre.LITMMORPG),
            2018
        )
    )
    lib.addBook(
        Book(
            "Ancient world history",
            listOf(Author("A. A.", "Vigasin"), Author("G. I.", "Gorer")),
            listOf(Genre.TEXTBOOK, Genre.HISTORIC),
            2005
        )
    )
    lib.addBook(
        Book(
            "Curse academy",
            listOf(Author("Elena", "Zvezdnaya")),
            listOf(Genre.FANTASY, Genre.NOVEL, Genre.DETECTIVE),
            2014
        )
    )
    lib.addBook(
        Book(
            "How to Lead: Wisdom from the World's Greatest CEOs, Founders, and Game Changers",
            listOf(Author("David M.", "Rubenstein")),
            listOf(Genre.BUSINESS),
            2021
        )
    )
    // change some statuses of the books:
    lib.noveltyBook(lib.findBooks("Lead")[0])
    lib.toRestorateBook(lib.findBooks("Matan")[0])
    // take some books
    lib.takeBook(User("Sin", "Bar"), lib.findBooks("Fire")[0])
    lib.takeBook(User("Sin", "Bar"), lib.findBooks("Curse")[0])
    // return a book
    lib.returnBook(lib.findBooks("Fire")[0])
    for (pair in lib.getAllBookStatuses()) println("book with title ${pair.key.title} has status ${pair.value}")
}