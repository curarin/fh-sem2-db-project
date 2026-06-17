package at.fhburgenland.model.repository.interfaces;

import at.fhburgenland.model.Customer;
import at.fhburgenland.model.Event;

import java.util.List;

public interface EventCustomerRepository {
    void findById(int id);
    void addCustomerToEvent(Customer customer, Event event);
    void removeCustomerFromEvent(Customer customer, Event event);
    List<Customer> getCustomersByEvent(int eventId);

}
