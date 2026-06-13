package at.fhburgenland.model.repository.interfaces;

import at.fhburgenland.model.Customer;

public interface CustomerRepository {

    public Customer findById(Integer id);

    public Customer create(Customer customer);

    public void remove(Customer customer);

    public Customer update(Customer customer);


}
