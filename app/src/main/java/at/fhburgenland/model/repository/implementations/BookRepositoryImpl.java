package at.fhburgenland.model.repository.implementations;

import at.fhburgenland.model.dao.implementations.BookDaoImpl;
import at.fhburgenland.model.dao.interfaces.BookDao;
import at.fhburgenland.model.Book;
import at.fhburgenland.model.repository.interfaces.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class BookRepositoryImpl implements BookRepository {
    private final EntityManagerFactory entityManagerFactory;

    public BookRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Book findByIsbn(String isbn) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            BookDao bookDao = new BookDaoImpl(entityManager);
            return bookDao.readByIsbn(isbn);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Book> findByBookName(String bookName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            BookDao bookDao = new BookDaoImpl(entityManager);
            return bookDao.readByTitle(bookName);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Book> findByAuthor(String author) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            BookDao bookDao = new BookDaoImpl(entityManager);
            return bookDao.readByAuthor(author);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Book> findByGenre(String genre) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            BookDao bookDao = new BookDaoImpl(entityManager);
            return bookDao.readByGenre(genre);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Book> findByPublisher(String publisher) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            BookDao bookDao = new BookDaoImpl(entityManager);
            return bookDao.readByPublisher(publisher);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void save(Book updatedBook) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = null;

        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            BookDao bookDao = new BookDaoImpl(entityManager);
            Book existingBook = bookDao.readByIsbn(updatedBook.getIsbn());

            if (existingBook != null) {
                bookDao.create(updatedBook);
            } else {
                bookDao.update(updatedBook);
            }
            entityTransaction.commit();
        } catch (Exception exception) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void remove(Book book) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = null;

        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            BookDao bookDao = new BookDaoImpl(entityManager);
            bookDao.delete(book);
            entityTransaction.commit();
        } catch (Exception exception) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }
}
