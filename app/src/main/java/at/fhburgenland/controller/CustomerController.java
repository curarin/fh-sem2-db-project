package at.fhburgenland.controller;

import at.fhburgenland.model.*;
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
                    Customer customer = selectCustomer(customerView, customerRepository);
                }
                case 2 -> {
                    String firstName = customerView.getCustomerFirstNameByUser();
                    String lastName = customerView.getCustomerLastNameByUser();
                    String street = customerView.getStreetByUser();
                    String zip = customerView.getZipByUser();
                    String town = customerView.getTownByUser();
                    String city = customerView.getCityByUser();
                    String country = customerView.getCountryByUser();
                    String streetNumber = customerView.getStreetNumberByUser();

                    boolean customerCreated = customerRepository.save(firstName, lastName, street, streetNumber, zip, town, city, country);

                    if (!customerCreated) {
                        customerView.printMessage("Customer created successfully!");
                    } else {
                        customerView.printMessage("Customer already exists");
                    }
                }
                case 3 -> {
                    // Edit existing customer
                    int id = customerView.getCustomerIdByUser();
                    Customer customerToBeEdited = customerRepository.findById(id);
                    customerView.printCustomer(customerToBeEdited);

                    if (customerToBeEdited != null) {
                        switch (customerView.showEditOptionsMenu()) {
                            case 1 -> {
                                String firstName = customerView.getCustomerFirstNameByUser();
                                customerToBeEdited.setFirstName(firstName);
                                customerRepository.save(customerToBeEdited);
                            }
                            case 2 -> {
                                String lastName = customerView.getCustomerLastNameByUser();
                                customerToBeEdited.setLastName(lastName);
                                customerRepository.save(customerToBeEdited);
                            }
                            case 3 -> {
                                String streetName = customerView.getStreetByUser();
                                Street street = new Street();
                                street.setStreet(streetName);
                                customerToBeEdited.setStreet(street);
                                customerRepository.save(customerToBeEdited);
                            }
                            case 4 -> {
                                String streetNumber = customerView.getStreetNumberByUser();
                                customerToBeEdited.setStreetNumber(streetNumber);
                                customerRepository.save(customerToBeEdited);
                            }
                            case 5 -> {
                                String zipCode = customerView.getZipByUser();
                                Zip zip = new Zip();
                                zip.setZipCode(zipCode);
                                customerToBeEdited.setZip(zip);
                                customerRepository.save(customerToBeEdited);
                            }
                            case 6 -> {
                                String townName = customerView.getTownByUser();
                                Town town = new Town();
                                town.setTownName(townName);
                                customerToBeEdited.setTown(town);
                                customerRepository.save(customerToBeEdited);
                            }
                            case 7 -> {
                                String cityName = customerView.getCityByUser();
                                City city = new City();
                                city.setCityName(cityName);
                                customerToBeEdited.setCity(city);
                                customerRepository.save(customerToBeEdited);
                            }
                            case 8 -> {
                                String countryName = customerView.getCountryByUser();
                                Country country = new Country();
                                country.setCountryName(countryName);
                                customerToBeEdited.setCountry(country);
                                customerRepository.save(customerToBeEdited);
                            }
                        }
                    } else {
                        customerView.printMessage("Customer with ID " + id + " not found.");
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

    public static Customer selectCustomer(CustomerView customerView, CustomerRepository customerRepository) {
        Integer selectedCustomerId = switch (customerView.showExistingCustomerMenu()) {
            case 1 -> {
                String firstName = customerView.getCustomerFirstNameByUser();
                List<Customer> customers = customerRepository.findByFirstName(firstName);
                if (customers.isEmpty()) {
                    customerView.printMessage("No customers found.");
                    yield null;
                }
                for (Customer customer : customers) {
                    customerView.printCustomer(customer);
                }
                customerView.printSearchStatistics(customers);
                int id = customerView.getCustomerIdByUser();
                yield (id <= 0) ? null : id;
            }
            case 2 -> {
                String lastName = customerView.getCustomerLastNameByUser();
                List<Customer> customers = customerRepository.findByLastName(lastName);
                if (customers.isEmpty()) {
                    customerView.printMessage("No customers found.");
                    yield null;
                }
                for (Customer customer : customers) {
                    customerView.printCustomer(customer);
                }
                customerView.printSearchStatistics(customers);
                int id = customerView.getCustomerIdByUser();
                yield (id <= 0) ? null : id;
            }
            case 3 -> {
                int id = customerView.getCustomerIdByUser();
                if (id <= 0) yield null;
                Customer customer = customerRepository.findById(id);
                if (customer != null) {
                    customerView.printCustomer(customer);
                    yield customer.getCustomerId();
                } else {
                    customerView.printMessage("Customer with ID " + id + " not found.");
                    yield null;
                }
            }
            default -> null;
        };

        if (selectedCustomerId != null) {
            return customerRepository.findById(selectedCustomerId);
        }

        return null;
    }

}
