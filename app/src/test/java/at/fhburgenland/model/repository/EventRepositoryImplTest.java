package at.fhburgenland.model.repository;

import at.fhburgenland.model.*;
import at.fhburgenland.model.repository.implementations.EventRepositoryImpl;
import at.fhburgenland.model.repository.interfaces.EventRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class EventRepositoryImplTest {
    private static EntityManagerFactory entityManagerFactory;
    private static EventRepository eventRepository;

    @BeforeAll
    public static void setupEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("book-unit-test");
        eventRepository = new EventRepositoryImpl(entityManagerFactory);
    }

    @AfterAll
    public static void tearDownEntityManagerFactory() {
        entityManagerFactory.close();
    }

    @Test
    public void saveNewEvent() {
        Event newEvent = new Event();
        EventType newEventType = new EventType();
        newEventType.setEventTypeName("Test 1: Event Type");
        newEvent.setEventName("Test 1: Save new Event");
        newEvent.setEventType(newEventType);
        newEvent.setEventStartsAtTs(LocalDateTime.now());
        assertNull(newEvent.getEventId()); // before persisting the event through JPA there is no PK
        eventRepository.save(newEvent);
        assertNotNull(eventRepository.findByName("Test 1: Save new Event"));
        assertNotNull(newEvent.getEventId()); // after persisting event through JPA there is a PK
    }

    @Test
    public void saveNewEventWithoutBooksReturnsNullBooks() {
        Event newEvent = new Event();
        EventType newEventType = new EventType();
        newEventType.setEventTypeName("Test 2: Event Type");
        newEvent.setEventName("Test 2: Save new Event");
        newEvent.setEventType(newEventType);
        newEvent.setEventStartsAtTs(LocalDateTime.now());
        eventRepository.save(newEvent);
        Event justPersistedEvent = eventRepository.findById(newEvent.getEventId());
        assertEquals(Collections.EMPTY_SET, justPersistedEvent.getBooks());
    }

    @Test
    public void saveNewEventWithBooksReturnsBooks() {
        Event newEvent = new Event();
        EventType newEventType = new EventType();
        BookPublisher bookPublisher = new BookPublisher();
        BookGenre bookGenre = new BookGenre();
        BookAuthor bookAuthor = new BookAuthor();

        bookGenre.setBookGenreName("Test 3: Book Genre");
        bookAuthor.setBookAuthorName("Test 3: Book Author");
        bookPublisher.setBookPublisherName("Test 3 Publisher");
        newEventType.setEventTypeName("Test 3: Event Type");
        newEvent.setEventName("Test 3: Save new Event");
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

        book1.setBookAuthors(Set.of(bookAuthor));
        book2.setBookAuthors(Set.of(bookAuthor));
        book3.setBookAuthors(Set.of(bookAuthor));

        newEvent.setBooks(Set.of(book1, book2, book3));
        eventRepository.save(newEvent);

        assertFalse(newEvent.getBooks().isEmpty());
        assertEquals(3, newEvent.getBooks().size());
    }

    @Test
    public void updateEventWithNewData() {
        Event newEvent = new Event();
        EventType newEventType = new EventType();
        newEventType.setEventTypeName("Test 4: Event Type");
        newEvent.setEventName("Test 4: Save new Event");
        newEvent.setEventType(newEventType);
        newEvent.setEventStartsAtTs(LocalDateTime.now());
        eventRepository.save(newEvent);
        Event justPersistedEvent = eventRepository.findById(newEvent.getEventId());
        assertEquals(Collections.EMPTY_SET, justPersistedEvent.getBooks());
        assertEquals("Test 4: Event Type", justPersistedEvent.getEventType().getEventTypeName());
        assertEquals("Test 4: Save new Event", justPersistedEvent.getEventName());

        EventType updatedEventType = new EventType();
        updatedEventType.setEventTypeName("Test 4: Updated Event Type");
        justPersistedEvent.setEventName("Test 4: Updated Name");
        justPersistedEvent.setEventType(updatedEventType);
        eventRepository.save(justPersistedEvent);

        Event justUpdatedEvent = eventRepository.findById(justPersistedEvent.getEventId());
        assertEquals("Test 4: Updated Name", justUpdatedEvent.getEventName());
        assertEquals("Test 4: Updated Event Type", justUpdatedEvent.getEventType().getEventTypeName());
    }

    @Test
    public void removeEventFromDatabase() {
        Event newEvent = new Event();
        EventType newEventType = new EventType();
        newEventType.setEventTypeName("Test 5: Event Type");
        newEvent.setEventName("Test 5: Save new Event");
        newEvent.setEventType(newEventType);
        newEvent.setEventStartsAtTs(LocalDateTime.now());
        eventRepository.save(newEvent);
        assertNotNull(eventRepository.findById(newEvent.getEventId()));

        eventRepository.remove(newEvent.getEventId());
        assertNull(eventRepository.findById(newEvent.getEventId()));
        assertEquals(Collections.EMPTY_LIST, eventRepository.findByName("Test 5: Save new Event"));
    }

    @Test
    public void findAllEventsSortedByStartsAtTsDescending() {
        LocalDateTime earlier = LocalDateTime.now().minusDays(3);
        LocalDateTime later = earlier.plusDays(2);
        Event newEvent = new Event();
        EventType newEventType = new EventType();
        newEventType.setEventTypeName("Test 6: Event Type");
        newEvent.setEventName("Test 6: Save new Event");
        newEvent.setEventType(newEventType);
        newEvent.setEventStartsAtTs(earlier);

        Event newEvent2 = new Event();
        EventType newEventType2 = new EventType();
        newEventType2.setEventTypeName("Test 7: Event Type");
        newEvent2.setEventName("Test 7: Save new Event");
        newEvent2.setEventType(newEventType2);
        newEvent2.setEventStartsAtTs(later);

        eventRepository.save(newEvent);
        eventRepository.save(newEvent2);

        List<Event> allFoundEvents = eventRepository.findAll();
        assertFalse(allFoundEvents.isEmpty());

        LocalDateTime first = allFoundEvents.get(0).getEventStartsAtTs();
        LocalDateTime second = allFoundEvents.get(1).getEventStartsAtTs();
        assertTrue(first.isAfter(second)); // The latest shall be first in list - so when we print its the first to show
    }

    @Test
    public void findEventByName() {
        Event newEvent = new Event();
        EventType newEventType = new EventType();
        newEventType.setEventTypeName("Test 8: Event Type");
        newEvent.setEventName("Test 8: Save new Event");
        newEvent.setEventType(newEventType);
        newEvent.setEventStartsAtTs(LocalDateTime.now());
        eventRepository.save(newEvent);
        assertNotNull(eventRepository.findByName("Test 8: Save new Event"));
    }
}
