package at.fhburgenland.model.repository.interfaces;

import at.fhburgenland.model.Book;

import java.util.List;

/**
 * Repository layer which takes input from upstream controlling layer
 * and passes it further down to persistence layer with data access objects (DAO).
 */
public interface BookRepository {
    public Book findByIsbn(String isbn);

    public List<Book> findByBookName(String bookName);

    public List<Book> findByAuthor(String author);

    public List<Book> findByGenre(String genre);

    public List<Book> findByPublisher(String publisher);

    public void save(Book book);

    public void remove(String isbn);
}
