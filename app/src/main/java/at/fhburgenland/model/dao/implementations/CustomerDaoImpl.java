package at.fhburgenland.model.dao.implementations;

import at.fhburgenland.model.*;

import at.fhburgenland.model.dao.interfaces.CustomerDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.Collections;
import java.util.List;

/**
 * Concrete implementation of Customer DAO - offers CRUD operations as well as additional
 */
public class CustomerDaoImpl implements CustomerDao {
    private final EntityManager entityManager;

    public CustomerDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(Customer model) {
        entityManager.persist(model);
    }

    @Override
    public Customer readById(Integer id) {
        return entityManager.find(Customer.class, id);
    }

    @Override
    public List<Customer> findByLastName(String lastName) {
        String query = "select customer from Customer customer where lower(customer.lastName) = lower(:name)";
        TypedQuery<Customer> typedQuery = entityManager.createQuery(query, Customer.class);
        typedQuery.setParameter("name", lastName);
        return typedQuery.getResultList();
    }

    @Override
    public List<Customer> findByFirstName(String firstName) {
        String query = "select c from Customer c where lower(c.firstName) = lower(:firstName)";
        TypedQuery<Customer> typedQuery = entityManager.createQuery(query, Customer.class);
        typedQuery.setParameter("firstName", firstName);
        return typedQuery.getResultList();
    }

    @Override
    public Customer findByNameAndLastNameAndAddress(String firstName, String lastName, Street street, Country country, Zip zip, Town town) {
        String query = """
                select c from Customer c
                where lower(c.firstName) = lower(:firstName) 
                and lower(c.lastName) = lower(:lastName) 
                and c.street = :street 
                and c.country = :country 
                and c.zip = :zip 
                and c.town = :town""";
        TypedQuery<Customer> typedQuery = entityManager.createQuery(query, Customer.class);
        typedQuery.setParameter("firstName", firstName);
        typedQuery.setParameter("lastName", lastName);
        typedQuery.setParameter("street", street);
        typedQuery.setParameter("country", country);
        typedQuery.setParameter("zip", zip);
        typedQuery.setParameter("town", town);
        return typedQuery.getResultList().isEmpty() ? null : typedQuery.getResultList().get(0);
    }

    @Override
    public void update(Customer model) {
        entityManager.merge(model);
    }

    @Override
    public void delete(Customer model) {
        entityManager.remove(model);
    }
}
