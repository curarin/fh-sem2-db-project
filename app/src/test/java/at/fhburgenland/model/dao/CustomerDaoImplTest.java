package at.fhburgenland.model.dao;

import at.fhburgenland.model.*;
import at.fhburgenland.model.dao.implementations.CustomerDaoImpl;
import at.fhburgenland.model.dao.interfaces.CustomerDao;
import jakarta.persistence.*;
import org.junit.jupiter.api.*;

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
     * Creates a new customer with the given first and last name.
     * https://www.baeldung.com/hibernate-entitymanager#1-persisting-entities
     * @param firstName first name of the customer
     * @param lastName lastname of the customer
     * @return Customer
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
    public void testFindByFirstName() {
        Customer customer = createCustomer("Maria", "Donner");
        customerDao.create(customer);
        
        Customer found = customerDao.findByFirstName("Maria");
        assertNotNull(found);
        assertEquals("Maria", found.getFirstName());
        assertEquals("Donner", found.getLastName());
    }

    @Test
    public void testFindByFirstNameIgnoreCase() {
        Customer customer = createCustomer("Victoria", "Prein");
        customerDao.create(customer);
        
        Customer found = customerDao.findByFirstName("Victoria");
        assertNotNull(found);
        assertEquals("Victoria", found.getFirstName());
    }

    @Test
    public void testFindByFirstNameNotFound() {
        assertThrows(NoResultException.class, () -> {
            customerDao.findByFirstName("NonExistent");
        });
    }

    @Test
    public void testReadByLastName() {
        Customer customer = createCustomer("Johann", "Meier");
        customerDao.create(customer);
        
        java.util.List<Customer> found = customerDao.readByLastName("Meier");
        assertFalse(found.isEmpty());
        assertEquals("Johann", found.get(0).getFirstName());
    }

    @Test
    public void testReadByLastNameIgnoreCase() {
        Customer customer = createCustomer("Hannes", "Rupert");
        customerDao.create(customer);
        
        java.util.List<Customer> found = customerDao.readByLastName("rupert");
        assertFalse(found.isEmpty());
        assertEquals("Hannes", found.get(0).getFirstName());
    }
}
