package at.fhburgenland.model.dao.interfaces;

import at.fhburgenland.model.BookStockLog;

import java.util.List;

/**
 * Abstract class that provides CRUD operations for BookPublisher domain
 */
public interface BookStockLogDao {
    void create(BookStockLog bookStockLog);

    void update(BookStockLog bookStockLog);

    void delete(BookStockLog bookStockLog);

    List<BookStockLog> findByValue(boolean bookIsInStock);
}
