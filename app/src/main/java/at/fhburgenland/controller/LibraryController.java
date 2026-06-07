package at.fhburgenland.controller;

import at.fhburgenland.view.LibraryView;

public class LibraryController {
    private final LibraryView view;
    private final BookController bookController;

    public LibraryController(LibraryView view, BookController bookController) {
        this.view = view;
        this.bookController = bookController;
    }

    public void start() {
        boolean running = true;

        while (running) {
            int choice = view.showMenu();

            switch (choice) {
                case 1 -> {
                    bookController.start();
                }
                case 0  -> {
                    running = false;
                }
                default -> {
                    System.out.println("Invalid choice");
                }
            }
        }
    }
}
