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

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        Book book = new Book();
        BookAuthor author = new BookAuthor();
        author.setBookAuthorName("Test");

        BookGenre genre = new BookGenre();
        genre.setBookGenreName("Test");

        BookPublisher publisher = new BookPublisher();
        publisher.setBookPublisherName("Test");

        book.setIsbn("123456789");
        book.setBookTitle("Test Book Title");
        book.setBookGenre(genre);
        book.setBookPublisher(publisher);
        book.setBookAuthors(Set.of(author));

        bookDao.create(book);

        Book result = bookDao.readByIsbn("123456789");

        assertNotNull(result);
        assertEquals("Test Book Title", result.getBookTitle());
    }
}
