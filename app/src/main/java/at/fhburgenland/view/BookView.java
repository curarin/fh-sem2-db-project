package at.fhburgenland.view;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.BookAuthor;

import java.util.List;
import java.util.Scanner;

public class BookView {

    private final Scanner scanner = new Scanner(System.in);

    public void printMessage(String message) {
        System.out.println(message);
    }

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
                printMessage("Please enter a valid choice");
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

        return Integer.parseInt(scanner.nextLine());
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
                -------------------------------------
                | (0) Back                          |
                -------------------------------------
                """);
        return Integer.parseInt(scanner.nextLine());
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

    public void printBook(Book book) {
        String bookPrint = String.format("""
                -------------------------------------
                |       Book found in system        |
                -------------------------------------
                | Book Title: %s
                | Book Book Genre: %s
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
}