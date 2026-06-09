package at.fhburgenland.model.dao.implementations;

import at.fhburgenland.model.BookStockLog;
import at.fhburgenland.model.dao.interfaces.BookStockLogDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class BookStockLogDaoImpl implements BookStockLogDao {
    private final EntityManager entityManager;

    public BookStockLogDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(BookStockLog bookStockLog) {
        entityManager.persist(bookStockLog);
    }

    @Override
    public void update(BookStockLog bookStockLog) {
        entityManager.merge(bookStockLog);
    }

    @Override
    public void delete(BookStockLog bookStockLog) {
        entityManager.remove(bookStockLog);
    }

    @Override
    public BookStockLog findByValue(boolean bookIsInStock) {
        BookStockLog bookStockLog = new BookStockLog();
        bookStockLog.setBookIsInStock(bookIsInStock);
        String query = "select bookStockLog from BookStockLog as bookStockLog where bookStockLog.bookIsInStock = :bookIsInStock";
        TypedQuery<BookStockLog> typedQuery = entityManager.createQuery(query, BookStockLog.class);
        typedQuery.setParameter("bookIsInStock", bookIsInStock);
        return typedQuery.getSingleResultOrNull();
    }
}
