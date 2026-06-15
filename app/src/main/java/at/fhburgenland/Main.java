package at.fhburgenland;

import at.fhburgenland.controller.*;
import at.fhburgenland.model.repository.implementations.BookRepositoryImpl;
import at.fhburgenland.model.repository.implementations.CirculationRepositoryImpl;
import at.fhburgenland.model.repository.implementations.CustomerRepositoryImpl;
import at.fhburgenland.model.repository.implementations.EventRepositoryImpl;
import at.fhburgenland.view.*;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("book");

    public static void main(String[] args) {
        BookRepositoryImpl bookRepository = new BookRepositoryImpl(entityManagerFactory);
        CustomerRepositoryImpl customerRepository = new CustomerRepositoryImpl(entityManagerFactory);
        
        BookController bookController = new BookController(bookRepository, new BookView());
        EventController eventController = new EventController(new EventRepositoryImpl(entityManagerFactory), new EventView());
        CustomerController customerController = new CustomerController(customerRepository, new CustomerView());
        CirculationLogController circulationLogController = new CirculationLogController(
                new CirculationRepositoryImpl(entityManagerFactory),
                bookRepository,
                customerRepository,
                new CirculationView(),
                new CustomerView()
        );
        
        LibraryController libraryController = new LibraryController(new LibraryView(), bookController, eventController, customerController, circulationLogController);
        libraryController.start();
        entityManagerFactory.close();
    }
}

