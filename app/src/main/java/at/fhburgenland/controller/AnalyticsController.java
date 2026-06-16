package at.fhburgenland.controller;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.BookStockLog;
import at.fhburgenland.model.Customer;
import at.fhburgenland.model.repository.interfaces.AnalyticsRepository;
import at.fhburgenland.model.repository.interfaces.BookRepository;
import at.fhburgenland.model.repository.interfaces.CustomerRepository;
import at.fhburgenland.view.AnalyticsView;
import at.fhburgenland.view.BookView;
import at.fhburgenland.view.CustomerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller representation of analytics domain - maps user input from view to repository methods
 */
public class AnalyticsController {
    private final AnalyticsRepository analyticsRepository;
    private final CustomerRepository customerRepository;
    private final BookRepository bookRepository;
    private final AnalyticsView analyticsView;
    private final BookView bookView;


    public AnalyticsController(AnalyticsView analyticsView, BookView bookView, AnalyticsRepository analyticsRepository, CustomerRepository customerRepository, BookRepository bookRepository) {
        this.analyticsRepository = analyticsRepository;
        this.customerRepository = customerRepository;
        this.bookRepository = bookRepository;
        this.analyticsView = analyticsView;
        this.bookView = bookView;
    }

    /**
     * Run the analytics controller layer inside a loop
     */
    public void start() {
        boolean running = true;

        while (running) {

            switch (analyticsView.showMainMenu()) {
                // Geben Sie alle von einem bestimmten Kunden ausgeliehenen Bücher inklusive
                // Ausleihdatum zurück, die in einer gewissen Zeitspanne ausgeliehen wurden.
                case 1 -> {
                    List<Book> foundBooks = new ArrayList<>();
                    Customer selectedCustomer = CustomerController.selectCustomer(new CustomerView(), customerRepository);
                    if (selectedCustomer != null) {
                        String startDateInput = analyticsView.getStartDateByUser();
                        LocalDate startDate = LocalDate.parse(startDateInput);
                        String endDateInput = analyticsView.getEndtDateByUser();
                        LocalDate endDate = LocalDate.parse(endDateInput);
                        foundBooks = analyticsRepository.getBooksLoanedByCustomer(selectedCustomer.getCustomerId(), startDate, endDate);
                        analyticsView.printBooksAtLoanByCustomer(selectedCustomer, foundBooks, startDate, endDate);
                    } else {
                        System.err.println("Customer not found");
                    }
                }
                // Ermitteln Sie für jeden Kunden die Anzahl ausgeliehener Bücher, die Anzahl besuchter
                // Veranstaltungen und deren Gesamtaktivität (= Summe beider Werte). Geben Sie nur Kunden aus,
                // deren Gesamtaktivität über einem vorgegebenen Wert liegen.
                case 2 -> {
                    System.out.println("This is to be...");
                    // Get a specific total activity threshold

                }
                // Geben Sie alle verfügbaren Exemplare eines bestimmten Buches
                // inklusive Standort (Regal und Stockwerk) aus.
                case 3 -> {
                    switch (bookView.showExistingBookMenu()) {
                        case 1 -> {
                            String isbn = bookView.getIsbnByUser();
                            Book foundBook = bookRepository.findByIsbn(isbn);
                            if (foundBook != null) {
                                bookView.printBook(foundBook);
                                bookView.printStockStatistics(bookRepository.findStockByIsbn(isbn));
                            }
                        }
                        case 2 -> {
                            List<Book> booksByTitle = bookRepository.findByBookName(bookView.getBookTitleByUser());
                            for (Book book : booksByTitle) {
                                if (book != null) {
                                    bookView.printBook(book);
                                }
                            }
                            bookView.printSearchStatistics(booksByTitle);
                        }
                        case 3 -> {
                            List<Book> booksByGenre = bookRepository.findByGenre(bookView.getBookGenreByUser());
                            for (Book book : booksByGenre) {
                                if (book != null) {
                                    bookView.printBook(book);
                                }
                            }
                            bookView.printSearchStatistics(booksByGenre);
                        }
                        case 4 -> {
                            List<Book> booksByPublisher = bookRepository.findByPublisher(bookView.getBookPublisherByUser());
                            for (Book book : booksByPublisher) {
                                if (book != null) {
                                    bookView.printBook(book);
                                }
                            }
                            bookView.printSearchStatistics(booksByPublisher);
                        }
                        case 5 -> {
                            List<Book> booksByAuthor = bookRepository.findByAuthor(bookView.getBookAuthorByUser());
                            for (Book book : booksByAuthor) {
                                if (book != null) {
                                    bookView.printBook(book);
                                }
                            }
                            bookView.printSearchStatistics(booksByAuthor);
                        }
                        case 6 -> {
                            // Filter by Stock State true / false
                            List<Book> booksByStockLockState = bookRepository.findByStockState(bookView.getBookStockStateByUser());
                            for (Book book : booksByStockLockState) {
                                if (book != null) {
                                    bookView.printBook(book);
                                }
                            }
                            bookView.printSearchStatistics(booksByStockLockState);
                        }
                        case 0 -> running = false;
                    }
                    Book wantedBook = bookRepository.findByIsbn(bookView.getIsbnByUser());
                    List<BookStockLog> allPhysicalCopiesOfWantedBook = analyticsRepository.getBookStockLogByBookIsbn(wantedBook.getIsbn());
                    analyticsView.printAllPhysicalCopiesOfWantedBook(allPhysicalCopiesOfWantedBook);
                }
                // Ermitteln Sie für jede Veranstaltung die Anzahl der Teilnehmer und geben Sie nur Veranstaltungen aus,
                // die mehr Teilnehmer als der Durchschnitt aller Veranstaltungen haben.
                case 4 -> {
                    System.out.println("...soon!");

                }
                case 0 -> running = false;
            }
        }
    }
}
