package at.fhburgenland.model.dao.interfaces;

import at.fhburgenland.model.BookCirculationLog;

import java.util.List;

/**
 * Abstract class that provides CRUD operations for BookAuthor domain
 */
public interface BookCirculationLogDao {
    void create(BookCirculationLog bookCirculationLog);

    List<BookCirculationLog> readByCustomerId(Integer customerId);

    BookCirculationLog readById(Integer id);

    void update(BookCirculationLog model);

    void delete(BookCirculationLog model);

}
