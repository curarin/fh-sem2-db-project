package at.fhburgenland;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.repository.interfaces.BookRepository;
import at.fhburgenland.model.repository.implementations.BookRepositoryImpl;
import jakarta.persistence.*;

import java.util.Set;

public class Main {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("book");

    public static void main(String[] args) {
        System.out.println("BUILD TEST 123456");
        BookRepository bookRepository = new BookRepositoryImpl(entityManagerFactory);
        Book book = bookRepository.find("123-456-7890-1a");
        System.out.println(book);
        System.out.println(String.format("""
                Book Title: %s
                Book ISBN: %s
                """, book.getBookTitle(), book.getIsbn()));

        entityManagerFactory.close();
        System.out.println("BUILD TEST 1234567");

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

