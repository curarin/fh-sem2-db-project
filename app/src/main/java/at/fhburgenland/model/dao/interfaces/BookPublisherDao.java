package at.fhburgenland.model.dao.interfaces;

import at.fhburgenland.model.BookPublisher;

import java.util.List;

/**
 * Abstract class that provides CRUD operations for BookPublisher domain
 */
public interface BookPublisherDao {
    void create(BookPublisher bookPublisher);
    BookPublisher readById(Integer bookPublisherId);
    List<BookPublisher> readByName(String bookPublisherName);
    void update(BookPublisher bookPublisher);
    void delete(BookPublisher bookPublisher);
}
