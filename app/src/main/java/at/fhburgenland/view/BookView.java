package at.fhburgenland.view;

import at.fhburgenland.model.Book;

import java.util.List;
import java.util.Scanner;

public class BookView {

    private final Scanner scanner = new Scanner(System.in);

    public void printMessage(String message) {
        System.out.println(message);
    }

    public String askForIsbn() {
        System.out.print("ISBN: ");
        return scanner.nextLine();
    }

    public String askForTitle() {
        System.out.print("Title: ");
        return scanner.nextLine();
    }

    public int showMainMenu() {
        System.out.println("""
                1 - Find book by ISBN
                2 - Update title
                3 - Exit
                """);

        return Integer.parseInt(scanner.nextLine());
    }

    public void printBook(Book book) {
        System.out.println(book);
    }
}