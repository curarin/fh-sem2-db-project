package at.fhburgenland;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.BookAuthor;
import at.fhburgenland.model.BookGenre;
import at.fhburgenland.model.BookPublisher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.Arrays;

public class BookManager {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("book");

    public static void run() {
        System.out.println("This is a book test...");
    }

    public static void addBook(Book book, BookGenre bookGenre, BookPublisher bookPublisher) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = null;

        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.persist(bookPublisher);
            entityManager.persist(bookGenre);
            for (BookAuthor author : book.getBookAuthors()) {
                entityManager.persist(author);
            }
            entityManager.persist(book);
            entityTransaction.commit();
        } catch (Exception exception) {
            System.out.println(Arrays.toString(exception.getStackTrace()));
            if(entityTransaction != null) {
                entityTransaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    public static void close() {
        entityManagerFactory.close();
    }
}
