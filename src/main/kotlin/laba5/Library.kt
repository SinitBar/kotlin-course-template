package laba5

import org.apache.log4j.LogManager
import org.apache.log4j.Logger

const val maxAmountOfBooksForOnePerson = 3
var logger: Logger = LogManager.getLogger("kotlin-course")

sealed class Status {
    object Available : Status()
    data class UsedBy(val user: User) : Status()
    object ComingSoon : Status()
    object Restoration : Status()
}

interface LibraryService {
    fun findBooks(
        substring: String = "",
        author: Author? = null,
        year: Int? = null,
        genre: Genre? = null
    ): List<Book>

    fun getAllBooks(): List<Book>
    fun getAllAvailableBooks(): List<Book>

    fun getBookStatus(book: Book): Status
    fun getAllBookStatuses(): Map<Book, Status>

    fun setBookStatus(book: Book, status: Status)

    fun addBook(book: Book, status: Status = Status.Available)

    fun registerUser(firstName: String, lastName: String)
    fun unregisterUser(user: User)

    fun takeBook(user: User, book: Book)
    fun returnBook(book: Book)
    fun toRestorateBook(book: Book)
    fun noveltyBook(book: Book)

    fun getUsers(): List<User>
    fun getUsersOnName(firstName: String, lastName: String): List<User>
}

class Library(
    initBooksStatused: Map<Book, Status> = emptyMap(),
    initUsersList: MutableList<User> = emptyList<User>().toMutableList(),
) : LibraryService {
    private val booksStatused: MutableMap<Book, Status> = mutableMapOf()
    private val usersList: MutableList<User> = emptyList<User>().toMutableList()

    init {
        for (pair in initBooksStatused)
            booksStatused.plus(pair.toPair())
        for (user in initUsersList)
            usersList.add(user)
    }

    override fun getUsers(): List<User> {
        return usersList.toList()
    }

    override fun getUsersOnName(firstName: String, lastName: String): List<User> {
        val users: List<User> = usersList.filter { user ->
            (user.firstName == firstName && user.lastName == lastName)
        }
        return (users.ifEmpty { throw NoSuchElementException("user doesn't exist") })
    }

    override fun findBooks(substring: String, author: Author?, year: Int?, genre: Genre?): List<Book> {
        return booksStatused.keys.filter { book ->
            (book.title.contains(substring, ignoreCase = true) &&
                    (author == null || !book.authors.contains(author)) &&
                    (year == null || year == book.publishYear) && (genre == null || book.genres.contains(genre))
                    )
        }.toList()
    }

    override fun getAllBooks(): List<Book> {
        return booksStatused.keys.toList()
    }

    override fun getAllAvailableBooks(): List<Book> {
        return booksStatused.filter { (_, status) ->
            status is Status.Available
        }.keys.toList()
    }

    override fun getBookStatus(book: Book): Status {
        if (booksStatused[book] == null) logger.warn("tried to get status of book (${book.title}) that doesn't exist")
        return (booksStatused[book] ?: throw  NoSuchElementException("book doesn't exist in the library"))
    }

    override fun getAllBookStatuses(): Map<Book, Status> {
        return booksStatused
    }

    override fun setBookStatus(book: Book, status: Status) {
        if (booksStatused[book] == null) logger.warn("tried to set status of book (${book.title}) that doesn't exist")
        booksStatused[book] ?: throw  NoSuchElementException("book doesn't exist in the library")
        booksStatused[book] = status
    }

    override fun addBook(
        book: Book,
        status: Status
    ) {
        booksStatused[book] = status
    }

    override fun registerUser(firstName: String, lastName: String) {
        usersList.add(User(firstName, lastName))
        logger.info("new user with first name = $firstName, last name = $lastName registered")
    }

    override fun unregisterUser(user: User) {
        usersList.remove(user)
        logger.info("user with first name = ${user.firstName}, last name = ${user.lastName} unregistered")
    }

    override fun takeBook(user: User, book: Book) {
        if (booksStatused.values.filter { status ->
                status is Status.UsedBy &&
                        status.user == user
            }.size >= maxAmountOfBooksForOnePerson
        ) {
            logger.warn(
                "user with first name = ${user.firstName}, last name = ${user.lastName} tried to take " +
                        "a book when took $maxAmountOfBooksForOnePerson books already"
            )
            throw IllegalArgumentException("user can have maximum $maxAmountOfBooksForOnePerson books at once")
        }
        booksStatused[book] = Status.UsedBy(user)
        logger.info(
            "user with first name = ${user.firstName}, last name = ${user.lastName} took a book " +
                    " with title = ${book.title}"
        )
    }

    override fun returnBook(book: Book) { // user returns book, so it's status changes to available
        logger.info(
            "user with first name = ${(booksStatused[book] as Status.UsedBy).user.firstName}, " +
                    "last name = ${(booksStatused[book] as Status.UsedBy).user.lastName} returned a book " +
                    " with title = ${book.title}"
        )
        booksStatused[book] = Status.Available
    }

    override fun toRestorateBook(book: Book) {
        booksStatused[book] = Status.Restoration
        logger.info("book with title = ${book.title} is sent to restoration")
    }

    override fun noveltyBook(book: Book) {
        booksStatused[book] = Status.ComingSoon
        logger.info("book with title = ${book.title} is coming soon")
    }
}