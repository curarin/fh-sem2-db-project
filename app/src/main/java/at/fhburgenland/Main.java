package at.fhburgenland;

import at.fhburgenland.controller.BookController;
import at.fhburgenland.controller.CustomerController;
import at.fhburgenland.controller.EventController;
import at.fhburgenland.controller.LibraryController;
import at.fhburgenland.model.repository.implementations.BookRepositoryImpl;
import at.fhburgenland.model.repository.implementations.CustomerRepositoryImpl;
import at.fhburgenland.model.repository.implementations.EventRepositoryImpl;
import at.fhburgenland.view.BookView;
import at.fhburgenland.view.CustomerView;
import at.fhburgenland.view.EventView;
import at.fhburgenland.view.LibraryView;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("book");

    public static void main(String[] args) {
        BookController bookController = new BookController(new BookRepositoryImpl(entityManagerFactory), new BookView());
        EventController eventController = new EventController(new EventRepositoryImpl(entityManagerFactory), new EventView());
        CustomerController customerController = new CustomerController(new CustomerRepositoryImpl(entityManagerFactory), new CustomerView());
        LibraryController libraryController = new LibraryController(new LibraryView(), bookController, eventController, customerController);
        libraryController.start();
        entityManagerFactory.close();
    }
}

