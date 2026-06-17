package at.fhburgenland.view;

import at.fhburgenland.model.*;
import at.fhburgenland.model.dto.CustomerAnalyticsDto;
import at.fhburgenland.model.dto.EventAnalyticsDto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class AnalyticsView {
    private final Scanner scanner = new Scanner(System.in);

    public int showMainMenu() {
        System.out.println("""
                =====================================
                |   ReiMi Library Management System |
                =====================================
                | (1) Analytics Query 1:            |
                -------------------------------------
                | Show all borrowed books for a     |
                | given customer incl. borrow date  |
                | within a defined time range       |
                =====================================
                | (2) Analytics Query 2:            |
                -------------------------------------
                | Show for each customer:           |
                |   - Count of borrowed books       |
                |   - Count of visited events       |
                |   - Count of total activities     |
                |                                   |
                | Customer has to be over           |
                | a user defined threshold          |
                =====================================
                | (3) Analytics Query 3:            |
                -------------------------------------
                | Show all physical existing books  |
                | for a given book incl.            |
                | its location                      |
                =====================================
                | (4) Analytics Query 4:            |
                -------------------------------------
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

    public int getTotalActivityThresholdByUser() {
        System.out.println("""
                -------------------------------------
                |       Please enter total          |
                |       activity threshold          |
                -------------------------------------
                """);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
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

    public void printTotalActivitiesByCustomer(List<CustomerAnalyticsDto> totalCustomerActivities) {
        for (CustomerAnalyticsDto customerAnalyticsDto : totalCustomerActivities) {
            String dtoPrint = String.format("""
                    -------------------------------------
                    |       Customer: %s %s
                    -------------------------------------
                    |   Total books at loan: %d
                    |   Total Events visited: %d
                    |   Total Activities: %d
                    -------------------------------------
                    """, customerAnalyticsDto.customer().getFirstName(), customerAnalyticsDto.customer().getLastName(), customerAnalyticsDto.countBooksAtLoan(), customerAnalyticsDto.countVisitedEvents(), customerAnalyticsDto.countTotalActivities());
            System.out.println(dtoPrint);
        }
    }

    public void printAllEventActivities(List<EventAnalyticsDto> dtos) {
        for (EventAnalyticsDto dto : dtos) {
            String bookPrint = String.format("""
                    -------------------------------------
                    |       Event found in system        |
                    -------------------------------------
                    | Event Title: %s
                    | Event Type: %s
                    | Event Starts at: %s
                    | Event ID: %s
                    | Participant Count: %d
                    -------------------------------------
                    """, dto.event().getEventName(), dto.event().getEventType().getEventTypeName(), dto.event().getEventStartsAtTs(), dto.event().getEventId(), dto.participantCount());
            System.out.println(bookPrint);
        }
    }

    public void printAllPhysicalCopiesOfWantedBook(List<BookStockLog> allPhysicalCopiesOfWantedBook) {
        int inStockCounter = 0;
        Set<BookLocation> foundLocations = new HashSet<>();

        for (BookStockLog bookStockLog : allPhysicalCopiesOfWantedBook) {
            if (bookStockLog.getBookIsInStock()) {
                inStockCounter++;
            }
        }
        String stockPrint = String.format("""
                ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                |       Total book copies           |
                |       currently in stock:         |
                |               %d                  |
                -------------------------------------
                |           Located at:             |
                """, inStockCounter);
        System.out.println(stockPrint);
        for (BookStockLog bookStockLog : allPhysicalCopiesOfWantedBook) {
            if (bookStockLog.getBookIsInStock()) {
                if (foundLocations.add(bookStockLog.getBookLocation())) {
                    String locatedAt = String.format("""
                            | Floor: %d                         |
                            | Shelf: %d                         |
                            """, bookStockLog.getBookLocation().getBookLocationFloor().getBookLocationFloorNumber(), bookStockLog.getBookLocation().getBookLocationShelf().getBookLocationShelfNumber());
                    System.out.print(locatedAt);
                }

            }
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
}
