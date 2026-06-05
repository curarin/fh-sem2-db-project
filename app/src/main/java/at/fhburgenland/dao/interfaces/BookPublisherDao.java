package at.fhburgenland.dao.interfaces;

import at.fhburgenland.model.BookPublisher;

/**
 * Abstract class that provides CRUD operations for BookPublisher domain
 */
public interface BookPublisherDao {
    void create(BookPublisher bookPublisher);
    BookPublisher read(Integer bookPublisherId);
    void update(BookPublisher bookPublisher);
    void delete(BookPublisher bookPublisher);
}
