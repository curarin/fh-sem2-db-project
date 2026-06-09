package at.fhburgenland.model.dao;

import at.fhburgenland.model.*;
import at.fhburgenland.model.dao.implementations.BookDaoImpl;
import at.fhburgenland.model.dao.implementations.BookStockLogDaoImpl;
import at.fhburgenland.model.dao.interfaces.BookDao;
import at.fhburgenland.model.dao.interfaces.BookStockLogDao;
import jakarta.persistence.*;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
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

    @AfterAll
    public static void tearDownEntityManagerFactory() {
        entityManagerFactory.close();
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
        int randomNumber = ThreadLocalRandom.current().nextInt();
        String authorName = "Standard Author".concat(String.valueOf(randomNumber));
        BookAuthor author = new BookAuthor();
        author.setBookAuthorName(authorName);

        BookGenre genre = new BookGenre();
        String genreName = "Standard Genre".concat(String.valueOf(randomNumber));
        genre.setBookGenreName(genreName);

        BookPublisher publisher = new BookPublisher();
        String publisherName = "Standard Publisher".concat(String.valueOf(randomNumber));
        publisher.setBookPublisherName(publisherName);

        Book book = new Book();
        if (isbn != null) {
            book.setIsbn(isbn);
        } else {
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

    @Test
    public void createAndReadBook() {
        bookDao.create(this.createStandardBook("123456789"));
        Book result = bookDao.readByIsbn("123456789");
        assertNotNull(result);
        assertNotNull(result.getBookTitle());
        assertNotNull(result.getBookGenre().getBookGenreName());
        assertEquals(1, result.getBookAuthors().size());
        assertNotNull(result.getBookPublisher().getBookPublisherName());
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
        Book currentBook = this.createStandardBook("11");
        String uuid = UUID.randomUUID().toString();
        currentBook.setBookTitle("Standard Book Title For This Unit Test" + uuid);
        bookDao.create(currentBook);
        List<Book> foundBooks = bookDao.readByTitle("Standard" + UUID.randomUUID());
        List<Book> exactBooks = bookDao.readByTitle("Standard Book Title For This Unit Test" + uuid);
        assertEquals(0, foundBooks.size());
        assertEquals(1, exactBooks.size());
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
    public void updateBookWithNewAuthors() {
        bookDao.create(this.createStandardBook("123456789"));
        Book createdBook = bookDao.readByIsbn("123456789");
        assertNotNull(createdBook);
        assertEquals(1, createdBook.getBookAuthors().size());

        BookAuthor bookAuthor1 = new BookAuthor();
        BookAuthor bookAuthor2 = new BookAuthor();
        BookAuthor bookAuthor3 = new BookAuthor();
        bookAuthor1.setBookAuthorName("Standard Author 1");
        bookAuthor2.setBookAuthorName("Standard Author 2");
        bookAuthor3.setBookAuthorName("Standard Author 3");
        createdBook.setBookAuthors(Set.of(bookAuthor1, bookAuthor2, bookAuthor3));
        bookDao.update(createdBook);
        assertEquals(3, bookDao.readByIsbn("123456789").getBookAuthors().size());
    }

    @Test
    public void creatingMultipleBooksWithSameIsbnThrowsException() {
        assertThrows(EntityExistsException.class, () -> {
            bookDao.create(this.createStandardBook("123456789"));
            bookDao.create(this.createStandardBook("123456789"));
        });
    }
}
