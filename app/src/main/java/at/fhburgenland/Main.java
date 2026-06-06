package at.fhburgenland;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.BookAuthor;
import at.fhburgenland.model.repository.implementations.BookRepositoryImpl;
import at.fhburgenland.model.repository.interfaces.BookRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("book");

    public static void main(String[] args) {
        BookRepository bookRepository = new BookRepositoryImpl(entityManagerFactory);
        Book book = bookRepository.find("123-456-7890-1a");
        System.out.println("vVvVvVvVvVvVvVvVvVvVvVvVvVvVvVvVvVvVvVvVvVvVvVvV");
        System.out.printf(String.format("""
                Book Title: %s
                Book ISBN: %s
                Book Genre: %s
                Book Publisher: %s
                """, book.getBookTitle(), book.getIsbn(), book.getBookGenre().getBookGenreName(), book.getBookPublisher().getBookPublisherName()));

        int counter = 1;
        for (BookAuthor bookAuthor : book.getBookAuthors()) {
            System.out.printf(String.format("""
                    Book Author %d: %s
                    """, counter, bookAuthor.getBookAuthorName()));
            counter++;
        }
        System.out.println("vVvVvVvVvVvVvVvVvVvVvVvVvVvVvVvVvVvVvVvVvVvVvVvV");

        String newBookTitle = book.getBookTitle() + "| new Version: " + counter;
        System.out.println("newBookTitle: " + newBookTitle);
        book.setBookTitle(newBookTitle);
        System.out.println(book.getBookTitle());
        bookRepository.save(book);

        entityManagerFactory.close();
    }
}

