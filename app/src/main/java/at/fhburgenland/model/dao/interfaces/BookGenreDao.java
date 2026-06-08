package at.fhburgenland.model.dao.interfaces;

import at.fhburgenland.model.BookGenre;

import java.util.List;

/**
 * Abstract class that provides CRUD operations for BookGenre domain
 */
public interface BookGenreDao {
    void create(BookGenre bookGenre);
    BookGenre readById(Integer bookGenreId);
    List<BookGenre> readByName(String bookGenre);
    void update(BookGenre bookGenre);
    void delete(BookGenre bookGenre);
}
