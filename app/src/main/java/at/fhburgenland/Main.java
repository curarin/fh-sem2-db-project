package at.fhburgenland;

import at.fhburgenland.controller.BookController;
import at.fhburgenland.controller.EventController;
import at.fhburgenland.controller.LibraryController;
import at.fhburgenland.model.repository.implementations.BookRepositoryImpl;
import at.fhburgenland.model.repository.implementations.EventRepositoryImpl;
import at.fhburgenland.view.BookView;
import at.fhburgenland.view.EventView;
import at.fhburgenland.view.LibraryView;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("book");

    public static void main(String[] args) {
        BookController newBookController = new BookController(new BookRepositoryImpl(entityManagerFactory), new BookView());
        EventController newEventcontroller = new EventController(new EventRepositoryImpl(entityManagerFactory), new EventView());
        LibraryController libraryController = new LibraryController(new LibraryView(), newBookController, newEventcontroller);
        libraryController.start();
        entityManagerFactory.close();
    }
}

