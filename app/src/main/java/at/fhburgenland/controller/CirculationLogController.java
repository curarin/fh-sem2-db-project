package at.fhburgenland.controller;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.BookCirculationLog;
import at.fhburgenland.model.Customer;
import at.fhburgenland.model.repository.interfaces.BookRepository;
import at.fhburgenland.model.repository.interfaces.CirculationRepository;
import at.fhburgenland.model.repository.interfaces.CustomerRepository;
import at.fhburgenland.view.CirculationView;
import at.fhburgenland.view.CustomerView;
import at.fhburgenland.view.ViewUtil;

import java.util.List;

public class CirculationLogController {
    private final CirculationRepository circulationRepository;
    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;
    private final CirculationView circulationView;
    private final CustomerView customerView;

    public CirculationLogController(CirculationRepository circulationRepository, BookRepository bookRepository, CustomerRepository customerRepository, CirculationView circulationView, CustomerView customerView) {
        this.circulationRepository = circulationRepository;
        this.bookRepository = bookRepository;
        this.customerRepository = customerRepository;
        this.circulationView = circulationView;
        this.customerView = customerView;
    }

    public void start() {
        boolean running = true;

        while (running) {
            int choice = circulationView.showMainMenu();

            switch (choice) {
                case 1 -> {
                    // select customer
                    Customer customer = CustomerController.selectCustomer(customerView, customerRepository);

                    if (customer == null) {
                        ViewUtil.printMessage("Customer not found.");
                    } else {
                        // Check if customer already has 5 books
                        if (circulationRepository.findOpenBooksByCustomer(customer).size() >= 5) {
                            ViewUtil.printMessage("Customer has already borrowed 5 books and cannot borrow any more.");
                        } else {
                            String isbn = circulationView.getIsbnByUser();
                            Book book = bookRepository.findByIsbn(isbn);
                            if (book == null) {
                                ViewUtil.printMessage("Book not found.");
                            } else {
                                try {
                                    circulationRepository.borrowBook(customer, book);
                                    ViewUtil.printMessage("Book borrowed successfully.");
                                } catch (Exception e) {
                                    ViewUtil.printMessage("Error: " + e.getMessage());
                                }
                            }
                        }
                    }
                }
                case 2 -> {
                    // select customer
                    Customer customer = CustomerController.selectCustomer(customerView, customerRepository);

                    if (customer == null) {
                        continue;
                    }

                    // show borrowed books by customer
                    List<BookCirculationLog> openBooksByCustomer = circulationRepository.findOpenBooksByCustomer(customer);
                    circulationView.printBorrowedBooks(openBooksByCustomer);

                    if (openBooksByCustomer.isEmpty()) {
                        continue;
                    }


                    int logId = circulationView.getCirculationLogIdByUser();
                    if (logId <= 0) {
                        continue;
                    }

                    try {
                        circulationRepository.returnBook(logId);
                        ViewUtil.printMessage("Book returned successfully.");
                    } catch (Exception e) {
                        ViewUtil.printMessage("Error: " + e.getMessage());
                    }
                }
                case 0 -> running = false;
                default -> ViewUtil.printMessage("Invalid choice.");
            }
        }
    }


}
