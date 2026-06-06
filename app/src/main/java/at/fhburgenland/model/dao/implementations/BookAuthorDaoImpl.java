package at.fhburgenland.model.dao.implementations;

import at.fhburgenland.model.dao.interfaces.BookAuthorDao;
import at.fhburgenland.model.BookAuthor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class BookAuthorDaoImpl implements BookAuthorDao {

    private final EntityManager entityManager;
    private EntityTransaction entityTransaction = null;

    public BookAuthorDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(BookAuthor bookAuthor) {
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.persist(bookAuthor);
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
    public BookAuthor read(Integer bookAuthorId) {
        return entityManager.find(BookAuthor.class, bookAuthorId);
    }

    @Override
    public void update(BookAuthor bookAuthor) {
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.merge(bookAuthor);
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
    public void delete(BookAuthor bookAuthor) {
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.remove(bookAuthor);
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
