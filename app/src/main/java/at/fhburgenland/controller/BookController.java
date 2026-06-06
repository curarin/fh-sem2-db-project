package at.fhburgenland.controller;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.repository.interfaces.BookRepository;
import at.fhburgenland.view.BookView;

public class BookController {

    private final BookRepository repository;
    private final BookView view;

    public BookController(BookRepository repository, BookView view) {
        this.repository = repository;
        this.view = view;
    }

    public void start() {

        boolean running = true;

        while (running) {

            int choice = view.showMainMenu();

            switch (choice) {

                case 1 -> {
                    String isbn = view.askForIsbn();

                    Book book = repository.findByIsbn(isbn);

                    if (book == null) {
                        view.printMessage("Book not found.");
                    } else {
                        view.printBook(book);
                    }
                }

                case 2 -> {
                    String isbn = view.askForIsbn();
                    String title = view.askForTitle();

                    Book book = repository.findByIsbn(isbn);

                    if (book != null) {
                        book.setBookTitle(title);
                        repository.save(book);
                    }
                }

                case 3 -> running = false;
            }
        }
    }
}