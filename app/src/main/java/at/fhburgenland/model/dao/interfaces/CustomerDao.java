package at.fhburgenland.model.dao.interfaces;

import at.fhburgenland.model.Customer;

import java.util.List;

/**
 * Abstract class that provides CRUD operations for Customer
 */
public interface CustomerDao {
    void create(Customer model);

    Customer readById(Integer id);

    List<Customer> readByLastName(String name);

    Customer findByFirstName(String firstName);

    void update(Customer model);
    
    void delete(Customer model);

}
