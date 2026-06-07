package at.fhburgenland;

import at.fhburgenland.controller.BookController;
import at.fhburgenland.controller.LibraryController;
import at.fhburgenland.model.Book;
import at.fhburgenland.model.BookAuthor;
import at.fhburgenland.model.repository.implementations.BookRepositoryImpl;
import at.fhburgenland.model.repository.interfaces.BookRepository;
import at.fhburgenland.view.BookView;
import at.fhburgenland.view.LibraryView;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("book");

    public static void main(String[] args) {
        LibraryController libraryController = new LibraryController(new LibraryView(), new BookController(new BookRepositoryImpl(entityManagerFactory), new BookView()));
        libraryController.start();
        entityManagerFactory.close();
    }
}

