package at.fhburgenland.controller;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.Customer;
import at.fhburgenland.model.repository.interfaces.BookRepository;
import at.fhburgenland.model.repository.interfaces.CirculationRepository;
import at.fhburgenland.model.repository.interfaces.CustomerRepository;
import at.fhburgenland.view.CirculationView;

public class CirculationLogController {
    private final CirculationRepository circulationRepository;
    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;
    private final CirculationView circulationView;

    public CirculationLogController(CirculationRepository circulationRepository, BookRepository bookRepository, CustomerRepository customerRepository, CirculationView circulationView) {
        this.circulationRepository = circulationRepository;
        this.bookRepository = bookRepository;
        this.customerRepository = customerRepository;
        this.circulationView = circulationView;
    }

    public void start() {
        boolean running = true;

        while (running) {
            int choice = circulationView.showMainMenu();

            switch (choice) {
                case 1 -> {
                    int customerId = circulationView.getCustomerIdByUser();
                    Customer customer = customerRepository.findById(customerId);
                    if (customer == null) {
                        circulationView.printMessage("Customer not found.");
                    } else {
                        String isbn = circulationView.getIsbnByUser();
                        Book book = bookRepository.findByIsbn(isbn);
                        if (book == null) {
                            circulationView.printMessage("Book not found.");
                        } else {
                            try {
                                circulationRepository.borrowBook(customer, book);
                                circulationView.printMessage("Book borrowed successfully.");
                            } catch (Exception e) {
                                circulationView.printMessage("Error: " + e.getMessage());
                            }
                        }
                    }
                }
                case 2 -> {
                    int logId = circulationView.getCirculationLogIdByUser();
                    try {
                        circulationRepository.returnBook(logId);
                        circulationView.printMessage("Book returned successfully.");
                    } catch (Exception e) {
                        circulationView.printMessage("Error: " + e.getMessage());
                    }
                }
                case 0 -> running = false;
                default -> circulationView.printMessage("Invalid choice.");
            }
        }
    }
}
