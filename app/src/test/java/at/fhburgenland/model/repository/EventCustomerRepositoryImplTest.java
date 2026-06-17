package at.fhburgenland.model.repository;

import at.fhburgenland.model.Customer;
import at.fhburgenland.model.Event;
import at.fhburgenland.model.EventType;
import at.fhburgenland.model.repository.implementations.CustomerRepositoryImpl;
import at.fhburgenland.model.repository.implementations.EventCustomerRepositoryImpl;
import at.fhburgenland.model.repository.implementations.EventRepositoryImpl;
import at.fhburgenland.model.repository.interfaces.CustomerRepository;
import at.fhburgenland.model.repository.interfaces.EventCustomerRepository;
import at.fhburgenland.model.repository.interfaces.EventRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EventCustomerRepositoryImplTest {
    private static EntityManagerFactory entityManagerFactory;
    private static EventCustomerRepository eventCustomerRepository;
    private static CustomerRepository customerRepository;
    private static EventRepository eventRepository;

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

    @BeforeAll
    public static void setupEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("book-unit-test");
        eventCustomerRepository = new EventCustomerRepositoryImpl(entityManagerFactory);
        customerRepository = new CustomerRepositoryImpl(entityManagerFactory);
        eventRepository = new EventRepositoryImpl(entityManagerFactory);
    }

    @AfterAll
    public static void tearDownEntityManagerFactory() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    @Test
    public void testAddCustomerToEventAndGetCustomersByEvent() {
        // Create Customer
        customerRepository.save("Test", "Customer", "Street", "1", "1234", "Town", "City", "Country");
        List<Customer> foundCustomers = customerRepository.findByFirstName("Test");
        assertFalse(foundCustomers.isEmpty());
        Customer customer = foundCustomers.get(0);

        // Create Event
        Event event = new Event();
        event.setEventName("Test Event");
        event.setEventStartsAtTs(LocalDateTime.now().withNano(0)); // Avoid precision issues
        EventType type = new EventType();
        type.setEventTypeName("Test Type");
        event.setEventType(type);
        eventRepository.save(event);

        List<Event> foundEvents = eventRepository.findByName("Test Event");
        assertFalse(foundEvents.isEmpty());
        Event savedEvent = foundEvents.get(0);

        // Add Customer to Event
        eventCustomerRepository.addCustomerToEvent(customer, savedEvent);

        // Get Customers by Event
        List<Customer> customers = eventCustomerRepository.getCustomersByEvent(savedEvent.getEventId());

        assertFalse(customers.isEmpty());
        boolean found = false;
        for (Customer c : customers) {
            assertNotNull(c.getFirstName());
            if (c.getCustomerId().equals(customer.getCustomerId())) {
                found = true;
                break;
            }
        }
        assertTrue(found, "Customer should be found in the event guest list");
    }

    @Test
    public void testRemoveCustomerFromEvent() {
        // Create Customer
        customerRepository.save("Remove", "Customer", "Street", "1", "1234", "Town", "City", "Country");
        Customer customer = customerRepository.findByFirstName("Remove").get(0);

        // Create Event
        Event event = new Event();
        event.setEventName("Remove Event");
        event.setEventStartsAtTs(LocalDateTime.now().withNano(0));
        EventType type = new EventType();
        type.setEventTypeName("Remove Type");
        event.setEventType(type);
        eventRepository.save(event);
        Event savedEvent = eventRepository.findByName("Remove Event").get(0);

        // Add then Remove
        eventCustomerRepository.addCustomerToEvent(customer, savedEvent);
        List<Customer> customersBefore = eventCustomerRepository.getCustomersByEvent(savedEvent.getEventId());
        assertEquals(1, customersBefore.size());

        eventCustomerRepository.removeCustomerFromEvent(customer, savedEvent);
        List<Customer> customersAfter = eventCustomerRepository.getCustomersByEvent(savedEvent.getEventId());
        assertTrue(customersAfter.isEmpty());
    }
}
