package at.fhburgenland.model.repository;

import at.fhburgenland.model.*;
import at.fhburgenland.model.dto.CustomerAnalyticsDto;
import at.fhburgenland.model.repository.implementations.*;
import at.fhburgenland.model.repository.interfaces.*;
import at.fhburgenland.view.AnalyticsView;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AnalyticsRepositoryImplTest {
    private static AnalyticsRepository analyticsRepository;
    private static BookRepository bookRepository;
    private static CustomerRepository customerRepository;
    private static CirculationRepository circulationRepository;
    private static EventRepository eventRepository;
    private static EntityManagerFactory entityManagerFactory;
    private static EventCustomerRepository eventCustomerRepository;

    @BeforeAll
    public static void setupEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("book-unit-test");
        analyticsRepository = new AnalyticsRepositoryImpl(entityManagerFactory);
        circulationRepository = new CirculationRepositoryImpl(entityManagerFactory);
        bookRepository = new BookRepositoryImpl(entityManagerFactory);
        customerRepository = new CustomerRepositoryImpl(entityManagerFactory);
        eventRepository = new EventRepositoryImpl(entityManagerFactory);
        eventCustomerRepository = new EventCustomerRepositoryImpl(entityManagerFactory);
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

    /**
     * Show all borrowed books for a given customer
     * incl. borrow date within a defined time range
     */
    @Test
    public void testFirstAnalyticsQuery() {
        // First we create books
        Book firstBook = createStandardBook("1");
        Book secondBook = createStandardBook("2");
        Book thirdBook = createStandardBook("3");
        Book fourthBook = createStandardBook("4");

        for (Book book : Arrays.asList(firstBook, secondBook, thirdBook, fourthBook)) {
            BookLocation mainLocationForUniqueBook = new BookLocation();
            BookLocationFloor locationFloor = new BookLocationFloor();
            locationFloor.setBookLocationFloorNumber(ThreadLocalRandom.current().nextInt());

            BookLocationShelf locationShelf = new BookLocationShelf();
            locationShelf.setBookLocationShelfNumber(ThreadLocalRandom.current().nextInt());

            mainLocationForUniqueBook.setBookLocationFloor(locationFloor);
            mainLocationForUniqueBook.setBookLocationShelf(locationShelf);

            bookRepository.save(book);
            bookRepository.saveBookCopyCount(book, ThreadLocalRandom.current().nextInt(1, 7), mainLocationForUniqueBook);
        }

        // Then we create a customer

        String firstName = "Alfred";
        String lastName = "Dorfer444";
        String street = "Waltendorf";
        String zip = "8010";
        String streetNumber = "14";
        String town = "Graz";
        String country = "Austria";

        customerRepository.save(firstName, lastName, street, streetNumber, zip, town, "Capital City", country);
        Customer newCustomer = customerRepository.findByLastName("Dorfer444").get(0);

        // Then we loan a book
        circulationRepository.borrowBook(newCustomer, firstBook);
        circulationRepository.borrowBook(newCustomer, secondBook);
        circulationRepository.borrowBook(newCustomer, thirdBook);
        circulationRepository.borrowBook(newCustomer, fourthBook);

        // Then we print all borrowed books
        List<Book> foundBorrowedBooksForAlfred = analyticsRepository.getBooksLoanedByCustomer(newCustomer.getCustomerId(), LocalDate.now(), LocalDate.now().plusWeeks(2));
        assertEquals(4, foundBorrowedBooksForAlfred.size());
        AnalyticsView view = new AnalyticsView();
        view.printBooksAtLoanByCustomer(newCustomer, foundBorrowedBooksForAlfred, LocalDate.now(), LocalDate.now().plusWeeks(2));
    }

    /**
     * Show all physical existing books for a given book incl. its location
     */
    @Test
    public void testSecondAnalyticsQuery() {
        Book firstBook = createStandardBook("10");
        Book secondBook = createStandardBook("20");
        Book thirdBook = createStandardBook("30");
        Book fourthBook = createStandardBook("40");

        for (Book book : Arrays.asList(firstBook, secondBook, thirdBook, fourthBook)) {
            BookLocation mainLocationForUniqueBook = new BookLocation();
            BookLocationFloor locationFloor = new BookLocationFloor();
            locationFloor.setBookLocationFloorNumber(ThreadLocalRandom.current().nextInt());

            BookLocationShelf locationShelf = new BookLocationShelf();
            locationShelf.setBookLocationShelfNumber(ThreadLocalRandom.current().nextInt());

            mainLocationForUniqueBook.setBookLocationFloor(locationFloor);
            mainLocationForUniqueBook.setBookLocationShelf(locationShelf);

            bookRepository.save(book);
            bookRepository.saveBookCopyCount(book, 3, mainLocationForUniqueBook);

        }
        assertEquals(3, analyticsRepository.getBookStockLogByBookIsbn(firstBook.getIsbn()).size());
        assertEquals(3, analyticsRepository.getBookStockLogByBookIsbn(secondBook.getIsbn()).size());
        assertEquals(3, analyticsRepository.getBookStockLogByBookIsbn(thirdBook.getIsbn()).size());
        assertEquals(3, analyticsRepository.getBookStockLogByBookIsbn(fourthBook.getIsbn()).size());
    }

    /**
     * Show for each customer:
     * - count of books at loan
     * - count of visited events
     * - count of total activities
     */
    @Test
    public void testThirdAnalyticsQuery() {
        // First we create books
        Book firstBook = createStandardBook("11");
        Book secondBook = createStandardBook("12");
        Book thirdBook = createStandardBook("13");
        Book fourthBook = createStandardBook("14");

        for (Book book : Arrays.asList(firstBook, secondBook, thirdBook, fourthBook)) {
            BookLocation mainLocationForUniqueBook = new BookLocation();
            BookLocationFloor locationFloor = new BookLocationFloor();
            locationFloor.setBookLocationFloorNumber(ThreadLocalRandom.current().nextInt());

            BookLocationShelf locationShelf = new BookLocationShelf();
            locationShelf.setBookLocationShelfNumber(ThreadLocalRandom.current().nextInt());

            mainLocationForUniqueBook.setBookLocationFloor(locationFloor);
            mainLocationForUniqueBook.setBookLocationShelf(locationShelf);

            bookRepository.save(book);
            bookRepository.saveBookCopyCount(book, ThreadLocalRandom.current().nextInt(1, 7), mainLocationForUniqueBook);
        }

        // Then we create a customer

        String firstName = "Alfred";
        String lastName = "Dorfer123";
        String street = "Waltendorf";
        String zip = "8010";
        String streetNumber = "14";
        String town = "Graz";
        String country = "Austria";

        customerRepository.save(firstName, lastName, street, streetNumber, zip, town, "Capital City", country);
        Customer newCustomer = customerRepository.findByLastName("Dorfer123").get(0);

        // Then we create an event
        Event newEventWithBooks = new Event();
        EventType newEventType = new EventType();
        newEventType.setEventTypeName("Test 9: Event Type Test Bla");
        newEventWithBooks.setEventName("Test 9: Save new Event Test Bla");
        newEventWithBooks.setEventType(newEventType);
        newEventWithBooks.setEventStartsAtTs(LocalDateTime.now());
        eventRepository.save(newEventWithBooks);

        // Then the Customer visits events
        eventCustomerRepository.addCustomerToEvent(newCustomer, newEventWithBooks);

        // The customer also borrows books
        circulationRepository.borrowBook(newCustomer, secondBook);

        List<CustomerAnalyticsDto> dto = analyticsRepository.getActivityCountsPerCustomerByThreshold(0);
        assertNotNull(dto);
        assertEquals(1, dto.getFirst().countVisitedEvents());
        assertEquals(1, dto.getFirst().countBooksAtLoan());
        assertEquals(2, dto.getFirst().countTotalActivities());
    }

    /**
     * Show the attendant count for each event and show only those
     * which have above average attendant count
     */
    @Test
    public void testFourthAnalyticsQuery() {
        // Events
        Event firstEvent = new Event();
        EventType firstEventType = new EventType();
        firstEventType.setEventTypeName("Lesung");
        firstEvent.setEventType(firstEventType);
        firstEvent.setEventName("Lesung Thriller");
        firstEvent.setEventStartsAtTs(LocalDateTime.now());

        Event secondEvent = new Event();
        EventType secondEventType = new EventType();
        secondEvent.setEventType(secondEventType);
        secondEventType.setEventTypeName("Lesung 2");
        secondEvent.setEventName("Lesung Thriller 2");
        secondEvent.setEventStartsAtTs(LocalDateTime.now());


        Event thirdEvent = new Event();
        EventType thirdEventType = new EventType();
        thirdEventType.setEventTypeName("Lesung 3");
        thirdEvent.setEventType(thirdEventType);
        thirdEvent.setEventName("Lesung 3");
        thirdEvent.setEventStartsAtTs(LocalDateTime.now());

        eventRepository.save(firstEvent);
        eventRepository.save(secondEvent);
        eventRepository.save(thirdEvent);

        // Create Customer
        customerRepository.save("1", "1", "1", "1", "1", "1", "1", "1");
        customerRepository.save("2", "2", "2", "2", "2", "2", "2", "2");
        customerRepository.save("3", "3", "3", "3", "3", "3", "3", "3");
        customerRepository.save("4", "4", "4", "4", "4", "4", "4", "4");
        customerRepository.save("5", "5", "5", "5", "5", "5", "5", "5");
        customerRepository.save("6", "6", "6", "6", "6", "6", "6", "6");

        eventCustomerRepository.addCustomerToEvent(customerRepository.findByFirstName("1").get(0), firstEvent);
        eventCustomerRepository.addCustomerToEvent(customerRepository.findByFirstName("2").get(0), firstEvent);
        eventCustomerRepository.addCustomerToEvent(customerRepository.findByFirstName("3").get(0), secondEvent);
        eventCustomerRepository.addCustomerToEvent(customerRepository.findByFirstName("4").get(0), thirdEvent);
        eventCustomerRepository.addCustomerToEvent(customerRepository.findByFirstName("5").get(0), thirdEvent);
        eventCustomerRepository.addCustomerToEvent(customerRepository.findByFirstName("6").get(0), thirdEvent);

        assertEquals(3, analyticsRepository.getEventsWithMoreThanAverageAttendantCount().getFirst().participantCount());
    }

}
