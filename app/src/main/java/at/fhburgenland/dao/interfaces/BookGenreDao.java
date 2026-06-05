package at.fhburgenland.dao.interfaces;

import at.fhburgenland.model.BookGenre;

/**
 * Abstract class that provides CRUD operations for BookGenre domain
 */
public interface BookGenreDao {
    void create(BookGenre bookGenre);
    BookGenre read(Integer bookGenreId);
    void update(BookGenre bookGenre);
    void delete(BookGenre bookGenre);
}
