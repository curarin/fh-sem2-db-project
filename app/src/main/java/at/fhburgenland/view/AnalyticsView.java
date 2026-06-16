package at.fhburgenland.view;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.BookAuthor;
import at.fhburgenland.model.Customer;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AnalyticsView {
    private final Scanner scanner = new Scanner(System.in);

    public void printMessage(String message) {
        System.out.println(message);
    }

    public Integer showMainMenu() {
        System.out.println("""
                =====================================
                |   ReiMi Library Management System |
                =====================================
                | (1) Analytics Query 1:            |
                    -   -   -   -   -   -   -   -
                | Show all borrowed books for a     |
                | given customer incl. borrow date  |
                | within a defined time range       |
                -------------------------------------
                | (2) Analytics Query 2:            |
                    -   -   -   -   -   -   -   -
                | Show for each customer:           |
                |   - Count of borrowed books       |
                |   - Count of visited events       |
                |   - Count of total activities     |
                |                                   |
                | Customer has to be over           |
                | a user defined threshold          |
                -------------------------------------
                | (3) Analytics Query 3:            |
                    -   -   -   -   -   -   -   -
                | Show all physical existing books  |
                | for a given book incl.            |
                | its location                      |
                -------------------------------------
                | (4) Analytics Query 4:            |
                    -   -   -   -   -   -   -   -
                | Show the attendant count for      |
                | each event and show only those    |
                | which have above average          |
                | attendant count                   |
                -------------------------------------
                | (0) Main Menu                     |
                =====================================
                """);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String getStartDateByUser() {
        System.out.println("""
                -------------------------------------
                |  Please enter Start Date          |
                |  in this format: YYYY-mm-dd       |
                |e.g. "2026-06-01" for 1. June 2026 |
                -------------------------------------
                """);
        return scanner.nextLine();
    }

    public String getEndtDateByUser() {
        System.out.println("""
                -------------------------------------
                |  Please enter End Date            |
                |  in this format: YYYY-mm-dd       |
                |e.g. "2026-06-01" for 1. June 2026 |
                -------------------------------------
                """);
        return scanner.nextLine();
    }

    public void printBooksAtLoanByCustomer(Customer selectedCustomer, List<Book> selectedBooks, LocalDate startDate, LocalDate endDate) {
        String bookPrint = String.format("""
                -------------------------------------
                |       Books for: %s %s
                -------------------------------------
                | Date Range Start: %s
                | Date Range End: %s
                -------------------------------------
                """, selectedCustomer.getFirstName(), selectedCustomer.getLastName(), startDate, endDate);
        System.out.println(bookPrint);
        for (Book book : selectedBooks) {
            String authors = book.getBookAuthors().stream().map(BookAuthor::getBookAuthorName).collect(Collectors.joining(", "));
            String specificPrint = String.format("""
                    | Title: %s
                    | Authors: %s
                    | Book Isbn: %s
                    """, book.getBookTitle(), authors, book.getIsbn());
            System.out.println(specificPrint);
        }
    }
}
