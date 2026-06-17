package at.fhburgenland.view;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.Customer;
import at.fhburgenland.model.Event;

import java.util.List;
import java.util.Scanner;

public class EventView {
    private final Scanner scanner = new Scanner(System.in);

    public int showMainMenu() {
        System.out.println("""
                =====================================
                |   ReiMi Library Management System |
                =====================================
                | (1) Show existing Event           |
                | (2) Add new Event                 |
                | (3) Edit existing Event           |
                | (4) Delete Event                  |
                |--- Customer Event Management -----|
                | (5) Add Customers to Event        |
                | (6) Show Customers visiting Event |
                | (7) Remove Customers from Event   |
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

    public String getEventNameByUser() {
        System.out.println("""
                -------------------------------------
                |     Please enter Event Title      |
                -------------------------------------
                """);
        return scanner.nextLine();
    }

    public boolean getUserChoiceForBookAddition() {
        System.out.println("""
                -------------------------------------
                |  Add books to current event?      |
                -------------------------------------
                | (1) Yes                           |
                | (2) No                            |
                -------------------------------------
                """);
        return scanner.nextLine().equals("1");
    }

    public boolean getUserChoiceForCustomerAddition() {
        System.out.println("""
                -------------------------------------
                |  Add another customers            |
                        to current event?           |
                -------------------------------------
                | (1) Yes                           |
                | (2) No                            |
                -------------------------------------
                """);
        return scanner.nextLine().equals("1");
    }

    public String getEventTypeNameByUser() {
        System.out.println("""
                -------------------------------------
                |   Please enter Event Type Name    |
                -------------------------------------
                """);
        return scanner.nextLine();
    }

    public int showEditOptionsMenu() {
        System.out.println("""
                -------------------------------------
                |  What would you like to edit?     |
                -------------------------------------
                | (1) Event Name                    |
                | (2) Event Type                    |
                | (3) Event Start Date              |
                -------------------------------------
                | (0) Back                          |
                -------------------------------------
                """);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }

    }

    public String getEventStartDateByUser() {
        System.out.println("""
                -------------------------------------
                |  Please enter Event Start Date    |
                |  in this format: YYYY-mm-dd       |
                |e.g. "2026-06-01" for 1. June 2026 |
                -------------------------------------
                """);
        return scanner.nextLine();
    }

    public String getEventStartHourByUser() {
        System.out.println("""
                -------------------------------------
                |  Please enter Event Start Hour    |
                |  in this format: 23               |
                |  "23" when the Event starts       |
                |  at 23 o'clock                    |
                -------------------------------------
                """);
        return scanner.nextLine();
    }

    public String getEventStartMinuteByUser() {
        System.out.println("""
                -------------------------------------
                |  Please enter Event Start Hour    |
                |  in this format: 58               |
                |  "58" when the Event starts       |
                |  at the 58th minute               |
                -------------------------------------
                """);
        return scanner.nextLine();
    }

    public int getEventIdByUser() {
        System.out.println("""
                -------------------------------------
                |     Please enter Event ID         |
                -------------------------------------
                """);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public int chooseEventToAddUser() {
        System.out.println("""
                -------------------------------------
                |         Choose Event by ID        |
                |     to add Customers to Event     |
                -------------------------------------
                """);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public int chooseEventToRemoveUser() {
        System.out.println("""
                -------------------------------------
                |         Choose Event by ID        |
                |   to remove Customers from Event  |
                -------------------------------------
                """);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public boolean getUserChoiceForCustomerRemoval() {
        System.out.println("""
                -------------------------------------
                | Remove another customers          |
                |       from current event?         |
                -------------------------------------
                | (1) Yes                           |
                | (2) No                            |
                -------------------------------------
                """);
        return scanner.nextLine().equals("1");
    }

    public int showExistingEventMenu() {
        System.out.println("""
                -------------------------------------
                |       Choose filter option        |
                -------------------------------------
                | (1) Search by Title               |
                | (2) Search by ID                  |
                | (3) Search by Event Type          |
                | (4) Show all                      |
                -------------------------------------
                | (0) Back                          |
                -------------------------------------
                """);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void printEvent(Event event) {
        String bookPrint = String.format("""
                -------------------------------------
                |       Event found in system        |
                -------------------------------------
                | Event Title: %s
                | Event Type: %s
                | Event Starts at: %s
                | Event ID: %s
                """, event.getEventName(), event.getEventType().getEventTypeName(), event.getEventStartsAtTs(), event.getEventId());
        System.out.println(bookPrint);
        int bookCounter = 1;
        for (Book book : event.getBooks()) {
            System.out.printf("| Book (%d): %s (Genre: %s)\n", bookCounter, book.getBookTitle(), book.getBookGenre().getBookGenreName());
            bookCounter++;
        }
        System.out.print("-------------------------------------\n");
    }

    /**
     * Prints a grid of all events in the system
     * @param events List of all events
     */
    public void printEventGrid(List<Event> events) {
        System.out.print("""
                -------------------------------------
                |      Overview of all Events:      |
                -------------------------------------""");
        
        System.out.println("");
        System.out.println("|        ID : Name");
        for (Event event : events) {
            System.out.printf("| Event  %d : %s\n", event.getEventId(), event.getEventName());
        }
        System.out.print("-------------------------------------\n");
    }

    /**
     * Prints a grid of all events in the system
     * @param customers List of all customers
     */
    public void printCustomerVisitingEventGrid(List<Customer> customers, String eventName) {
        System.out.printf("""
                -------------------------------------
                |      Overview of all Customer     |
                |                visiting:          |
                | %s
                -------------------------------------""", eventName);

        System.out.println("");
        System.out.println("| ID : Name");
        for (Customer customer: customers) {
            System.out.printf("| %d : %s %s\n", customer.getCustomerId(),
                    customer.getFirstName(), customer.getLastName());
        }
        System.out.print("-------------------------------------\n");
    }



}
