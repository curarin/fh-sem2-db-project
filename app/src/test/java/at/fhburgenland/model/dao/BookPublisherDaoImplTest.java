package at.fhburgenland.model.dao;

import at.fhburgenland.model.BookPublisher;
import at.fhburgenland.model.dao.implementations.BookPublisherDaoImpl;
import at.fhburgenland.model.dao.interfaces.BookPublisherDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class BookPublisherDaoImplTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    private BookPublisherDao bookPublisherDao;

    @BeforeAll
    public static void setupEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("book-unit-test");
    }

    @BeforeEach
    public void setupEntityManager() {
        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();
        bookPublisherDao = new BookPublisherDaoImpl(entityManager);
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
    public void createNewBookPublisher() {
        BookPublisher bookPublisher = new BookPublisher();
        bookPublisher.setBookPublisherName("Test Book Publisher");
        bookPublisherDao.create(bookPublisher);
        assertEquals("Test Book Publisher", bookPublisher.getBookPublisherName());
        assertNotNull(bookPublisherDao.readById(bookPublisher.getBookPublisherId()));
    }

    @Test
    public void updateBookPublisher() {
        BookPublisher bookPublisher = new BookPublisher();
        bookPublisher.setBookPublisherName("Test Book Publisher");
        bookPublisherDao.create(bookPublisher);
        assertEquals("Test Book Publisher", bookPublisherDao.readById(bookPublisher.getBookPublisherId()).getBookPublisherName());
        bookPublisher.setBookPublisherName("Updated Book Publisher");
        bookPublisherDao.update(bookPublisher);
        assertEquals("Updated Book Publisher", bookPublisherDao.readById(bookPublisher.getBookPublisherId()).getBookPublisherName());
    }

    @Test
    public void deleteBookPublisher() {
        BookPublisher bookPublisher = new BookPublisher();
        bookPublisher.setBookPublisherName("Test Book Publisher");
        bookPublisherDao.create(bookPublisher);
        assertNotNull(bookPublisherDao.readById(bookPublisher.getBookPublisherId()));
        bookPublisherDao.delete(bookPublisher);
        assertNull(bookPublisherDao.readById(bookPublisher.getBookPublisherId()));
    }

    @Test
    public void newPublishersGetRunningIds() {
        BookPublisher bookPublisher1 = new BookPublisher();
        bookPublisher1.setBookPublisherName("Test Book Publisher 1");
        bookPublisherDao.create(bookPublisher1);
        assertNotNull(bookPublisherDao.readById(bookPublisher1.getBookPublisherId()));
        assertEquals("Test Book Publisher 1", bookPublisherDao.readById(bookPublisher1.getBookPublisherId()).getBookPublisherName());

        BookPublisher bookPublisher2 = new BookPublisher();
        bookPublisher2.setBookPublisherName("Test Book Publisher 2");
        bookPublisherDao.create(bookPublisher2);
        assertNotNull(bookPublisherDao.readById(bookPublisher2.getBookPublisherId()));
        assertEquals("Test Book Publisher 2", bookPublisherDao.readById(bookPublisher2.getBookPublisherId()).getBookPublisherName());

        BookPublisher bookPublisher3 = new BookPublisher();
        bookPublisher3.setBookPublisherName("Test Book Publisher 3");
        bookPublisherDao.create(bookPublisher3);
        assertNotNull(bookPublisherDao.readById(bookPublisher3.getBookPublisherId()));
        assertEquals("Test Book Publisher 3", bookPublisherDao.readById(bookPublisher3.getBookPublisherId()).getBookPublisherName());
    }

    @Test
    public void findAllPublishersByOverlappingName() {
        BookPublisher bookPublisher1 = new BookPublisher();
        BookPublisher bookPublisher2 = new BookPublisher();
        BookPublisher bookPublisher3 = new BookPublisher();

        bookPublisher1.setBookPublisherName("Test Book Publisher 1");
        bookPublisher2.setBookPublisherName("Test Book Publisher 2");
        bookPublisher3.setBookPublisherName("Test Book Publisher 3");

        bookPublisherDao.create(bookPublisher1);
        bookPublisherDao.create(bookPublisher2);
        bookPublisherDao.create(bookPublisher3);

        assertEquals(3, bookPublisherDao.readByName("Test Book Publisher").size());
    }
}
