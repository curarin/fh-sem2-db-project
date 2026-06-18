package at.fhburgenland.model.repository;

import at.fhburgenland.model.*;
import at.fhburgenland.model.repository.implementations.BookRepositoryImpl;
import at.fhburgenland.model.repository.implementations.EventBookRepositoryImpl;
import at.fhburgenland.model.repository.implementations.EventRepositoryImpl;
import at.fhburgenland.model.repository.interfaces.BookRepository;
import at.fhburgenland.model.repository.interfaces.EventBookRepository;
import at.fhburgenland.model.repository.interfaces.EventRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EventBookRepositoryImplTest {
    private static EntityManagerFactory entityManagerFactory;
    private static EventBookRepository eventBookRepository;
    private static BookRepository bookRepository;
    private static EventRepository eventRepository;

    @BeforeAll
    public static void setupEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("book-unit-test");
        eventBookRepository = new EventBookRepositoryImpl(entityManagerFactory);
        eventRepository = new EventRepositoryImpl(entityManagerFactory);
        bookRepository = new BookRepositoryImpl(entityManagerFactory);
    }

    @AfterAll
    public static void tearDownEntityManagerFactory() {
        entityManagerFactory.close();
    }

    @BeforeEach
    public void cleanDatabase() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.createQuery("delete from CustomerEventMap").executeUpdate();
        entityManager.createQuery("delete from BookCirculationLog").executeUpdate();
        entityManager.createQuery("delete from Event").executeUpdate();
        entityManager.createQuery("delete from Customer").executeUpdate();
        entityManager.createQuery("delete from BookStockLog").executeUpdate();
        entityManager.createQuery("delete from Book").executeUpdate();
        entityManager.createQuery("delete from BookLocation ").executeUpdate();
        entityManager.createQuery("delete from BookLocationFloor").executeUpdate();
        entityManager.createQuery("delete from BookLocationShelf ").executeUpdate();
        entityManager.createQuery("delete from BookPublisher").executeUpdate();
        entityManager.createQuery("delete from BookGenre").executeUpdate();
        entityManager.createQuery("delete from BookAuthor").executeUpdate();

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Event createEvent() {
        Event newEvent = new Event();
        EventType newEventType = new EventType();
        newEventType.setEventTypeName("Test 1: Event Type");
        newEvent.setEventName("Test 1: Save new Event");
        newEvent.setEventType(newEventType);
        newEvent.setEventStartsAtTs(LocalDateTime.now());
        return newEvent;
    }

    private Book createStandardBook(String isbn) {
        int randomNumber = ThreadLocalRandom.current().nextInt();
        String authorName = "Standard Author".concat(String.valueOf(randomNumber));
        BookAuthor author = new BookAuthor();
        author.setBookAuthorName(authorName);

        BookStockLog stockLog = new BookStockLog();
        stockLog.setBookIsInStock(true);

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

    @Test
    public void addBooksToEvent() {
        Book bookOne = createStandardBook("1");
        Book bookTwo = createStandardBook("2");
        Book bookThree = createStandardBook("3");

        bookRepository.save(bookOne);
        bookRepository.save(bookTwo);
        bookRepository.save(bookThree);

        Event eventOne = createEvent();
        eventRepository.save(eventOne);

        System.out.println(eventRepository.findById(eventOne.getEventId()).getEventId());

        assertEquals(Collections.EMPTY_LIST, eventBookRepository.getBooksFromEvent(eventOne));
        eventBookRepository.addBookToEvent(bookOne, eventOne);

        assertNotNull(eventBookRepository.getBooksFromEvent(eventOne).getFirst());
        assertEquals(1, eventBookRepository.getBooksFromEvent(eventOne).size());

        eventBookRepository.addBookToEvent(bookTwo, eventOne);
        assertEquals(2, eventBookRepository.getBooksFromEvent(eventOne).size());

        eventBookRepository.addBookToEvent(bookThree, eventOne);
        assertEquals(3, eventBookRepository.getBooksFromEvent(eventOne).size());
    }

    @Test
    public void removeBooksFromEvent() {
        Book bookOne = createStandardBook("1");
        Book bookTwo = createStandardBook("2");
        Book bookThree = createStandardBook("3");

        bookRepository.save(bookOne);
        bookRepository.save(bookTwo);
        bookRepository.save(bookThree);

        Event eventOne = createEvent();
        eventRepository.save(eventOne);

        eventBookRepository.addBookToEvent(bookOne, eventOne);
        eventBookRepository.addBookToEvent(bookTwo, eventOne);
        eventBookRepository.addBookToEvent(bookThree, eventOne);

        assertEquals(3, eventBookRepository.getBooksFromEvent(eventOne).size());

        eventBookRepository.removeBookFromEvent(bookOne, eventOne);

        assertEquals(2, eventBookRepository.getBooksFromEvent(eventOne).size());

        eventBookRepository.removeBookFromEvent(bookTwo, eventOne);
        assertEquals(1, eventBookRepository.getBooksFromEvent(eventOne).size());

        eventBookRepository.removeBookFromEvent(bookThree, eventOne);

        assertEquals(Collections.EMPTY_LIST, eventBookRepository.getBooksFromEvent(eventOne));


    }
}
