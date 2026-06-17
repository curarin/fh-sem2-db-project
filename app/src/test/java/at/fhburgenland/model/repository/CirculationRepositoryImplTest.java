package at.fhburgenland.model.repository;

import at.fhburgenland.model.*;
import at.fhburgenland.model.repository.implementations.BookRepositoryImpl;
import at.fhburgenland.model.repository.implementations.CirculationRepositoryImpl;
import at.fhburgenland.model.repository.implementations.CustomerRepositoryImpl;
import at.fhburgenland.model.repository.interfaces.BookRepository;
import at.fhburgenland.model.repository.interfaces.CirculationRepository;
import at.fhburgenland.model.repository.interfaces.CustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CirculationRepositoryImplTest {
    private static EntityManagerFactory entityManagerFactory;
    private static CirculationRepository circulationRepository;
    private static BookRepository bookRepository;
    private static CustomerRepository customerRepository;

    @BeforeAll
    public static void setup() {
        entityManagerFactory = Persistence.createEntityManagerFactory("book-unit-test");
        circulationRepository = new CirculationRepositoryImpl(entityManagerFactory);
        bookRepository = new BookRepositoryImpl(entityManagerFactory);
        customerRepository = new CustomerRepositoryImpl(entityManagerFactory);
    }

    @AfterAll
    public static void tearDown() {
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

    private Customer createAndSaveCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Pete");
        customer.setLastName("Petrovic");

        City city = new City();
        city.setCityName("Wien");
        customer.setCity(city);

        Country country = new Country();
        country.setCountryName("Austria");
        customer.setCountry(country);

        Zip zip = new Zip();
        zip.setZipCode("1010");
        customer.setZip(zip);

        Town town = new Town();
        town.setTownName("Graz");
        customer.setTown(town);

        Street street = new Street();
        street.setStreet("Rabaa");
        customer.setStreet(street);

        customerRepository.save(customer);
        return customer;
    }


    @Test
    void testBorrowNonExistentStock() {
        String isbn = "0000000000";
        Book book = new Book();
        book.setIsbn(isbn);
        book.setBookTitle("No Stock Book");
        bookRepository.save(book); // Saved book but no copies in stock

        Customer customer = createAndSaveCustomer();

        assertThrows(RuntimeException.class, () -> {
            circulationRepository.borrowBook(customer, book);
        });
    }

    @Test
    void testFindOpenBooksByCustomer() {
        Customer customer = createAndSaveCustomer();

        Book book = new Book();
        book.setIsbn("1234567890");
        book.setBookTitle("Test Book");

        BookGenre genre = new BookGenre();
        genre.setBookGenreName("Test Genre");
        book.setBookGenre(genre);

        BookPublisher publisher = new BookPublisher();
        publisher.setBookPublisherName("Test Publisher");
        book.setBookPublisher(publisher);

        bookRepository.save(book);

        BookLocation location = new BookLocation();
        BookLocationFloor floor = new BookLocationFloor();
        floor.setBookLocationFloorNumber(1);
        location.setBookLocationFloor(floor);

        BookLocationShelf shelf = new BookLocationShelf();
        shelf.setBookLocationShelfNumber(1);
        location.setBookLocationShelf(shelf);

        bookRepository.saveBookCopyCount(book, 1, location);

        circulationRepository.borrowBook(customer, book);

        List<BookCirculationLog> openLogs = circulationRepository.findOpenBooksByCustomer(customer);
        assertNotNull(openLogs);
        assertEquals(1, openLogs.size());
        assertEquals(book.getIsbn(), openLogs.get(0).getFkStockid().getBook().getIsbn());

        circulationRepository.returnBook(openLogs.get(0).getBookCirculationLogId());

        openLogs = circulationRepository.findOpenBooksByCustomer(customer);
        assertEquals(0, openLogs.size());
    }
}
