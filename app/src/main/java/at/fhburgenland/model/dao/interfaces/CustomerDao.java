package at.fhburgenland.model.dao.interfaces;

import at.fhburgenland.model.*;

import java.util.List;

/**
 * Abstract class that provides CRUD operations for Customer
 */
public interface CustomerDao {
    void create(Customer model);

    Customer readById(Integer id);

    List<Customer> findByLastName(String name);

    List<Customer> findByFirstName(String firstName);

    Customer findByNameAndLastNameAndAddress(String firstName, String lastName, Street street, Country country, Zip zip, Town town);

    void update(Customer model);
    
    void delete(Customer model);

}
