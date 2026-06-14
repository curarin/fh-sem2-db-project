package at.fhburgenland.model.repository;

import at.fhburgenland.model.*;
import at.fhburgenland.model.repository.implementations.BookRepositoryImpl;
import at.fhburgenland.model.repository.implementations.CirculationRepositoryImpl;
import at.fhburgenland.model.repository.implementations.CustomerRepositoryImpl;
import at.fhburgenland.model.repository.interfaces.BookRepository;
import at.fhburgenland.model.repository.interfaces.CirculationRepository;
import at.fhburgenland.model.repository.interfaces.CustomerRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.UUID;

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
}
