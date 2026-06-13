package at.fhburgenland.controller;

import at.fhburgenland.model.repository.implementations.CustomerRepositoryImpl;
import at.fhburgenland.model.repository.interfaces.CustomerRepository;
import at.fhburgenland.view.CustomerView;

public class CustomerController {

    CustomerRepository customerRepository;
    CustomerView customerView;

    public CustomerController(CustomerRepository customerRepo, CustomerView customerView){
        this.customerRepository = customerRepo;
        this.customerView = customerView;
    }


    public void start() {
        boolean running = true;

        while (running) {


        }
    }
}
