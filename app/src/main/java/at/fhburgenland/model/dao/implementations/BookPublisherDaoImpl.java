package at.fhburgenland.model.dao.implementations;

import at.fhburgenland.model.BookGenre;
import at.fhburgenland.model.dao.interfaces.BookPublisherDao;
import at.fhburgenland.model.BookPublisher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

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
    public BookPublisher readById(Integer bookPublisherId) {
        return entityManager.find(BookPublisher.class, bookPublisherId);
    }

    @Override
    public List<BookPublisher> readByName(String bookPublisherName) {
        String query = "select publisher from BookPublisher as publisher where publisher.bookPublisherName like :bookPublisherName";
        TypedQuery<BookPublisher> typedPublisherQuery = entityManager.createQuery(query, BookPublisher.class);
        typedPublisherQuery.setParameter("bookPublisherName", "%" + bookPublisherName + "%");
        return typedPublisherQuery.getResultList();
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
