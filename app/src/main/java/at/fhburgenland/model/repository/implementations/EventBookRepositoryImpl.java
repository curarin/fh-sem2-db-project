package at.fhburgenland.model.repository.implementations;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.Event;
import at.fhburgenland.model.repository.interfaces.EventBookRepository;

import java.util.List;

public class EventBookRepositoryImpl implements EventBookRepository {
    @Override
    public void addBookToEvent(Book book, Event event) {

    }

    @Override
    public void removeBookFromEvent(Book book, Event event) {

    }

    @Override
    public List<Book> getBooksFromEvent(Event event) {
        return List.of();
    }
}
