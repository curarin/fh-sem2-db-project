package at.fhburgenland.model.dao.implementations;

import at.fhburgenland.model.BookLocation;
import at.fhburgenland.model.dao.interfaces.BookLocationDao;
import jakarta.persistence.EntityManager;

public class BookLocationDaoImpl implements BookLocationDao {
    private final EntityManager entityManager;

    public BookLocationDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(BookLocation bookLocation) {
        entityManager.persist(bookLocation);
    }

    @Override
    public void update(BookLocation bookLocation) {
        entityManager.merge(bookLocation);
    }

    @Override
    public void delete(BookLocation bookLocation) {
        entityManager.remove(bookLocation);
    }

    @Override
    public BookLocation readById(Integer bookLocationId) {
        return entityManager.find(BookLocation.class, bookLocationId);
    }
}
