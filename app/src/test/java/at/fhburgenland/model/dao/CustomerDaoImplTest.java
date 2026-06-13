package at.fhburgenland.model.dao;

import at.fhburgenland.model.*;
import at.fhburgenland.model.dao.implementations.CustomerDaoImpl;
import at.fhburgenland.model.dao.interfaces.CustomerDao;
import jakarta.persistence.*;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
    private Customer createStandardCustomer(String firstName, String lastName) {
        int randomNumber = ThreadLocalRandom.current().nextInt();

        Country country = new Country();
        country.setCountryName("Country".concat(String.valueOf(randomNumber)));
        entityManager.persist(country);

        City city = new City();
        city.setCityName("City".concat(String.valueOf(randomNumber)));
        entityManager.persist(city);

        Zip zip = new Zip();
        zip.setZipCode(String.valueOf(Math.abs(randomNumber % 10000)));
        entityManager.persist(zip);

        Town town = new Town();
        town.setTownName("Town".concat(String.valueOf(randomNumber)));
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
        Customer customer = createStandardCustomer("Maria", "Donner");
        customerDao.create(customer);

        Customer result = customerDao.readById(customer.getCustomerId());
        assertNotNull(result);
        assertEquals("Maria", result.getFirstName());
        assertEquals("Donner", result.getLastName());
    }

    @Test
    public void updateCustomer() {
        Customer customer = createStandardCustomer("Maria", "Donner");
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
        Customer customer = createStandardCustomer("Maria", "Donner");
        customerDao.create(customer);
        assertNotNull(customerDao.readById(customer.getCustomerId()));

        customerDao.delete(customer);
        assertNull(customerDao.readById(customer.getCustomerId()));
    }

    @Test
    public void findByFirstName() {
        Customer customer = createStandardCustomer("Maria", "Donner");
        customerDao.create(customer);

        List<Customer> found = customerDao.findByFirstName("Maria");
        assertFalse(found.isEmpty());
        assertEquals("Maria", found.getFirst().getFirstName());
    }

    @Test
    public void findByFirstNameIgnoreCase() {
        Customer customer = createStandardCustomer("Victoria", "Prein");
        customerDao.create(customer);

        List<Customer> found = customerDao.findByFirstName("victoria");
        assertFalse(found.isEmpty());
        assertEquals("Victoria", found.get(0).getFirstName());
    }

    @Test
    public void findByFirstNameNotFound() {
        List<Customer> found = customerDao.findByFirstName("NonExistent");
        assertTrue(found.isEmpty());
    }

    @Test
    public void findByLastName() {
        Customer customer = createStandardCustomer("Johann", "Meier");
        customerDao.create(customer);

        List<Customer> found = customerDao.findByLastName("Meier");
        assertFalse(found.isEmpty());
        assertEquals("Johann", found.get(0).getFirstName());
    }

    @Test
    public void findByLastNameIgnoreCase() {
        Customer customer = createStandardCustomer("Hannes", "Rupert");
        customerDao.create(customer);

        List<Customer> found = customerDao.findByLastName("rupert");
        assertFalse(found.isEmpty());
        assertEquals("Hannes", found.get(0).getFirstName());
    }
}
