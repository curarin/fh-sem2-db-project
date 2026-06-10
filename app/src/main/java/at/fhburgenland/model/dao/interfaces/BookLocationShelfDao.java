package at.fhburgenland.model.dao.interfaces;

import at.fhburgenland.model.BookLocationShelf;

/**
 * Abstract class that provides CRUD operations for Book Location Shelf domain
 */
public interface BookLocationShelfDao {
    void create(BookLocationShelf bookLocationShelf);
    void update(BookLocationShelf bookLocationShelf);
    void delete(BookLocationShelf bookLocationShelf);
    BookLocationShelf readById(Integer bookLocationId);
    BookLocationShelf readByNumber(Integer bookLocationFloorNumber);
}
