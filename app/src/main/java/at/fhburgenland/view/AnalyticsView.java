package at.fhburgenland.view;

import java.util.Scanner;

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
}
