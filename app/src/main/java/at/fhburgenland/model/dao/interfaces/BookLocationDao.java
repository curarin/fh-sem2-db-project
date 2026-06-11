package at.fhburgenland.model.dao.interfaces;

import at.fhburgenland.model.BookLocation;
import at.fhburgenland.model.BookLocationFloor;
import at.fhburgenland.model.BookLocationShelf;

/**
 * Abstract class that provides CRUD operations for Book Location domain
 */
public interface BookLocationDao {
    void create(BookLocation bookLocation);

    void update(BookLocation bookLocation);

    void delete(BookLocation bookLocation);

    BookLocation readById(Integer bookLocationId);

    BookLocation readByFloorAndShelf(BookLocationFloor bookLocationFloor, BookLocationShelf bookLocationShelf);
}
