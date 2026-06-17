package at.fhburgenland.view;

import at.fhburgenland.model.BookCirculationLog;

import java.util.List;
import java.util.Scanner;

public class CirculationView {
    private final Scanner scanner = new Scanner(System.in);

    public int showMainMenu() {
        System.out.println("""
                =====================================
                |   Book Circulation Management      |
                =====================================
                | (1) Borrow a Book                 |
                | (2) Return a Book                 |
                -------------------------------------
                | (0) Back                          |
                =====================================
                """);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public int getCustomerIdByUser() {
        System.out.println("""
                -------------------------------------
                |     Please enter Customer ID      |
                -------------------------------------
                """);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String getIsbnByUser() {
        System.out.println("""
                -------------------------------------
                |       Please enter ISBN           |
                -------------------------------------
                """);
        return scanner.nextLine();
    }

    public int getCirculationLogIdByUser() {
        System.out.println("""
                -------------------------------------
                |    Please enter Circulation ID    |
                -------------------------------------
                """);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void printBorrowedBooks(BookCirculationLog log) {
        System.out.println("ID: " + log.getBookCirculationLogId() + " | Book: " + log.getFkStockid().getBook().getBookTitle() + " | Loan Date: " + log.getLoanStartsAtDate());
    }

    public void printBorrowedBooks(List<BookCirculationLog> logs) {
        if (logs.isEmpty()) {
            System.out.println("No borrowed books found for this customer.");
        } else {
            System.out.println("Borrowed Books:");
            for (BookCirculationLog log : logs) {
                printBorrowedBooks(log);
            }
        }
    }
}
