package laba5Tests

import laba5.*
import org.junit.Assert
import org.junit.Test
import kotlin.jvm.Throws
import kotlin.test.assertFailsWith

/**
 * The main testing class
 */
class LibraryTesting {
    /**
     * The method tests the creation of object of the class Library
     * @see Library
     */
    @Test
    fun `Create library testing`() {
        val lib = Library()
        Assert.assertEquals(0, lib.getAllBooks().size)
    }

    /**
     * The method tests adding book to the library
     * @see Library.addBook
     */
    @Test
    fun `adding book testing`() {
        val lib = Library()
        lib.addBook(Book("Matan-Satan", listOf(Author("Satan", "Demidovich")), listOf(Genre.TEXTBOOK), 2005))
        Assert.assertEquals(1, lib.getAllBooks().size)
    }

    /**
     * The method tests get all books in the library
     * @see Library.addBook
     */
    @Test
    fun `get list of books testing`() {
        val lib = Library()
        Assert.assertEquals(0, lib.getAllBooks().size)
        lib.addBook(Book("Matan-Satan", listOf(Author("Satan", "Demidovich")), listOf(Genre.TEXTBOOK), 2005))
        Assert.assertEquals(1, lib.getAllBooks().size)
        Assert.assertEquals("Matan-Satan", lib.getAllBooks()[0].title)
        Assert.assertEquals(1, lib.getAllBooks()[0].authors.size)
        Assert.assertEquals("Satan", lib.getAllBooks()[0].authors[0].firstName)
        Assert.assertEquals("Demidovich", lib.getAllBooks()[0].authors[0].lastName)
        Assert.assertEquals(1, lib.getAllBooks()[0].genres.size)
        Assert.assertEquals(Genre.TEXTBOOK, lib.getAllBooks()[0].genres[0])
        Assert.assertEquals(2005, lib.getAllBooks()[0].publishYear)
    }

    /**
     * The method tests registration of user to the library
     * @see Library.registerUser
     */
    @Test
    fun `user registration testing`() {
        val lib = Library()
        lib.registerUser("Sin", "Bar")
        Assert.assertEquals(1, lib.getUsers().size)
    }

    /**
     * The method tests unregistration of user to the library
     * @see Library.unregisterUser
     */
    @Test
    fun `user unregistration testing`() {
        val lib = Library()
        lib.registerUser("Sin", "Bar")
        Assert.assertEquals(1, lib.getUsers().size)
        lib.unregisterUser(lib.getUsersOnName("Sin", "Bar")[0])
        Assert.assertEquals(0, lib.getUsers().size)
    }

    /**
     * The method tests getting the list of available books in the library
     * @see Library.getAllAvailableBooks
     */
    @Test
    fun `get list of available books testing`() {
        val lib = Library()
        lib.registerUser("Sin", "Bar")
        lib.addBook(Book("Matan-Satan", listOf(Author("Satan", "Demidovich")), listOf(Genre.TEXTBOOK), 2005))
        Assert.assertEquals(1, lib.getAllAvailableBooks().size)
        lib.takeBook(lib.getUsersOnName("Sin", "Bar")[0], lib.findBooks("Matan")[0])
        Assert.assertEquals(0, lib.getAllAvailableBooks().size)
    }

    /**
     * The method tests getting book's status in the library
     * @see Library.getBookStatus
     */
    @Test
    fun `get book's status testing`() {
        val lib = Library()
        lib.registerUser("Sin", "Bar")
        lib.addBook(Book("Matan-Satan", listOf(Author("Satan", "Demidovich")), listOf(Genre.TEXTBOOK), 2005))
        Assert.assertEquals(Status.Available, lib.getBookStatus(lib.findBooks("Satan")[0]))
        lib.takeBook(lib.getUsersOnName("Sin", "Bar")[0], lib.findBooks("Matan")[0])
        Assert.assertEquals(
            Status.UsedBy(lib.getUsersOnName("Sin", "Bar")[0]),
            lib.getBookStatus(lib.findBooks("Satan")[0])
        )
    }

    /**
     * The method tests return book in the library
     * @see Library.returnBook
     */
    @Test
    fun `return book testing`() {
        val lib = Library()
        lib.registerUser("Sin", "Bar")
        lib.addBook(Book("Matan-Satan", listOf(Author("Satan", "Demidovich")), listOf(Genre.TEXTBOOK), 2005))
        lib.takeBook(User("Sin", "Bar"), lib.findBooks("Matan")[0])
        Assert.assertEquals(0, lib.getAllAvailableBooks().size)
        lib.returnBook(lib.getAllBooks()[0])
        Assert.assertEquals(1, lib.getAllAvailableBooks().size)
    }

    /**
     * The method tests take book by user in the library
     * @see Library.takeBook
     */
    @Throws(IllegalArgumentException::class)
    @Test
    fun `take book testing`() {
        val lib = Library()
        lib.registerUser("Sin", "Bar")
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
        lib.takeBook(lib.getUsersOnName("Sin", "Bar")[0], lib.findBooks("world")[0])
        lib.takeBook(lib.getUsersOnName("Sin", "Bar")[0], lib.findBooks("Curse")[0])
        lib.takeBook(lib.getUsersOnName("Sin", "Bar")[0], lib.findBooks("Fire")[0])
        val exception = assertFailsWith<IllegalArgumentException> {
            lib.takeBook(
                lib.getUsersOnName("Sin", "Bar")[0],
                lib.findBooks("Head")[0]
            )
        }
        println("caught exception: $exception")
        Assert.assertEquals("user can have maximum $maxAmountOfBooksForOnePerson books at once", exception.message)
    }

    /**
     * The method tests getting the list of users in the library
     * @see Library.getUsers
     */
    @Test
    fun `get list of users testing`() {
        val lib = Library()
        Assert.assertEquals(0, lib.getUsers().size)
        lib.registerUser("Sin", "Bar")
        Assert.assertEquals(1, lib.getUsers().size)
    }

    /**
     * The method tests setting book status in the library
     * @see Library.setBookStatus
     */
    @Test
    fun `set book status testing`() {
        val lib = Library()
        lib.addBook(Book("Matan-Satan", listOf(Author("Satan", "Demidovich")), listOf(Genre.TEXTBOOK), 2005))
        lib.setBookStatus(lib.findBooks("Matan")[0], Status.Restoration)
        Assert.assertEquals(Status.Restoration, lib.getBookStatus(lib.findBooks("Matan")[0]))
    }

    /**
     * The method tests setting book status error in the library
     * @see Library.setBookStatus
     */
    @Throws(NoSuchElementException::class)
    @Test
    fun `set book status error testing`() {
        val lib = Library()
        lib.addBook(Book("Matan-Satan", listOf(Author("Satan", "Demidovich")), listOf(Genre.TEXTBOOK), 2005))
        val exception = assertFailsWith<NoSuchElementException> {
            lib.setBookStatus(
                Book(
                    "Fire faculty",
                    listOf(Author("Alex", "Kosh")),
                    listOf(Genre.FANTASY),
                    2020
                ), Status.Restoration
            )
        }
        println("caught exception: $exception")
        Assert.assertEquals("book doesn't exist in the library", exception.message)
    }

    /**
     * The method tests get book status error in the library
     * @see Library.getBookStatus
     */
    @Throws(NoSuchElementException::class)
    @Test
    fun `get book status error testing`() {
        val lib = Library()
        lib.addBook(Book("Matan-Satan", listOf(Author("Satan", "Demidovich")), listOf(Genre.TEXTBOOK), 2005))
        val exception = assertFailsWith<NoSuchElementException> {
            lib.getBookStatus(
                Book(
                    "Fire faculty",
                    listOf(Author("Alex", "Kosh")),
                    listOf(Genre.FANTASY),
                    2020
                )
            )
        }
        println("caught exception: $exception")
        Assert.assertEquals("book doesn't exist in the library", exception.message)
    }

    /**
     * The method tests get user on name error in the library
     * @see Library.getUsersOnName
     */
    @Throws(NoSuchElementException::class)
    @Test
    fun `get user on name error testing`() {
        val lib = Library()
        val exception = assertFailsWith<NoSuchElementException> { lib.getUsersOnName("Sin", "Bar") }
        println("caught exception: $exception")
        Assert.assertEquals("user doesn't exist", exception.message)
    }

    /**
     * The method tests restoration book in the library
     * @see Library.toRestorateBook
     */
    @Test
    fun `restorate book testing`() {
        val lib = Library()
        lib.addBook(Book("Matan-Satan", listOf(Author("Satan", "Demidovich")), listOf(Genre.TEXTBOOK), 2005))
        lib.toRestorateBook(lib.getAllBooks()[0])
        Assert.assertEquals(Status.Restoration, lib.getBookStatus(lib.getAllBooks()[0]))
    }

    /**
     * The method tests novelty book in the library
     * @see Library.noveltyBook
     */
    @Test
    fun `novelty book testing`() {
        val lib = Library()
        lib.addBook(Book("Matan-Satan", listOf(Author("Satan", "Demidovich")), listOf(Genre.TEXTBOOK), 2005))
        lib.noveltyBook(lib.getAllBooks()[0])
        Assert.assertEquals(Status.ComingSoon, lib.getBookStatus(lib.getAllBooks()[0]))
    }

    /**
     * The method tests book searching to the library
     * @see Library.findBooks
     */
    @Test
    fun `book searching testing`() {
        val lib = Library()
        lib.addBook(Book("Matan-Satan", listOf(Author("Satan", "Demidovich")), listOf(Genre.TEXTBOOK), 2005))
        Assert.assertEquals(1, lib.findBooks("Matan").size)
        Assert.assertEquals(1, lib.findBooks("Satan").size)
        Assert.assertEquals(1, lib.findBooks(author = Author("Satan", "Demidovich")).size)
        Assert.assertEquals(1, lib.findBooks(genre = Genre.TEXTBOOK).size)
        Assert.assertEquals(1, lib.findBooks(year = 2005).size)
    }

    /**
     * The method tests getting the list of book statuses in the library
     * @see Library.getAllBookStatuses
     */
    @Test
    fun `get list of book statuses testing`() {
        val lib = Library()
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
                "Сurse academy",
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
        for (book in lib.getAllBookStatuses())
            Assert.assertEquals(lib.getBookStatus(book.key), Status.Available)
    }
}