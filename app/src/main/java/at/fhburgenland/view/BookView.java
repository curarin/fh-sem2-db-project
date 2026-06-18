package at.fhburgenland.view;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.BookAuthor;
import at.fhburgenland.model.BookLocation;
import at.fhburgenland.model.BookStockLog;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Handles views related to book stuff - e.g. prompt user for ISBN, Author, Book Title,...
 * Passes the value down to a controller layer.
 */
public class BookView {

    private final Scanner scanner = new Scanner(System.in);

    public String getIsbnByUser() {
        System.out.println("""
                -------------------------------------
                |       Please enter ISBN           |
                -------------------------------------
                """);
        return scanner.nextLine();
    }

    public String getBookTitleByUser() {
        System.out.println("""
                -------------------------------------
                |     Please enter Book Title       |
                -------------------------------------
                """);
        return scanner.nextLine();
    }

    public String getBookGenreByUser() {
        System.out.println("""
                -------------------------------------
                |     Please enter Book Genre       |
                -------------------------------------
                """);
        return scanner.nextLine();
    }

    public int getBookLocationFloorByUser() {
        System.out.println("""
                -------------------------------------
                |       On which floor is           |
                |        the book located?          |
                |   enter number - e.g. "5"         |
                -------------------------------------
                """);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public int getBookLocationShelfByUser() {
        System.out.println("""
                -------------------------------------
                |       In which shelf is           |
                |        the book located?          |
                |   enter number - e.g. "5"         |
                -------------------------------------
                """);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String getBookPublisherByUser() {
        System.out.println("""
                -------------------------------------
                |    Please enter Book Publisher    |
                -------------------------------------
                """);
        return scanner.nextLine();
    }

    public String getBookAuthorByUser() {
        System.out.println("""
                -------------------------------------
                |     Please enter Book Author      |
                -------------------------------------
                """);
        return scanner.nextLine();
    }

    public boolean getBookStockStateByUser() {
        System.out.println("""
                -------------------------------------
                |  Shall the book be in stock?      |
                -------------------------------------
                | (1) Yes                           |
                | (2) No                            |
                -------------------------------------
                """);
        switch (scanner.nextLine()) {
            case "1" -> {
                return true;
            }
            case "2" -> {
                return false;
            }
            default -> {
                ViewUtil.printMessage("Please enter a valid choice");
                return false;
            }
        }
    }

    public int getBookCountByUser(Integer floorNumber, Integer shelfNumber) {
        String output = String.format("""
                -------------------------------------
                |   How many physical book copies   |
                |  are located at chosen location:  |
                |                                   |
                |   Floor: %d                       |
                |   Shelf: %d                       |
                |                                   |
                |       Please enter the amount     |
                |           of book copies          |
                -------------------------------------
                """, floorNumber, shelfNumber);
        System.out.println(output);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public boolean getBookAuthorChoiceByUser() {
        System.out.println("""
                -------------------------------------
                |  Another author for the book?     |
                -------------------------------------
                | (1) Yes                           |
                | (2) No                            |
                -------------------------------------
                """);
        switch (scanner.nextLine()) {
            case "1" -> {
                return true;
            }
            case "2" -> {
                return false;
            }
            default -> {
                ViewUtil.printMessage("Please enter a valid choice");
                return false;
            }
        }
    }

    public int showMainMenu() {
        System.out.println("""
                =====================================
                |   ReiMi Library Management System |
                =====================================
                | (1) Show existing Book            |
                | (2) Add new Book                  |
                | (3) Edit existing Book            |
                | (4) Delete Book                   |
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

    public int showExistingBookMenu() {
        System.out.println("""
                -------------------------------------
                |       Choose filter option        |
                -------------------------------------
                | (1) Show by ISBN                  |
                | (2) Search by Title               |
                | (3) Search by Genre               |
                | (4) Search by Publisher           |
                | (5) Search by Author              |
                | (6) Search by Stock State         |
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

    public int showEditOptionsMenu() {
        System.out.println("""
                -------------------------------------
                |  What would you like to edit?     |
                -------------------------------------
                | (1) Title                         |
                | (2) Genre                         |
                | (3) Author                        |
                | (4) Publisher                     |
                -------------------------------------
                | (0) Back                          |
                -------------------------------------
                """);
        return Integer.parseInt(scanner.nextLine());
    }

    public void printSearchStatistics(List<Book> books) {
        String bookPrint = String.format("""
                ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                | Total Books found: %d             |
                ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                """, books.size());
        System.out.println(bookPrint);
    }

    public void printStockStatistics(List<BookStockLog> stock) {
        int inStockCounter = 0;
        Set<BookLocation> foundLocations = new HashSet<>();

        for (BookStockLog bookStockLog : stock) {
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
        for (BookStockLog bookStockLog : stock) {
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

    public void printBook(Book book) {
        String bookPrint = String.format("""
                -------------------------------------
                |       Book found in system        |
                -------------------------------------
                | Book Title: %s                    
                | Book Genre: %s                    
                | Book Publisher: %s                
                | Book ISBN: %s                     
                """, book.getBookTitle(), book.getBookGenre().getBookGenreName(), book.getBookPublisher().getBookPublisherName(), book.getIsbn());
        System.out.println(bookPrint);
        int authorCounter = 1;
        for (BookAuthor bookAuthor : book.getBookAuthors()) {
            System.out.printf("| Author (%d): %s\n", authorCounter, bookAuthor.getBookAuthorName());
            authorCounter++;
        }
        System.out.print("-------------------------------------\n");
    }

    public void printRemovalError(String reason) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("|   Removal not possible!           |");
        System.out.println("|   Reason:                         |");
        System.out.printf("|   %s\n", reason);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    public boolean getRemoveStockChoiceByUser() {
        System.out.println("""
                -------------------------------------
                |  Do you want to remove the stock? |
                -------------------------------------
                | (1) Yes                           |
                | (2) No                            |
                -------------------------------------
                """);
        String choice = scanner.nextLine();
        return "1".equals(choice);
    }
}