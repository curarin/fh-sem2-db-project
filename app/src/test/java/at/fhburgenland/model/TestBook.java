package at.fhburgenland.model;

import at.fhburgenland.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestBook {
    @Test
    void shouldPersistBookWithAuthorGenreAndPublisher() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test-pu");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        BookGenre genre = new BookGenre();
        genre.setBookGenreName("Fantasy");

        BookPublisher publisher = new BookPublisher();
        publisher.setBookPublisherName("Penguin");

        BookAuthor author = new BookAuthor();
        author.setBookAuthorName("J. R. R. Tolkien");

        em.persist(genre);
        em.persist(publisher);
        em.persist(author);

        Book book = new Book(
                "9780007525492",
                "The Hobbit",
                genre,
                publisher
        );

        book.setBookAuthors(Set.of(author));

        em.persist(book);

        em.getTransaction().commit();

        em.clear();

        Book savedBook = em.find(Book.class, "9780007525492");

        assertNotNull(savedBook);
        assertEquals("The Hobbit", savedBook.getBookTitle());
        assertEquals("Fantasy", savedBook.getBookGenre().getBookGenreName());
        assertEquals("Penguin", savedBook.getBookPublisher().getBookPublisherName());
        assertEquals(1, savedBook.getBookAuthors().size());

        em.close();
        emf.close();
    }
}

