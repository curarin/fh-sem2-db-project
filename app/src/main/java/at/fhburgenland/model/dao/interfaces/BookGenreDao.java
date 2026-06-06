package at.fhburgenland.model.dao.interfaces;

import at.fhburgenland.model.BookGenre;

/**
 * Abstract class that provides CRUD operations for BookGenre domain
 */
public interface BookGenreDao {
    void create(BookGenre bookGenre);
    BookGenre readById(Integer bookGenreId);
    void update(BookGenre bookGenre);
    void delete(BookGenre bookGenre);
}
