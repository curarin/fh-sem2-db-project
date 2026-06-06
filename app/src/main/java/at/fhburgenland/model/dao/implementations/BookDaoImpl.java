package at.fhburgenland.model.dao.implementations;

import at.fhburgenland.model.dao.interfaces.BookDao;
import at.fhburgenland.model.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class BookDaoImpl implements BookDao {
    private final EntityManager entityManager;
    private EntityTransaction entityTransaction = null;

    public BookDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(Book book) {
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.persist(book);
            entityTransaction.commit();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Book read(String bookIsbn) {
        return entityManager.find(Book.class, bookIsbn);
    }

    @Override
    public void update(Book book) {
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.merge(book);
            entityTransaction.commit();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(Book book) {
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.remove(book);
            entityTransaction.commit();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }
}
