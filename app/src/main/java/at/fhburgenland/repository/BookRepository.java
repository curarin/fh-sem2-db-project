package at.fhburgenland.repository;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.BookAuthor;
import at.fhburgenland.model.BookPublisher;

import java.util.List;

public interface BookRepository {
    public Book getById(String isbn);
    public List<Book> getAllByPublisher(BookPublisher bookPublisher);
    public List<Book> getAllByAuthor(BookAuthor bookAuthor);
}
