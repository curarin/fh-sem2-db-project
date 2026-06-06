package at.fhburgenland.model.repository.interfaces;

import at.fhburgenland.model.Book;

import java.util.List;

public interface BookRepository {
    public Book findByIsbn(String isbn);
    public List<Book> findByBookName(String bookName);
    public List<Book> findByAuthor(String author);
    public List<Book> findByGenre(String genre);
    public List<Book> findByPublisher(String publisher);
    public void save(Book book);
    public void remove(Book book);
}
