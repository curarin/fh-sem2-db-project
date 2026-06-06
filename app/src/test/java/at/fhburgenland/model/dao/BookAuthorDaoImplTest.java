package at.fhburgenland.model.dao;

import at.fhburgenland.model.BookAuthor;
import at.fhburgenland.model.dao.implementations.BookAuthorDaoImpl;
import at.fhburgenland.model.dao.interfaces.BookAuthorDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class BookAuthorDaoImplTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    private BookAuthorDao bookAuthorDao;

    @BeforeAll
    public static void setupEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("book-unit-test");
    }

    @BeforeEach
    public void setupEntityManager() {
        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();
        bookAuthorDao = new BookAuthorDaoImpl(entityManager);
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
    public void createBookAuthor() {
        BookAuthor bookAuthor = new BookAuthor();
        bookAuthor.setBookAuthorName("Book Author Name");
        bookAuthorDao.create(bookAuthor);
        assertNotNull(bookAuthorDao.readById(bookAuthor.getBookAuthorId()));
        assertEquals("Book Author Name", bookAuthorDao.readById(bookAuthor.getBookAuthorId()).getBookAuthorName());
    }

    @Test
    public void createMultipleBookAuthorsAndFindByOverlappingName() {
        BookAuthor bookAuthor1 = new BookAuthor();
        bookAuthor1.setBookAuthorName("Book Author 1");
        bookAuthorDao.create(bookAuthor1);
        BookAuthor bookAuthor2 = new BookAuthor();
        bookAuthor2.setBookAuthorName("Book Author 2");
        bookAuthorDao.create(bookAuthor2);
        BookAuthor bookAuthor3 = new BookAuthor();
        bookAuthor3.setBookAuthorName("Book Author 3");
        bookAuthorDao.create(bookAuthor3);
        BookAuthor bookAuthor4 = new BookAuthor();
        bookAuthor4.setBookAuthorName("Book Author 4");
        bookAuthorDao.create(bookAuthor4);

        assertEquals(4, bookAuthorDao.readByName("Book Author").size());
    }

    @Test
    public void updateBookAuthor() {
        BookAuthor bookAuthor = new BookAuthor();
        bookAuthor.setBookAuthorName("Book Author 1");
        bookAuthorDao.create(bookAuthor);

        assertEquals("Book Author 1", bookAuthorDao.readById(bookAuthor.getBookAuthorId()).getBookAuthorName());
        bookAuthor.setBookAuthorName("Fully fresh book author update yay");
        bookAuthorDao.update(bookAuthor);

        assertEquals("Fully fresh book author update yay", bookAuthorDao.readById(bookAuthor.getBookAuthorId()).getBookAuthorName());
    }

    @Test
    public void deleteBookAuthor() {
        BookAuthor bookAuthor = new BookAuthor();
        bookAuthor.setBookAuthorName("Book Author 1");
        bookAuthorDao.create(bookAuthor);
        assertNotNull(bookAuthorDao.readById(bookAuthor.getBookAuthorId()));

        bookAuthorDao.delete(bookAuthor);
        assertNull(bookAuthorDao.readById(bookAuthor.getBookAuthorId()));
    }
}
