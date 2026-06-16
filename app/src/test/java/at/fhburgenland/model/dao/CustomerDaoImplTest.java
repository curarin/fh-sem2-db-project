package at.fhburgenland.model.dao;

import at.fhburgenland.model.*;
import at.fhburgenland.model.dao.implementations.CustomerDaoImpl;
import at.fhburgenland.model.dao.interfaces.CustomerDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerDaoImplTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    private CustomerDao customerDao;

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
        customerDao = new CustomerDaoImpl(entityManager);
        entityTransaction.begin();
    }

    @AfterEach
    public void tearDownEntityManager() {
        if (entityTransaction.isActive()) {
            entityTransaction.rollback();
        }
        entityManager.close();
    }

    /**
     * Standard customer generation method
     */
    private Customer createCustomer(String firstName, String lastName) {

        Country country = new Country();
        country.setCountryName("Austria");
        entityManager.persist(country);

        City city = new City();
        city.setCityName("Eisenstadt");
        entityManager.persist(city);

        Zip zip = new Zip();
        zip.setZipCode("7000");
        entityManager.persist(zip);

        Town town = new Town();
        town.setTownName("Eisenstadt");
        entityManager.persist(town);

        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setCountry(country);
        customer.setCity(city);
        customer.setZip(zip);
        customer.setTown(town);

        return customer;
    }

    @Test
    public void createAndReadCustomer() {
        Customer customer = createCustomer("Maria", "Donner");
        customerDao.create(customer);

        Customer result = customerDao.readById(customer.getCustomerId());
        assertNotNull(result);
        assertEquals("Maria", result.getFirstName());
        assertEquals("Donner", result.getLastName());
    }

    @Test
    public void updateCustomer() {
        Customer customer = createCustomer("Maria", "Donner");
        customerDao.create(customer);

        Customer createdCustomer = customerDao.readById(customer.getCustomerId());
        assertNotNull(createdCustomer);
        assertEquals("Maria", createdCustomer.getFirstName());

        createdCustomer.setFirstName("Updated");
        customerDao.update(createdCustomer);
        assertEquals("Updated", customerDao.readById(customer.getCustomerId()).getFirstName());
    }

    @Test
    public void deleteCustomer() {
        Customer customer = createCustomer("Maria", "Donner");
        customerDao.create(customer);
        assertNotNull(customerDao.readById(customer.getCustomerId()));

        customerDao.delete(customer);
        assertNull(customerDao.readById(customer.getCustomerId()));
    }

    @Test
    public void findByFirstName() {
        Customer customer = createCustomer("Maria", "Donner");
        customerDao.create(customer);

        List<Customer> found = customerDao.findByFirstName("Maria");
        assertFalse(found.isEmpty());
        assertEquals("Maria", found.getFirst().getFirstName());
    }

    @Test
    public void findByFirstNameNotFound() {
        List<Customer> found = customerDao.findByFirstName("NonExistent");
        assertTrue(found.isEmpty());
    }

    @Test
    public void findByLastName() {
        Customer customer = createCustomer("Johann", "Meier");
        customerDao.create(customer);

        List<Customer> found = customerDao.findByLastName("Meier");
        assertFalse(found.isEmpty());
        assertEquals("Johann", found.get(0).getFirstName());
    }

}
