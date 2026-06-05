package at.fhburgenland.repository.interfaces;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.BookAuthor;
import at.fhburgenland.model.BookGenre;
import at.fhburgenland.model.BookPublisher;

import java.util.List;

public interface BookRepository {
    public Book getByIsbn(String isbn);
    public List<Book> getAllByPublisher(BookPublisher bookPublisher);
    public List<Book> getAllByAuthor(BookAuthor bookAuthor);
    public List<Book> getAllByGenre(BookGenre bookGenre);
}
