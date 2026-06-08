package at.fhburgenland.model.dao.implementations;

import at.fhburgenland.model.BookPublisher;
import at.fhburgenland.model.dao.interfaces.BookPublisherDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * Concrete implementation of BookPublisher DAO - offers CRUD operations as well as additional
 * read methods for lookups based on ID, name,...
 */
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
        String query = "select publisher from BookPublisher as publisher where lower(publisher.bookPublisherName) like lower(:bookPublisherName)";
        TypedQuery<BookPublisher> typedPublisherQuery = entityManager.createQuery(query, BookPublisher.class);
        typedPublisherQuery.setParameter("bookPublisherName", bookPublisherName);
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
