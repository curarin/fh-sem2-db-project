package at.fhburgenland.controller;

import at.fhburgenland.model.Customer;
import at.fhburgenland.model.repository.interfaces.CustomerRepository;
import at.fhburgenland.view.CustomerView;

import java.util.List;

public class CustomerController {

    private final CustomerRepository customerRepository;
    private final CustomerView customerView;

    public CustomerController(CustomerRepository customerRepo, CustomerView customerView){
        this.customerRepository = customerRepo;
        this.customerView = customerView;
    }


    public void start() {
        boolean running = true;

        while (running) {
            int choice = customerView.showMainMenu();

            switch (choice) {
                case 1 -> {
                    switch (customerView.showExistingCustomerMenu()) {
                        case 1 -> {
                            String firstName = customerView.getCustomerFirstNameByUser();
                            List<Customer> customers = customerRepository.findByFirstName(firstName);
                            for (Customer customer : customers) {
                                customerView.printCustomer(customer);
                            }
                            customerView.printSearchStatistics(customers);
                        }
                        case 2 -> {
                            String lastName = customerView.getCustomerLastNameByUser();
                            List<Customer> customers = customerRepository.findByLastName(lastName);
                            for (Customer customer : customers) {
                                customerView.printCustomer(customer);
                            }
                            customerView.printSearchStatistics(customers);
                        }
                        case 3 -> {
                            int id = customerView.getCustomerIdByUser();
                            Customer customer = customerRepository.findById(id);
                            if (customer != null) {
                                customerView.printCustomer(customer);
                            } else {
                                customerView.printMessage("Customer with ID " + id + " not found.");
                            }
                        }
                        case 0 -> running = false;
                    }
                }
                case 2 -> {
                    String firstName = customerView.getCustomerFirstNameByUser();
                    String lastName = customerView.getCustomerLastNameByUser();
                    String street = customerView.getStreetByUser();
                    String zip = customerView.getZipByUser();
                    String town = customerView.getTownByUser();
                    String city = customerView.getCityByUser();
                    String country = customerView.getCountryByUser();

                    boolean customerCreated = customerRepository.save(firstName, lastName, street, zip, town, city, country);

                    if (!customerCreated) {
                        customerView.printMessage("Customer created successfully!");
                    } else {
                        customerView.printMessage("Customer already exists");
                    }
                }
                case 4 -> {
                    int id = customerView.getCustomerIdByUser();
                    Customer customerToBeDeleted = customerRepository.findById(id);
                    if (customerToBeDeleted != null) {
                        customerRepository.remove(customerToBeDeleted);
                        customerView.printMessage("Customer deleted successfully!");
                    } else {
                        customerView.printMessage("Customer with ID " + id + " not found.");
                    }
                }
                case 0 -> running = false;
                default -> customerView.printMessage("Invalid choice. Please try again.");
            }
        }
    }
}
