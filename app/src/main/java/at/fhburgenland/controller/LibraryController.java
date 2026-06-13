package at.fhburgenland.controller;

import at.fhburgenland.view.LibraryView;

/**
 * Controller representation of library - maps user input from view to further controlling classes.
 * Acts as main entry point.
 */
public class LibraryController {
    private final LibraryView view;
    private final BookController bookController;
    private final EventController eventController;
    private final CustomerController customerController;

    public LibraryController(LibraryView view, BookController bookController,
                             EventController eventController, CustomerController customerController) {
        this.view = view;
        this.bookController = bookController;
        this.eventController = eventController;
        this.customerController = customerController;
    }

    public void start() {
        boolean running = true;

        while (running) {
            int choice = view.showMenu();

            switch (choice) {
                case 1 -> {
                    bookController.start();
                }
                case 2 -> {
                    eventController.start();
                }
                case 3 -> {
                    customerController.start();
                }
                case 0 -> {
                    running = false;
                }
                default -> {
                    System.out.println("Invalid choice");
                }
            }
        }
    }
}
