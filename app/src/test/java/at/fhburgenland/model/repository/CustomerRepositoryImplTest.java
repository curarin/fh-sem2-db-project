package at.fhburgenland.model.repository;

import at.fhburgenland.model.Customer;
import at.fhburgenland.model.repository.implementations.CustomerRepositoryImpl;
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

public class CustomerRepositoryImplTest {
    private static EntityManagerFactory entityManagerFactory;
    private static CustomerRepository customerRepository;

    @BeforeAll
    public static void setupEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("book-unit-test");
        customerRepository = new CustomerRepositoryImpl(entityManagerFactory);
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

    @Test
    public void testSaveAndFindById() {
        String firstName = "Alfred";
        String lastName = "Dorfer";
        String street = "Waltendorf";
        String zip = "8010";
        String streetNumber = "14";
        String town = "Graz";
        String country = "Austria";

        customerRepository.save(firstName, lastName, street, streetNumber, zip, town, "Capital City", country);

        List<Customer> customers = customerRepository.findByFirstName(firstName);
        assertFalse(customers.isEmpty());
        Customer customer = customers.get(0);
        assertEquals(firstName, customer.getFirstName());

        Customer found = customerRepository.findById(customer.getCustomerId());
        assertNotNull(found);
        assertEquals(firstName, found.getFirstName());
        assertNotNull(found.getStreet().getStreet());
        assertEquals("Waltendorf", found.getStreet().getStreet());
    }

    @Test
    public void testCreateAndRemove() {
        // Since Customer doesn't have cascade persist, we use the complex save method to ensure dependent entities exist
        customerRepository.save("Helene", "Fischer", "Hauptplatz", "5", "8080", "St. Gallen", "", "Schweiz");

        List<Customer> customers = customerRepository.findByFirstName("Helene");
        assertFalse(customers.isEmpty());
        Customer customer = customers.get(0);

        customerRepository.remove(customer);

        assertNull(customerRepository.findById(customer.getCustomerId()));
        assertTrue(customerRepository.findByFirstName("Helene").isEmpty());
    }

    @Test
    public void testUpdateCustomer() {
        String firstName = "Gudrun";
        customerRepository.save(firstName, "Wiener", "Parndorf", "4", "7111", "", "Outlet", "Austria");

        Customer customer = customerRepository.findByFirstName(firstName).get(0);
        customer.setFirstName("Marie");
        customerRepository.update(customer);

        Customer updated = customerRepository.findById(customer.getCustomerId());
        assertEquals("Marie", updated.getFirstName());
    }

    @Test
    public void testFindByLastName() {
        String lastName = "Uitz";
        customerRepository.save("Simone", lastName, "Hofstätten", "55", "8200", "Hofstätten", "Gleisdorf", "Austria");

        List<Customer> results = customerRepository.findByLastName(lastName);
        assertEquals(1, results.size());
        assertEquals(lastName, results.get(0).getLastName());
    }


}
