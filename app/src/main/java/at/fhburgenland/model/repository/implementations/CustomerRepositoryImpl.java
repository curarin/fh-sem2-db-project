package at.fhburgenland.model.repository.implementations;

import at.fhburgenland.model.Customer;
import at.fhburgenland.model.dao.implementations.CustomerDaoImpl;
import at.fhburgenland.model.dao.interfaces.CustomerDao;
import at.fhburgenland.model.repository.interfaces.CustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

public class CustomerRepositoryImpl implements CustomerRepository {

    private final EntityManagerFactory entityManagerFactory;

    public CustomerRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public Customer findById(Integer id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Customer customer = entityManager.find(Customer.class, id);
        entityManager.close();
        return customer;
    }


    @Override
    public Customer create(Customer customer) {
        return null;
    }

    @Override
    public Customer update(Customer customer) {
        return null;
    }

    @Override
    public void remove(Customer customer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = null;

        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            CustomerDao customerDao = new CustomerDaoImpl(entityManager);
            customerDao.delete(customer);
            entityTransaction.commit();
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }




}
