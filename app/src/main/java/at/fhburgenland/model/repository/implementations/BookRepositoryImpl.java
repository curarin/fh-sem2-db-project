package at.fhburgenland.model.repository.implementations;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.BookAuthor;
import at.fhburgenland.model.BookStockLog;
import at.fhburgenland.model.dao.implementations.*;
import at.fhburgenland.model.dao.interfaces.*;
import at.fhburgenland.model.repository.interfaces.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Concrete implementation of Book Repository which handles persistence logic and interacts with
 * DAO layer (with further implements CRUD operations).
 * FindBy... methods use an exact overlap search (case-insensitive).
 */
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
    public List<Book> findByStockState(Boolean bookIsCurrentlyInStock) {
        List<Book> books = new ArrayList<>();
        Set<String> foundIsbns = new HashSet<>();

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            BookStockLogDao bookStockLogDao = new BookStockLogDaoImpl(entityManager);
            for (BookStockLog bookStockLog : bookStockLogDao.findByValue(bookIsCurrentlyInStock)) {
                // Wir wollen nur unique Bücher hier haben und weil wir da schon auf ne List als Return Type commited
                // sind, ziehen wir den Spaghetti Code durch um hier ne zusätzliche Logik zu bauen. yolo
                Book foundBook = bookStockLog.getBook();
                if (foundIsbns.add(foundBook.getIsbn())) {
                    books.add(foundBook);
                }
            }
            return books;
        } finally {
            entityManager.close();
        }
    }

    /**
     * Implements save logic - checks if dependent objects already exist (e.g. Book Author, Publisher, Genre,...) and
     * handles logic. E.g. if object already exists, it reads the existing entity and passes it into the Book Object.
     * This is done so we don't violate unique constraints set in JPA Entity
     *
     * @param updatedBook Book which shall be saved
     */
    @Override
    public void save(Book updatedBook) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = null;

        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();

            // Check if Genre already exists, if yes insert existing one
            BookGenreDao bookGenreDao = new BookGenreDaoImpl(entityManager);
            updatedBook.setBookGenre(bookGenreDao.readByName(updatedBook.getBookGenre().getBookGenreName()).stream().findFirst().orElse(updatedBook.getBookGenre()));

            // Check if Publisher already exists, if yes insert existing one
            BookPublisherDao bookPublisherDao = new BookPublisherDaoImpl(entityManager);
            updatedBook.setBookPublisher(bookPublisherDao.readByName(updatedBook.getBookPublisher().getBookPublisherName()).stream().findFirst().orElse(updatedBook.getBookPublisher()));

            // Check if Author already exists
            BookAuthorDao bookAuthorDao = new BookAuthorDaoImpl(entityManager);
            Set<BookAuthor> checkedBookAuthors = new HashSet<>();
            for (BookAuthor bookAuthor : updatedBook.getBookAuthors()) {
                checkedBookAuthors.add(bookAuthorDao.readByName(bookAuthor.getBookAuthorName()).stream().findFirst().orElse(bookAuthor));
            }
            updatedBook.setBookAuthors(checkedBookAuthors);

            BookDao bookDao = new BookDaoImpl(entityManager);
            Book existingBook = bookDao.readByIsbn(updatedBook.getIsbn());

            if (existingBook == null) {
                bookDao.create(updatedBook);
            } else {
                bookDao.update(updatedBook);
            }
            entityTransaction.commit();
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void saveBookCopyCount(Book book, int bookCopyCount) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = null;

        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();

            BookDao bookDao = new BookDaoImpl(entityManager);
            Book managedBook = bookDao.readByIsbn(book.getIsbn());

            BookStockLogDao bookStockLogDao = new BookStockLogDaoImpl(entityManager);
            for (int i = 1; i <= bookCopyCount; i++) {
                BookStockLog bookStockLog = new BookStockLog();
                bookStockLog.setBook(managedBook);
                bookStockLog.setBookIsInStock(true);
                bookStockLogDao.create(bookStockLog);
            }
            entityTransaction.commit();
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void remove(String isbn) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = null;

        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            BookDao bookDao = new BookDaoImpl(entityManager);
            Book book = bookDao.readByIsbn(isbn);
            bookDao.delete(book);
            entityTransaction.commit();
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }
}
