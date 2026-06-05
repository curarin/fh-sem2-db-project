package at.fhburgenland;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.BookAuthor;
import at.fhburgenland.model.BookGenre;
import at.fhburgenland.model.BookPublisher;
import jakarta.persistence.*;

import java.util.Set;

public class Main {

    public static void main(String[] args) {
        System.out.println("Test");
        BookAuthor authorOne = new BookAuthor();
        authorOne.setBookAuthorName("Maria Jose");
        BookAuthor authorTwo = new BookAuthor();
        authorTwo.setBookAuthorName("Joseffy Pablito");
        String isbn = "123-456-7890-1a";
        String bookTitle = "Das absolut neue Buch";
        String bookGenreName = "Thiller";
        String bookPublisherName = "WildPublish";
        BookPublisher bookPublisher = new BookPublisher();
        bookPublisher.setBookPublisherName(bookPublisherName);
        BookGenre bookGenre = new BookGenre();
        bookGenre.setBookGenreName(bookGenreName);
        Book newBook = new Book(isbn, bookTitle, bookGenre, bookPublisher);
        newBook.setBookAuthors(Set.of(authorOne, authorTwo));

        BookManager.addBook(newBook, bookGenre, bookPublisher);
        BookManager.close();
        /* TO DO
            -) Connect Database
            -) Klasse zur Tabelle erstellen!
            -) Create Methods for
                -) addPerson
                -) readPerson
                -) readAllPersons --> Ausgabe ganze Tabelle
                -) update Person
                -) delete Person
         */
    }
}

