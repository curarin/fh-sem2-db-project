package at.fhburgenland.model.dao;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.BookAuthor;
import at.fhburgenland.model.BookGenre;
import at.fhburgenland.model.BookPublisher;
import at.fhburgenland.model.dao.implementations.BookDaoImpl;
import at.fhburgenland.model.dao.interfaces.BookDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BookDaoImplTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    private static Book standardBook;
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

    @BeforeAll
    public static void setupStandardBook() {
        Book book = new Book();
        BookAuthor author = new BookAuthor();
        author.setBookAuthorName("Test Author");

        BookGenre genre = new BookGenre();
        genre.setBookGenreName("Test Genre");

        BookPublisher publisher = new BookPublisher();
        publisher.setBookPublisherName("Test Publisher");

        book.setIsbn("123456789");
        book.setBookTitle("Test Book Title");
        book.setBookGenre(genre);
        book.setBookPublisher(publisher);
        book.setBookAuthors(Set.of(author));
        standardBook = book;
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
        bookDao.create(standardBook);
        Book result = bookDao.readByIsbn("123456789");
        assertNotNull(result);
        assertEquals("Test Book Title", result.getBookTitle());
    }

    @Test
    public void getBooksByTitle() {
        List<Book> foundBooks = bookDao.readByTitle("Test");
        assertNotNull(foundBooks);
    }

    @Test
    public void getBooksByAuthor() {
        List<Book> foundBooks = bookDao.readByAuthor("Test");
        assertNotNull(foundBooks);
    }

    @Test
    public void getBooksByGenre() {
        List<Book> foundBooks = bookDao.readByGenre("Test");
        assertNotNull(foundBooks);
    }

    @Test
    public void getBooksByPublisher() {
        List<Book> foundBooks = bookDao.readByPublisher("Test");
        assertNotNull(foundBooks);
    }
}
