package at.fhburgenland.model.dao;

import at.fhburgenland.model.*;
import at.fhburgenland.model.dao.implementations.EventDaoImpl;
import at.fhburgenland.model.dao.interfaces.EventDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class EventDaoImplTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    private EventDao eventDao;

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
        eventDao = new EventDaoImpl(entityManager);
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
    public void createAndFindEvent() {
        Event event = new Event();
        EventType eventType = new EventType();
        eventType.setEventTypeName("Test 1 Event Type");
        event.setEventType(eventType);
        event.setEventStartsAtTs(LocalDateTime.now());
        event.setEventName("Test 1 Event Name");
        eventDao.create(event);

        Event foundEvent = eventDao.readbyName("Test 1 Event Name").getFirst();
        assertNotNull(foundEvent);
        assertNotNull(foundEvent.getEventName());
        assertNotNull(foundEvent.getEventStartsAtTs());
        assertEquals(0, foundEvent.getBooks().size());
    }

    @Test
    public void createEventWithManyBooks() {
        Event newEvent = new Event();
        EventType newEventType = new EventType();
        BookPublisher bookPublisher = new BookPublisher();
        BookGenre bookGenre = new BookGenre();
        BookAuthor bookAuthor = new BookAuthor();

        bookGenre.setBookGenreName("Test 3: Book Genre" + UUID.randomUUID());
        bookAuthor.setBookAuthorName("Test 3: Book Author" + UUID.randomUUID());
        bookPublisher.setBookPublisherName("Test 3 Publisher" + UUID.randomUUID());
        newEventType.setEventTypeName("Test 3: Event Type" + UUID.randomUUID());
        newEvent.setEventName("Test 3: Save new Event - Test12345");
        newEvent.setEventType(newEventType);
        newEvent.setEventStartsAtTs(LocalDateTime.now());

        Book book1 = new Book();
        Book book2 = new Book();
        Book book3 = new Book();

        book1.setBookPublisher(bookPublisher);
        book2.setBookPublisher(bookPublisher);
        book3.setBookPublisher(bookPublisher);

        book1.setBookGenre(bookGenre);
        book2.setBookGenre(bookGenre);
        book3.setBookGenre(bookGenre);

        book1.setIsbn("1-2-3" + UUID.randomUUID());
        book2.setIsbn("1-2-3-4"+ UUID.randomUUID());
        book3.setIsbn("1-2-3-4-5"+ UUID.randomUUID());

        book1.setBookTitle("Test 1: Book Title"+ UUID.randomUUID());
        book2.setBookTitle("Test 2: Book Title"+ UUID.randomUUID());
        book3.setBookTitle("Test 3: Book Title"+ UUID.randomUUID());

        book1.setBookAuthors(Set.of(bookAuthor));
        book2.setBookAuthors(Set.of(bookAuthor));
        book3.setBookAuthors(Set.of(bookAuthor));

        newEvent.setBooks(Set.of(book1, book2, book3));
        eventDao.create(newEvent);

        Event justPersistedEvent = eventDao.readById(newEvent.getEventId());

        assertFalse(justPersistedEvent.getBooks().isEmpty());
        assertEquals(3, justPersistedEvent.getBooks().size());
        assertNotNull(justPersistedEvent.getBooks().stream().findFirst().get().getIsbn());
    }

    @Test
    public void removeEventAfterCreatingIt() {
        Event newEvent = new Event();
        EventType newEventType = new EventType();

        newEventType.setEventTypeName("Test 4: Event Type - Testtest");
        newEvent.setEventName("Test 4: Save new Event");
        newEvent.setEventType(newEventType);
        newEvent.setEventStartsAtTs(LocalDateTime.now());

        eventDao.create(newEvent);

        Event justPersistedEvent = eventDao.readbyName("Test 4: Save new Event").getFirst();
        assertNotNull(justPersistedEvent);

        eventDao.delete(justPersistedEvent);
        assertThrows(NoSuchElementException.class, () -> eventDao.readbyName("Test 4: Save new Event").getFirst());
    }
}
