package at.fhburgenland.model.dao.implementations;

import at.fhburgenland.model.BookCirculationLog;
import at.fhburgenland.model.dao.interfaces.BookCirculationLogDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * implementation of BookCirculationLogDao
 * offers CRUD operations
 *
 */
public class BookCirculationLogDaoImpl implements BookCirculationLogDao {
    private final EntityManager entityManager;

    public BookCirculationLogDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(BookCirculationLog bookCirculationLog) {
        entityManager.persist(bookCirculationLog);
    }

    @Override
    public List<BookCirculationLog> readByCustomerId(Integer customerId) {
        TypedQuery<BookCirculationLog> query = entityManager.createQuery(
                "SELECT b FROM BookCirculationLog b " +
                        "JOIN FETCH b.fkStockid s " +
                        "JOIN FETCH s.book " +
                        "WHERE b.customer.customerId = :customerId", BookCirculationLog.class);
        query.setParameter("customerId", customerId);
        return query.getResultList();
    }

    @Override
    public BookCirculationLog readById(Integer id) {
        return entityManager.find(BookCirculationLog.class, id);
    }

    @Override
    public void update(BookCirculationLog model) {
        entityManager.merge(model);
    }

    @Override
    public void delete(BookCirculationLog model) {
        entityManager.remove(model);
    }

    @Override
    public boolean isBookInCirculation(String isbn) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(b) FROM BookCirculationLog b WHERE b.fkStockid.book.isbn = :isbn", Long.class);
        query.setParameter("isbn", isbn);
        return query.getSingleResult() > 0;
    }
}
