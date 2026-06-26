package at.fhburgenland.model.dao.interfaces;

import at.fhburgenland.model.BookAuthor;

import java.util.List;

/**
 * Interface that provides CRUD operations for BookAuthor domain
 */
public interface BookAuthorDao {
    void create(BookAuthor bookAuthor);

    BookAuthor readById(Integer bookAuthorId);

    List<BookAuthor> readByName(String authorName);

    void update(BookAuthor bookAuthor);

    void delete(BookAuthor bookAuthor);

}
