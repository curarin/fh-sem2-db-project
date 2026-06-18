package at.fhburgenland.model.repository;

import at.fhburgenland.model.*;
import at.fhburgenland.model.repository.implementations.BookRepositoryImpl;
import at.fhburgenland.model.repository.implementations.CustomerRepositoryImpl;
import at.fhburgenland.model.repository.interfaces.BookRepository;
import at.fhburgenland.model.repository.interfaces.CustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    public void cleanDatabase() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.createQuery("delete from CustomerEventMap").executeUpdate();
        entityManager.createQuery("delete from BookCirculationLog").executeUpdate();
        entityManager.createQuery("delete from Event").executeUpdate();
        entityManager.createQuery("delete from Customer").executeUpdate();
        entityManager.createQuery("delete from BookStockLog").executeUpdate();
        entityManager.createQuery("delete from Book").executeUpdate();
        entityManager.createQuery("delete from BookLocation ").executeUpdate();
        entityManager.createQuery("delete from BookLocationFloor").executeUpdate();
        entityManager.createQuery("delete from BookLocationShelf ").executeUpdate();
        entityManager.createQuery("delete from BookPublisher").executeUpdate();
        entityManager.createQuery("delete from BookGenre").executeUpdate();
        entityManager.createQuery("delete from BookAuthor").executeUpdate();

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private Book createStandardBook(String isbn) {
        int randomNumber = ThreadLocalRandom.current().nextInt();
        String authorName = "Standard Author".concat(String.valueOf(randomNumber));
        BookAuthor author = new BookAuthor();
        author.setBookAuthorName(authorName);

        BookStockLog stockLog = new BookStockLog();
        stockLog.setBookIsInStock(true);

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
        Book newBook = createStandardBook("12355");
        bookRepository.save(newBook);
        assertNotNull(bookRepository.findByIsbn("12355"));
    }

    @Test
    public void updateBookWithNewData() {
        Book newBook = createStandardBook("12344");
        bookRepository.save(newBook);
        assertNotNull(bookRepository.findByIsbn("12344"));
        assertEquals("Standard Book Title", bookRepository.findByIsbn("12344").getBookTitle());

        newBook.setBookTitle("New Book Title");
        bookRepository.save(newBook);
        assertEquals("New Book Title", bookRepository.findByIsbn("12344").getBookTitle());
    }


    @Test
    public void removeBookFromDatabase() {
        Book book = createStandardBook("123");
        book.setBookTitle("Book which is about to be removed");
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
        Book book1 = createStandardBook("1231");
        Book book2 = createStandardBook("4561");
        Book book3 = createStandardBook("7891");

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
        Book book1 = createStandardBook("12311");
        Book book2 = createStandardBook("45622");
        Book book3 = createStandardBook("78933");

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

    @Test
    public void create30PhysicalCopiesOfABook() {
        Book uniqueBook = new Book();
        uniqueBook.setIsbn("UNIQUE");
        BookAuthor mainAuthorForUniqueBook = new BookAuthor();
        mainAuthorForUniqueBook.setBookAuthorName("Main Author for Unique Book");

        BookPublisher mainPublisherForUniqueBook = new BookPublisher();
        mainPublisherForUniqueBook.setBookPublisherName("Main Publisher for Unique Book");

        BookGenre mainGenreForUniqueBook = new BookGenre();
        mainGenreForUniqueBook.setBookGenreName("Main Genre for Unique Book");

        uniqueBook.setBookGenre(mainGenreForUniqueBook);
        uniqueBook.setBookAuthors(Set.of(mainAuthorForUniqueBook));
        uniqueBook.setBookPublisher(mainPublisherForUniqueBook);

        uniqueBook.setBookTitle("Book which is about to be super unique");

        BookLocation mainLocationForUniqueBook = new BookLocation();
        BookLocationFloor mainLocationFloorForUniqueBook = new BookLocationFloor();
        mainLocationFloorForUniqueBook.setBookLocationFloorNumber(1);
        BookLocationShelf mainLocationShelfForUniqueBook = new BookLocationShelf();
        mainLocationShelfForUniqueBook.setBookLocationShelfNumber(1);
        mainLocationForUniqueBook.setBookLocationFloor(mainLocationFloorForUniqueBook);
        mainLocationForUniqueBook.setBookLocationShelf(mainLocationShelfForUniqueBook);

        bookRepository.save(uniqueBook);
        bookRepository.saveBookCopyCount(bookRepository.findByIsbn(uniqueBook.getIsbn()), 30, mainLocationForUniqueBook);

        assertEquals(30, bookRepository.findStockByIsbn(uniqueBook.getIsbn()).size());
    }

    @Test
    public void testRemoveStock() {
        Book book = createStandardBook("STOCK-1");
        bookRepository.save(book);

        BookLocation location = new BookLocation();
        BookLocationFloor floor = new BookLocationFloor();
        floor.setBookLocationFloorNumber(1);
        BookLocationShelf shelf = new BookLocationShelf();
        shelf.setBookLocationShelfNumber(1);
        location.setBookLocationFloor(floor);
        location.setBookLocationShelf(shelf);
        bookRepository.saveBookCopyCount(book, 2, location);

        assertTrue(bookRepository.findStockByIsbn("STOCK-1").stream().anyMatch(BookStockLog::getBookIsInStock));

        bookRepository.removeStock("STOCK-1");

        assertFalse(bookRepository.findStockByIsbn("STOCK-1").stream().anyMatch(BookStockLog::getBookIsInStock));
    }
}
