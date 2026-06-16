package at.fhburgenland.model.repository;

import at.fhburgenland.model.*;
import at.fhburgenland.model.repository.implementations.*;
import at.fhburgenland.model.repository.interfaces.*;
import at.fhburgenland.view.AnalyticsView;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnalyticsRepositoryImplTest {
    private static AnalyticsRepository analyticsRepository;
    private static BookRepository bookRepository;
    private static CustomerRepository customerRepository;
    private static CirculationRepository circulationRepository;
    private static EventRepository eventRepository;
    private static EntityManagerFactory entityManagerFactory;

    @BeforeAll
    public static void setupEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("book-unit-test");
        analyticsRepository = new AnalyticsRepositoryImpl(entityManagerFactory);
        circulationRepository = new CirculationRepositoryImpl(entityManagerFactory);
        bookRepository = new BookRepositoryImpl(entityManagerFactory);
        customerRepository = new CustomerRepositoryImpl(entityManagerFactory);
        eventRepository = new EventRepositoryImpl(entityManagerFactory);
    }

    @AfterAll
    public static void tearDownEntityManagerFactory() {
        entityManagerFactory.close();
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
        String lastName = "Dorfer";
        String street = "Waltendorf";
        String zip = "8010";
        String streetNumber = "14";
        String town = "Graz";
        String country = "Austria";

        customerRepository.save(firstName, lastName, street, streetNumber, zip, town, "Capital City", country);
        Customer newCustomer = customerRepository.findByLastName("Dorfer").get(0);

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

}
