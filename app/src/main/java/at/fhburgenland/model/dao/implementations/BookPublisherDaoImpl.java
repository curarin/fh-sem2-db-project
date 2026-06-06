package at.fhburgenland.model.dao.implementations;

import at.fhburgenland.model.dao.interfaces.BookPublisherDao;
import at.fhburgenland.model.BookPublisher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class BookPublisherDaoImpl implements BookPublisherDao {
    private final EntityManager entityManager;
    private EntityTransaction entityTransaction = null;

    public BookPublisherDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(BookPublisher bookPublisher) {
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.persist(bookPublisher);
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
    public BookPublisher read(Integer bookPublisherId) {
        return entityManager.find(BookPublisher.class, bookPublisherId);
    }

    @Override
    public void update(BookPublisher bookPublisher) {
        try {
            entityTransaction.begin();
            entityManager.merge(bookPublisher);
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
    public void delete(BookPublisher bookPublisher) {
        try {
            entityTransaction.begin();
            entityManager.remove(bookPublisher);
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
