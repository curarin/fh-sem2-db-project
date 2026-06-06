package at.fhburgenland.model.dao.implementations;

import at.fhburgenland.model.dao.interfaces.BookPublisherDao;
import at.fhburgenland.model.BookPublisher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class BookPublisherDaoImpl implements BookPublisherDao {
    private final EntityManager entityManager;
    public BookPublisherDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(BookPublisher bookPublisher) {
        entityManager.persist(bookPublisher);
    }

    @Override
    public BookPublisher read(Integer bookPublisherId) {
        return entityManager.find(BookPublisher.class, bookPublisherId);
    }

    @Override
    public void update(BookPublisher bookPublisher) {
        entityManager.merge(bookPublisher);
    }

    @Override
    public void delete(BookPublisher bookPublisher) {
        entityManager.remove(bookPublisher);
    }
}
