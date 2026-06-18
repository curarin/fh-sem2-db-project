package at.fhburgenland.model.repository.interfaces;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.Event;

import java.util.List;

public interface EventBookRepository {
    void addBookToEvent(Book book, Event event);

    void removeBookFromEvent(Book book, Event event);

    List<Book> getBooksFromEvent(Event event);
}
