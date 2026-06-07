package at.fhburgenland.model.repository;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.BookAuthor;
import at.fhburgenland.model.BookGenre;
import at.fhburgenland.model.BookPublisher;
import at.fhburgenland.model.repository.implementations.BookRepositoryImpl;
import at.fhburgenland.model.repository.interfaces.BookRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

public class BookRepositoryImplTest {
    private static EntityManagerFactory entityManagerFactory;
    private static BookRepository bookRepository;

    @BeforeAll
    public static void setupEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("book-unit-test");
        bookRepository = new BookRepositoryImpl(entityManagerFactory);
    }

    @AfterAll
    public static void tearDownEntityManagerFactory() {
        entityManagerFactory.close();
    }

    private Book createStandardBook(String isbn) {
        int randomNumber = ThreadLocalRandom.current().nextInt();
        String authorName = "Standard Author".concat(String.valueOf(randomNumber));
        BookAuthor author = new BookAuthor();
        author.setBookAuthorName(authorName);

        BookGenre genre = new BookGenre();
        String genreName = "Standard Genre".concat(String.valueOf(randomNumber));
        genre.setBookGenreName(genreName);

        BookPublisher publisher = new BookPublisher();
        String publisherName = "Standard Publisher".concat(String.valueOf(randomNumber));
        publisher.setBookPublisherName(publisherName);

        Book book = new Book();
        if (isbn != null) {
            book.setIsbn(isbn);
        } else {
            book.setIsbn(String.valueOf(randomNumber));
        }
        book.setBookTitle("Standard Book Title");
        book.setBookGenre(genre);
        book.setBookPublisher(publisher);
        book.setBookAuthors(Set.of(author));

        return book;
    }

    @Test
    public void saveNewBook() {
        Book newBook = createStandardBook("123");
        bookRepository.save(newBook);
        assertNotNull(bookRepository.findByIsbn("123"));
    }

    @Test
    public void updateBookWithNewData() {
        Book newBook = createStandardBook("123");
        bookRepository.save(newBook);
        assertNotNull(bookRepository.findByIsbn("123"));
        assertEquals("Standard Book Title", bookRepository.findByIsbn("123").getBookTitle());

        newBook.setBookTitle("New Book Title");
        bookRepository.save(newBook);
        assertEquals("New Book Title", bookRepository.findByIsbn("123").getBookTitle());
    }

    @Test
    public void removeBookFromDatabase() {
        Book book = createStandardBook("123");
        bookRepository.save(book);
        assertNotNull(bookRepository.findByIsbn("123"));

        bookRepository.remove("123");
        assertNull(bookRepository.findByIsbn("123"));
        assertEquals(Collections.EMPTY_LIST, bookRepository.findByBookName(book.getBookTitle()));
        assertEquals(Collections.EMPTY_LIST, bookRepository.findByAuthor(book.getBookAuthors().toString()));
        assertEquals(Collections.EMPTY_LIST, bookRepository.findByGenre(book.getBookGenre().toString()));
    }

    @Test
    public void findBookByAuthor() {
        Book book1 = createStandardBook("123");
        Book book2 = createStandardBook("456");
        Book book3 = createStandardBook("789");

        BookAuthor mainAuthorToTest = new BookAuthor();
        mainAuthorToTest.setBookAuthorName("Main Author");
        book1.setBookAuthors(Set.of(mainAuthorToTest));
        book2.setBookAuthors(Set.of(mainAuthorToTest));
        book3.setBookAuthors(Set.of(mainAuthorToTest));
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);

        assertFalse(bookRepository.findByAuthor(mainAuthorToTest.getBookAuthorName()).isEmpty());
        assertEquals(3, bookRepository.findByAuthor(mainAuthorToTest.getBookAuthorName()).size());
    }

    @Test
    public void findBookByGenre() {
        Book book1 = createStandardBook("123");
        Book book2 = createStandardBook("456");
        Book book3 = createStandardBook("789");

        BookGenre mainGenreToTest = new BookGenre();
        mainGenreToTest.setBookGenreName("Main Genre");

        book1.setBookGenre(mainGenreToTest);
        book2.setBookGenre(mainGenreToTest);
        book3.setBookGenre(mainGenreToTest);
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);

        assertFalse(bookRepository.findByGenre(mainGenreToTest.getBookGenreName()).isEmpty());
        assertEquals(3, bookRepository.findByGenre(mainGenreToTest.getBookGenreName()).size());
    }

    @Test
    public void findBooksByPublisher() {
        Book book1 = createStandardBook("123");
        Book book2 = createStandardBook("456");
        Book book3 = createStandardBook("789");

        BookPublisher mainPublisherToTest = new BookPublisher();
        mainPublisherToTest.setBookPublisherName("Main Publisher");

        book1.setBookPublisher(mainPublisherToTest);
        book2.setBookPublisher(mainPublisherToTest);
        book3.setBookPublisher(mainPublisherToTest);

        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);

        assertFalse(bookRepository.findByPublisher(mainPublisherToTest.getBookPublisherName()).isEmpty());
        assertEquals(3, bookRepository.findByPublisher(mainPublisherToTest.getBookPublisherName()).size());
    }
}
