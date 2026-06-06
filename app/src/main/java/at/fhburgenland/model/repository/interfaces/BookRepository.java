package at.fhburgenland.model.repository.interfaces;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.BookAuthor;
import at.fhburgenland.model.BookGenre;
import at.fhburgenland.model.BookPublisher;

import java.util.List;

public interface BookRepository {
    public Book find(String isbn);
    public void save(Book book);
    public void remove(Book book);
}
