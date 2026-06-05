package at.fhburgenland.dao;

import at.fhburgenland.model.BookAuthor;

/**
 * Abstract class that provides CRUD operations for BookAuthor domain
 */
public interface BookAuthorDao {
    void create(BookAuthor bookAuthor);
    BookAuthor read(Integer bookAuthorId);
    void update(BookAuthor bookAuthor);
    void delete(Integer bookAuthorId);

}
