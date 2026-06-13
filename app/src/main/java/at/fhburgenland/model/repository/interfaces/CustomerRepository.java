package at.fhburgenland.model.repository.interfaces;

import at.fhburgenland.model.Customer;

import java.util.List;

public interface CustomerRepository {

    public Customer findById(Integer id);

    public List<Customer> findByFirstName(String firstName);

    public List<Customer> findByLastName(String lastName);

    public List<Customer> findAll();

    public Customer create(Customer customer);

    boolean save(String firstName, String lastName, String street, String zip, String town, String city, String country);

    public void remove(Customer customer);

    public Customer update(Customer customer);


}
