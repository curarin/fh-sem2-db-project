package at.fhburgenland.model.dao.interfaces;

import at.fhburgenland.model.BookLocation;

/**
 * Abstract class that provides CRUD operations for Book Location domain
 */
public interface BookLocationDao {
    void create(BookLocation bookLocation);
    void update(BookLocation bookLocation);
    void delete(BookLocation bookLocation);
    BookLocation readById(Integer bookLocationId);
}
