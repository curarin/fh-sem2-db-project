package at.fhburgenland.model.repository.interfaces;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.BookLocation;
import at.fhburgenland.model.BookStockLog;

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

    /**
     * Returns all unique ISBNs depending on Stock State
     *
     * @param bookIsCurrentlyInStock true/false
     * @return List of ISBNs currently in stock / not in stock
     */
    public List<Book> findByStockState(Boolean bookIsCurrentlyInStock);

    /**
     * Returns the stock state for a given book
     *
     * @param isbn ISBN of the book
     * @return list of books
     */
    public List<BookStockLog> findStockByIsbn(String isbn);

    public void save(Book book);

    public void saveBookCopyCount(Book book, int bookCopyCount, BookLocation bookLocation);

    public void remove(String isbn);

    public boolean isBookInCirculation(String isbn);

    public void removeStock(String isbn);
}
