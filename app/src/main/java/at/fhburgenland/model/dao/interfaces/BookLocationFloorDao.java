package at.fhburgenland.model.dao.interfaces;

import at.fhburgenland.model.BookLocationFloor;

/**
 * Abstract class that provides CRUD operations for Book Location Floor domain
 */
public interface BookLocationFloorDao {
    void create(BookLocationFloor bookLocationFloor);
    void update(BookLocationFloor bookLocationFloor);
    void delete(BookLocationFloor bookLocationFloor);
    BookLocationFloor readById(Integer bookLocationFloorId);
    BookLocationFloor readByNumber(Integer bookLocationFloorNumber);
}
