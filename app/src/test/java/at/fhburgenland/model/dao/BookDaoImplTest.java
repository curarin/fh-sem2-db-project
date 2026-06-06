package at.fhburgenland.model.dao;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.BookAuthor;
import at.fhburgenland.model.BookGenre;
import at.fhburgenland.model.BookPublisher;
import at.fhburgenland.model.dao.implementations.BookDaoImpl;
import at.fhburgenland.model.dao.interfaces.BookDao;
import jakarta.persistence.*;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

public class BookDaoImplTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    private BookDao bookDao;

    @BeforeAll
    public static void setupEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("book-unit-test");
    }

    @BeforeEach
    public void setupEntityManager() {
        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();
        bookDao = new BookDaoImpl(entityManager);
        entityTransaction.begin();
    }

    /**
     * Standard book generation method
     *
     * @param isbn Passing a isbn causes th generate a random one based on that - else we generate a hard coded one
     * @return Book object
     */
    private Book createStandardBook(String isbn) {
        BookAuthor author = new BookAuthor();
        author.setBookAuthorName("Standard Author");

        BookGenre genre = new BookGenre();
        genre.setBookGenreName("Standard Genre");

        BookPublisher publisher = new BookPublisher();
        publisher.setBookPublisherName("Standard Publisher");

        Book book = new Book();
        if (isbn != null) {
            book.setIsbn(isbn);
        } else {
            int randomNumber = ThreadLocalRandom.current().nextInt();
            book.setIsbn(String.valueOf(randomNumber));
        }
        book.setBookTitle("Standard Book Title");
        book.setBookGenre(genre);
        book.setBookPublisher(publisher);
        book.setBookAuthors(Set.of(author));

        return book;
    }

    @AfterEach
    public void tearDownEntityManager() {
        if (entityTransaction.isActive()) {
            entityTransaction.rollback();
        }
        entityManager.close();
    }

    @AfterAll
    public static void tearDownEntityManagerFactory() {
        entityManagerFactory.close();
    }

    @Test
    public void createAndReadBook() {
        bookDao.create(this.createStandardBook("123456789"));
        Book result = bookDao.readByIsbn("123456789");
        assertNotNull(result);
        assertEquals("Standard Book Title", result.getBookTitle());
        assertEquals("Standard Genre", result.getBookGenre().getBookGenreName());
        assertEquals("Standard Author", result.getBookAuthors().stream().findFirst().get().getBookAuthorName());
        assertEquals("Standard Publisher", result.getBookPublisher().getBookPublisherName());
    }

    @Test
    public void createBookFromManyBookAuthors() {
        BookAuthor bookAuthor1 = new BookAuthor();
        bookAuthor1.setBookAuthorName("Standard Author 1");
        BookAuthor bookAuthor2 = new BookAuthor();
        bookAuthor2.setBookAuthorName("Standard Author 2");
        BookAuthor bookAuthor3 = new BookAuthor();
        bookAuthor3.setBookAuthorName("Standard Author 3");

        BookGenre bookGenre = new BookGenre();
        bookGenre.setBookGenreName("Standard Genre");

        BookPublisher bookPublisher = new BookPublisher();
        bookPublisher.setBookPublisherName("Standard Publisher");

        Book bookWithManyAuthors = new Book();
        bookWithManyAuthors.setIsbn("123456789");
        bookWithManyAuthors.setBookTitle("Standard Book Title");
        bookWithManyAuthors.setBookGenre(bookGenre);
        bookWithManyAuthors.setBookPublisher(bookPublisher);
        bookWithManyAuthors.setBookAuthors(Set.of(bookAuthor1, bookAuthor2, bookAuthor3));

        bookDao.create(bookWithManyAuthors);

        assertEquals(3, bookWithManyAuthors.getBookAuthors().size());
        assertFalse(bookWithManyAuthors.getBookAuthors().isEmpty());

    }

    @Test
    public void getBooksByTitle() {
        bookDao.create(this.createStandardBook("1"));
        List<Book> foundBooks = bookDao.readByTitle("Standard");
        int foundBooksSize = foundBooks.size();
        assertTrue(foundBooksSize > 0);
        assertEquals(1, foundBooks.size());
    }

    @Test
    public void getBooksByAuthor() {
        bookDao.create(this.createStandardBook("1"));
        bookDao.create(this.createStandardBook("2"));
        List<Book> foundBooks = bookDao.readByAuthor("Standard");
        int foundBooksSize = foundBooks.size();
        assertTrue(foundBooksSize > 0);
        assertEquals(2, foundBooks.size());
    }

    @Test
    public void getBooksByGenre() {
        bookDao.create(this.createStandardBook("1"));
        bookDao.create(this.createStandardBook("2"));
        bookDao.create(this.createStandardBook("3"));
        bookDao.create(this.createStandardBook("4"));
        List<Book> foundBooks = bookDao.readByGenre("Standard");
        int foundBooksSize = foundBooks.size();
        assertTrue(foundBooksSize > 0);
        assertEquals(4, foundBooks.size());
    }

    @Test
    public void getBooksByPublisher() {
        bookDao.create(this.createStandardBook("1"));
        bookDao.create(this.createStandardBook("2"));
        bookDao.create(this.createStandardBook("3"));
        List<Book> foundBooks = bookDao.readByPublisher("Standard");
        int foundBooksSize = foundBooks.size();
        assertTrue(foundBooksSize > 0);
        assertEquals(3, foundBooks.size());
    }

    @Test
    public void removeBookAfterCreatingIt() {
        bookDao.create(this.createStandardBook("123456789"));
        Book createdBook = bookDao.readByIsbn("123456789");
        assertNotNull(createdBook);
        assertEquals("123456789", createdBook.getIsbn());

        bookDao.delete(createdBook);
        assertNull(bookDao.readByIsbn("123456789"));
    }

    @Test
    public void updateBookWithNewTitle() {
        bookDao.create(this.createStandardBook("123456789"));
        Book createdBook = bookDao.readByIsbn("123456789");
        assertNotNull(createdBook);
        assertEquals("Standard Book Title", createdBook.getBookTitle());

        createdBook.setBookTitle("Updated Book Title");
        bookDao.update(createdBook);
        assertEquals("Updated Book Title", bookDao.readByIsbn("123456789").getBookTitle());
    }

    @Test
    public void creatingMultipleBooksWithSameIsbnThrowsException() {
        assertThrows(EntityExistsException.class, () -> {
            bookDao.create(this.createStandardBook("123456789"));
            bookDao.create(this.createStandardBook("123456789"));
        });
    }
}
