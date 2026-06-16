package at.fhburgenland;

import at.fhburgenland.controller.*;
import at.fhburgenland.model.repository.implementations.*;
import at.fhburgenland.view.*;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("book");

    public static void main(String[] args) {
        BookRepositoryImpl bookRepository = new BookRepositoryImpl(entityManagerFactory);
        CustomerRepositoryImpl customerRepository = new CustomerRepositoryImpl(entityManagerFactory);
        EventRepositoryImpl eventRepository = new EventRepositoryImpl(entityManagerFactory);
        AnalyticsRepositoryImpl analyticsRepository = new AnalyticsRepositoryImpl(entityManagerFactory);
        CirculationRepositoryImpl circulationRepository = new CirculationRepositoryImpl(entityManagerFactory);

        BookController bookController = new BookController(bookRepository, new BookView());
        EventController eventController = new EventController(eventRepository, new EventView(), bookRepository, new BookView());
        CustomerController customerController = new CustomerController(customerRepository, new CustomerView());
        CirculationLogController circulationLogController = new CirculationLogController(circulationRepository, bookRepository, customerRepository, new CirculationView(), new CustomerView());
        AnalyticsController analyticsController = new AnalyticsController(new AnalyticsView(), new CustomerView(), analyticsRepository, customerRepository, bookRepository);

        LibraryController libraryController = new LibraryController(new LibraryView(), bookController, eventController, customerController, circulationLogController, analyticsController);
        libraryController.start();
        entityManagerFactory.close();
    }
}

