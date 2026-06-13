package at.fhburgenland.model.repository.implementations;

import at.fhburgenland.model.*;
import at.fhburgenland.model.dao.implementations.*;
import at.fhburgenland.model.dao.interfaces.*;
import at.fhburgenland.model.repository.interfaces.CustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.List;

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
    public List<Customer> findByFirstName(String firstName) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            CustomerDao customerDao = new CustomerDaoImpl(entityManager);
            return customerDao.findByFirstName(firstName);
        }
    }

    @Override
    public List<Customer> findByLastName(String lastName) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            CustomerDao customerDao = new CustomerDaoImpl(entityManager);
            return customerDao.findByLastName(lastName);
        }
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
    public void save(String firstName, String lastName, String streetString,
                     String zipString, String townString, String countryString) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = null;

        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();

            CustomerDao customerDao = new CustomerDaoImpl(entityManager);
            ZipDao zipDao = new ZipDaoImpl(entityManager);
            StreetDao streetDao = new StreetDaoImpl(entityManager);
            TownDao townDao = new TownDaoImpl(entityManager);
            CountryDao countryDao = new CountryDaoImpl(entityManager);

            Zip zip = zipDao.findByZip(zipString);
            Town town = townDao.findByName(townString);
            Street street = streetDao.findByName(streetString);
            Country country = countryDao.findByName(countryString);

            if (zip == null) {
                zip = new Zip();
                zip.setZip(zipString);
                zipDao.create(zip);
            }

            if (town == null) {
                town = new Town();
                town.setName(townString);
                townDao.create(town);
            }

            if (street == null) {
                street = new Street();
                street.setName(streetString);
                streetDao.create(street);
            }

            if (country == null) {
                country = new Country();
                country.setName(countryString);
                countryDao.create(country);
            }

            Customer byNameAndLastNameAndAddress = customerDao.findByNameAndLastNameAndAddress(firstName, lastName,
                    street, country, zip, town);

            if (byNameAndLastNameAndAddress == null) {
                Customer customer = new Customer();
                customer.setFirstName(firstName);
                customer.setLastName(lastName);
                customer.setStreet(street);
                customer.setCountry(country);
                customer.setZip(zip);
                customer.setTown(town);
                customerDao.create(customer);
            }

            entityTransaction.commit();
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
        }


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
