package at.fhburgenland.model.repository.implementations;

import at.fhburgenland.model.Customer;
import at.fhburgenland.model.CustomerEventMap;
import at.fhburgenland.model.CustomerEventMapId;
import at.fhburgenland.model.Event;
import at.fhburgenland.model.dao.implementations.CustomerEventMapDaoImpl;
import at.fhburgenland.model.dao.interfaces.CustomerEventMapDao;
import at.fhburgenland.model.repository.interfaces.EventCustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.List;

public class EventCustomerRepositoryImpl implements EventCustomerRepository {
    private final EntityManagerFactory entityManagerFactory;

    public EventCustomerRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * Finds a customer event map by customer and event
     * @param customer customer
     * @param event event
     */
    @Override
    public CustomerEventMap findById(Customer customer, Event event) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            CustomerEventMapId id = new CustomerEventMapId();
            id.setCustomerId(customer.getCustomerId());
            id.setEventId(event.getEventId());

            CustomerEventMapDao dao = new CustomerEventMapDaoImpl(entityManager);
            CustomerEventMap customerEventMap = dao.readById(id);

            transaction.commit();
            return customerEventMap;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void addCustomerToEvent(Customer customer, Event event) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            CustomerEventMapId id = new CustomerEventMapId();
            id.setCustomerId(customer.getCustomerId());
            id.setEventId(event.getEventId());

            CustomerEventMap map = new CustomerEventMap();
            map.setId(id);
            map.setCustomer(entityManager.merge(customer));
            map.setEvent(entityManager.merge(event));

            CustomerEventMapDao dao = new CustomerEventMapDaoImpl(entityManager);
            dao.create(map);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    /**
     * Removes a customer from an event
     * @param customer Customer to remove
     * @param event Event to remove customer from
     */
    @Override
    public void removeCustomerFromEvent(Customer customer, Event event) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            CustomerEventMapId id = new CustomerEventMapId();
            id.setCustomerId(customer.getCustomerId());
            id.setEventId(event.getEventId());

            CustomerEventMapDao dao = new CustomerEventMapDaoImpl(entityManager);
            CustomerEventMap customerEventMap = dao.readById(id);
            if (customerEventMap != null) {
                dao.delete(customerEventMap);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Customer> getCustomersByEvent(int eventId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            CustomerEventMapDao dao = new CustomerEventMapDaoImpl(entityManager);
            List<CustomerEventMap> maps = dao.findByEventId(eventId);
            List<Customer> customers = new ArrayList<>();
            for (CustomerEventMap map : maps) {
                customers.add(map.getCustomer());
            }
            return customers;
        } finally {
            entityManager.close();
        }
    }
}
