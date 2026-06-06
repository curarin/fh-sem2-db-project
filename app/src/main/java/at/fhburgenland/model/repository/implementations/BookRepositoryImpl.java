package at.fhburgenland.model.repository.implementations;

import at.fhburgenland.model.dao.interfaces.BookDao;
import at.fhburgenland.model.Book;
import at.fhburgenland.model.BookAuthor;
import at.fhburgenland.model.BookGenre;
import at.fhburgenland.model.BookPublisher;
import at.fhburgenland.model.repository.interfaces.BookRepository;

import java.util.List;

public class BookRepositoryImpl implements BookRepository {
    private final BookDao bookDao;

    public BookRepositoryImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public Book getByIsbn(String isbn) {
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

    @Override
    public List<Book> getAllByGenre(BookGenre bookGenre) {
        return List.of();
    }
}
