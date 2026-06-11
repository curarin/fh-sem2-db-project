package at.fhburgenland.model.dao.implementations;

import at.fhburgenland.model.Customer;

import at.fhburgenland.model.dao.interfaces.CustomerDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

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
    public List<Customer> readByName(String name) {
        String query = "select m from Customer m where lower(m.lastName) = lower(:name)";
        TypedQuery<Customer> typedQuery = entityManager.createQuery(query, Customer.class);
        typedQuery.setParameter("name", name);
        return typedQuery.getResultList();
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
