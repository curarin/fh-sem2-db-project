package at.fhburgenland.model.repository.implementations;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.BookCirculationLog;
import at.fhburgenland.model.BookStockLog;
import at.fhburgenland.model.Customer;
import at.fhburgenland.model.dao.implementations.BookCirculationLogDaoImpl;
import at.fhburgenland.model.dao.implementations.BookStockLogDaoImpl;
import at.fhburgenland.model.dao.interfaces.BookCirculationLogDao;
import at.fhburgenland.model.dao.interfaces.BookStockLogDao;
import at.fhburgenland.model.repository.interfaces.CirculationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;
import java.util.List;

public class CirculationRepositoryImpl implements CirculationRepository {
    private final EntityManagerFactory entityManagerFactory;

    public CirculationRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void borrowBook(Customer customer, Book book) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            BookStockLogDao stockLogDao = new BookStockLogDaoImpl(entityManager);
            BookCirculationLogDao circulationLogDao = new BookCirculationLogDaoImpl(entityManager);

            // Find available stock
            List<BookStockLog> availableStock = stockLogDao.findByIsbn(book.getIsbn()).stream()
                    .filter(BookStockLog::getBookIsInStock)
                    .toList();

            if (availableStock.isEmpty()) {
                throw new RuntimeException("No copies available for book: " + book.getBookTitle());
            }

            BookStockLog stockToBorrow = availableStock.get(0);
            stockToBorrow.setBookIsInStock(false);
            stockLogDao.update(stockToBorrow);

            BookCirculationLog log = new BookCirculationLog();
            log.setCustomer(entityManager.merge(customer));
            log.setFkStockid(stockToBorrow);
            log.setLoanStartsAtDate(LocalDate.now());
            log.setLoanEndsAtDate(LocalDate.now().plusWeeks(2));

            circulationLogDao.create(log);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error borrowing book", e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void returnBook(int circulationLogId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();

            BookCirculationLogDao circulationLogDao = new BookCirculationLogDaoImpl(entityManager);
            BookStockLogDao stockLogDao = new BookStockLogDaoImpl(entityManager);

            BookCirculationLog log = circulationLogDao.readById(circulationLogId);
            if (log == null) {
                throw new RuntimeException("Circulation log not found with ID: " + circulationLogId);
            }

            if (log.getBookReturnedAtDate() != null) {
                throw new RuntimeException("Book already returned for log ID: " + circulationLogId);
            }

            log.setBookReturnedAtDate(LocalDate.now());
            circulationLogDao.update(log);

            BookStockLog stock = log.getFkStockid();
            stock.setBookIsInStock(true);
            stockLogDao.update(stock);

            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new RuntimeException("Error returning book", e);
        } finally {
            entityManager.close();
        }
    }
}
