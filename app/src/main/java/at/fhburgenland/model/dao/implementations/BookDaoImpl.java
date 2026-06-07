package at.fhburgenland.model.dao.implementations;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.dao.interfaces.BookDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * Concrete implementation of Book DAO - offers CRUD operations as well as additional
 * read methods for lookups based on ID, name,...
 */
public class BookDaoImpl implements BookDao {
    private final EntityManager entityManager;

    public BookDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(Book book) {
        entityManager.persist(book);
    }

    @Override
    public Book readByIsbn(String bookIsbn) {
        return entityManager.find(Book.class, bookIsbn);
    }

    @Override
    public List<Book> readByTitle(String bookTitle) {
        String query = "select book from Book as book where lower(book.bookTitle) like lower(:bookTitle)";
        TypedQuery<Book> typedBookQuery = entityManager.createQuery(query, Book.class);
        typedBookQuery.setParameter("bookTitle", bookTitle);
        return typedBookQuery.getResultList();
    }

    @Override
    public List<Book> readByAuthor(String bookAuthor) {
        String query = "select book from Book as book left join book.bookAuthors as bookAuthor where lower(bookAuthor.bookAuthorName) like lower(:bookAuthor)";
        TypedQuery<Book> typedBookQuery = entityManager.createQuery(query, Book.class);
        typedBookQuery.setParameter("bookAuthor", bookAuthor);
        return typedBookQuery.getResultList();
    }

    @Override
    public List<Book> readByPublisher(String bookPublisher) {
        String query = "select book from Book as book left join book.bookPublisher as bookPublisher where lower(bookPublisher.bookPublisherName) like lower(:bookPublisher)";
        TypedQuery<Book> typedBookQuery = entityManager.createQuery(query, Book.class);
        typedBookQuery.setParameter("bookPublisher", bookPublisher);
        return typedBookQuery.getResultList();
    }

    @Override
    public List<Book> readByGenre(String bookGenre) {
        String query = "select book from Book as book left join book.bookGenre as bookGenre where lower(bookGenre.bookGenreName) like lower(:bookGenre)";
        TypedQuery<Book> typedBookQuery = entityManager.createQuery(query, Book.class);
        typedBookQuery.setParameter("bookGenre", bookGenre);
        return typedBookQuery.getResultList();
    }

    @Override
    public void update(Book book) {
        entityManager.merge(book);
    }

    @Override
    public void delete(Book book) {
        entityManager.remove(book);
    }
}
