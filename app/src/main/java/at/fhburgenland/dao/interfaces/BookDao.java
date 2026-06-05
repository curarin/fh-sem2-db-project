package at.fhburgenland.dao.interfaces;

import at.fhburgenland.model.Book;

/**
 * Abstract class that provides CRUD operations for Book domain
 */
public interface BookDao {
    void create(Book book);
    Book read(String bookIsbn);
    void update(Book book);
    void delete(Book book);
}
