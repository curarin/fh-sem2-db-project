package at.fhburgenland.model.dao.interfaces;

import at.fhburgenland.model.Book;

import java.util.List;

/**
 * Interface that provides CRUD operations for Book domain
 */
public interface BookDao {
    void create(Book book);

    Book readByIsbn(String bookIsbn);

    List<Book> readByTitle(String bookTitle);

    List<Book> readByAuthor(String bookAuthor);

    List<Book> readByPublisher(String bookPublisher);

    List<Book> readByGenre(String bookGenre);

    List<Book> readByStockState(Boolean bookIsCurrentlyInStock);

    void update(Book book);

    void delete(Book book);
}
