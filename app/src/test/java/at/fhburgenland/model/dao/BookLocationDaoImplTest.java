package at.fhburgenland.model.dao;

import at.fhburgenland.model.BookLocation;
import at.fhburgenland.model.BookLocationFloor;
import at.fhburgenland.model.BookLocationShelf;
import at.fhburgenland.model.dao.implementations.BookLocationDaoImpl;
import at.fhburgenland.model.dao.interfaces.BookLocationDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class BookLocationDaoImplTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    private BookLocationDao bookLocationDao;

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
        bookLocationDao = new BookLocationDaoImpl(entityManager);
        entityTransaction.begin();
    }

    @AfterEach
    public void tearDownEntityManager() {
        if (entityTransaction.isActive()) {
            entityTransaction.rollback();
        }
        entityManager.close();
    }

    @Test
    public void createNewBookLocation() {
        BookLocation bookLocation = new BookLocation();
        BookLocationFloor floor = new BookLocationFloor();
        floor.setBookLocationFloorNumber(1);
        BookLocationShelf shelf = new BookLocationShelf();
        shelf.setBookLocationShelfNumber(1);
        bookLocation.setBookLocationFloor(floor);
        bookLocation.setBookLocationShelf(shelf);

        // No ID before persisting
        assertNull(bookLocation.getBookLocationId());

        bookLocationDao.create(bookLocation);

        // Has ID after persisting
        assertNotNull(bookLocation.getBookLocationId());
        assertNotNull(bookLocationDao.readById(bookLocation.getBookLocationId()));
        assertEquals(1, bookLocationDao.readById(bookLocation.getBookLocationId()).getBookLocationFloor().getBookLocationFloorNumber());
        assertEquals(1, bookLocationDao.readById(bookLocation.getBookLocationId()).getBookLocationShelf().getBookLocationShelfNumber());
    }

    @Test
    public void removeBookLocation() {
        BookLocation bookLocationBeforeGettingRemoved = new BookLocation();
        BookLocationFloor floorToBeRemoved = new BookLocationFloor();
        floorToBeRemoved.setBookLocationFloorNumber(1);
        BookLocationShelf shelfToBeRemoved = new BookLocationShelf();
        shelfToBeRemoved.setBookLocationShelfNumber(1);
        bookLocationBeforeGettingRemoved.setBookLocationFloor(floorToBeRemoved);
        bookLocationBeforeGettingRemoved.setBookLocationShelf(shelfToBeRemoved);
        bookLocationDao.create(bookLocationBeforeGettingRemoved);


        BookLocation bookLocationToBeRemoved = bookLocationDao.readById(bookLocationBeforeGettingRemoved.getBookLocationId());
        assertNotNull(bookLocationToBeRemoved);

        bookLocationDao.delete(bookLocationToBeRemoved);
        assertNull(bookLocationDao.readById(bookLocationBeforeGettingRemoved.getBookLocationId()));
        assertNull(bookLocationDao.readById(bookLocationToBeRemoved.getBookLocationId()));
    }

    @Test
    public void updateBookLocation() {
        BookLocation bookLocationBeforeGettingUpdated = new BookLocation();
        BookLocationFloor floorBasic = new BookLocationFloor();
        floorBasic.setBookLocationFloorNumber(1);
        BookLocationShelf shelfBasic = new BookLocationShelf();
        shelfBasic.setBookLocationShelfNumber(1);
        bookLocationBeforeGettingUpdated.setBookLocationFloor(floorBasic);
        bookLocationBeforeGettingUpdated.setBookLocationShelf(shelfBasic);
        bookLocationDao.create(bookLocationBeforeGettingUpdated);

        assertNotNull(bookLocationBeforeGettingUpdated.getBookLocationId());

        BookLocation bookLocationToBeUpdated = bookLocationDao.readById(bookLocationBeforeGettingUpdated.getBookLocationId());
        assertNotNull(bookLocationToBeUpdated);
        assertEquals(1, bookLocationToBeUpdated.getBookLocationFloor().getBookLocationFloorNumber());

        BookLocationFloor floorToBeUpdated = new BookLocationFloor();
        floorToBeUpdated.setBookLocationFloorNumber(5);
        bookLocationToBeUpdated.setBookLocationFloor(floorToBeUpdated);
        bookLocationDao.update(bookLocationToBeUpdated);

        BookLocation bookLocationAfterUpdate = bookLocationDao.readById(bookLocationBeforeGettingUpdated.getBookLocationId());
        assertNotNull(bookLocationAfterUpdate);
        assertEquals(5, bookLocationAfterUpdate.getBookLocationFloor().getBookLocationFloorNumber());
        assertEquals(1, bookLocationAfterUpdate.getBookLocationShelf().getBookLocationShelfNumber());
    }
}
