package at.fhburgenland.model.dao;

import at.fhburgenland.model.BookGenre;
import at.fhburgenland.model.dao.implementations.BookGenreDaoImpl;
import at.fhburgenland.model.dao.interfaces.BookGenreDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BookGenreDaoImplTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    private BookGenreDao bookGenreDao;

    @BeforeAll
    public static void setupEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("book-unit-test");
    }

    @BeforeEach
    public void setupEntityManager() {
        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();
        bookGenreDao = new BookGenreDaoImpl(entityManager);
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
    public void createNewBookGenre() {
        BookGenre bookGenre = new BookGenre();
        bookGenre.setBookGenreName("Custom Book Genre");
        assertEquals(0, bookGenreDao.readByName("Custom Book Genre").size());
        bookGenreDao.create(bookGenre);
        assertEquals(1, bookGenreDao.readByName("Custom Book Genre").size());
    }

    @Test
    public void updateBookGenre() {
        BookGenre bookGenre = new BookGenre();
        bookGenre.setBookGenreName("Custom Book Genre");
        bookGenreDao.create(bookGenre);
        assertEquals(1, bookGenreDao.readByName("Custom Book Genre").size());
        BookGenre existingGenre = bookGenreDao.readByName("Custom Book Genre").get(0);
        existingGenre.setBookGenreName("Updated Book Genre");
        bookGenreDao.update(existingGenre);
        assertEquals(1, bookGenreDao.readByName("Updated Book Genre").size());
    }

    @Test
    public void deleteBookGenre() {
        BookGenre bookGenre = new BookGenre();
        bookGenre.setBookGenreName("Custom Book Genre");
        bookGenreDao.create(bookGenre);
        assertEquals(1, bookGenreDao.readByName("Custom Book Genre").size());
        bookGenreDao.delete(bookGenre);
        assertEquals(0, bookGenreDao.readByName("Custom Book Genre").size());
    }

    @Test
    public void readGenreById() {
        BookGenre bookGenre = new BookGenre();
        bookGenre.setBookGenreName("Custom Book Genre");
        bookGenreDao.create(bookGenre);
        assertEquals(BookGenre.class, bookGenreDao.readById(1).getClass());
        assertNotNull(bookGenreDao.readById(1));
    }

    @Test
    public void newGenreGetRunningIds() {
        BookGenre bookGenre = new BookGenre();
        bookGenre.setBookGenreName("Custom Book Genre 1");
        bookGenreDao.create(bookGenre);

        BookGenre bookGenre2 = new BookGenre();
        bookGenre2.setBookGenreName("Custom Book Genre 2");
        bookGenreDao.create(bookGenre2);

        BookGenre bookGenre3 = new BookGenre();
        bookGenre3.setBookGenreName("Custom Book Genre 3");
        bookGenreDao.create(bookGenre3);

        assertEquals(3, bookGenreDao.readByName("Custom Book Genre").size());
        assertNotNull(bookGenreDao.readById(1));
        assertNotNull(bookGenreDao.readById(2));
        assertNotNull(bookGenreDao.readById(3));
    }
}
