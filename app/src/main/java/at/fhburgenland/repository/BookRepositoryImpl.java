package at.fhburgenland.repository;

import at.fhburgenland.dao.interfaces.BookDao;
import at.fhburgenland.model.Book;
import at.fhburgenland.model.BookAuthor;
import at.fhburgenland.model.BookPublisher;

import java.util.List;

public class BookRepositoryImpl implements BookRepository {
    private final BookDao bookDao;

    public BookRepositoryImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public Book getById(String isbn) {
        return bookDao.read(isbn);
    }

    @Override
    public List<Book> getAllByPublisher(BookPublisher bookPublisher) {
        return List.of();
    }

    @Override
    public List<Book> getAllByAuthor(BookAuthor bookAuthor) {
        return List.of();
    }
}
