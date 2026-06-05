package at.fhburgenland.dao;

import at.fhburgenland.model.BookPublisher;

/**
 * Abstract class that provides CRUD operations for BookPublisher domain
 */
public interface BookPublisherDao {
    void create(BookPublisher bookPublisher);
    BookPublisher read(Integer bookPublisherId);
    void update(BookPublisher bookPublisher);
    void delete(Integer bookPublisherId);
}
