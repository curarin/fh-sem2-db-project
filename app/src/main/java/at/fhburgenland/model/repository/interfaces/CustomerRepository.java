package at.fhburgenland.model.repository.interfaces;

import at.fhburgenland.model.Customer;

import java.util.List;

public interface CustomerRepository {

    public Customer findById(Integer id);

    public List<Customer> findByFirstName(String firstName);

    public List<Customer> findByLastName(String lastName);

    public Customer create(Customer customer);

    void save(String firstName, String lastName, String street, String zip, String town, String country);

    public void remove(Customer customer);

    public Customer update(Customer customer);


}
