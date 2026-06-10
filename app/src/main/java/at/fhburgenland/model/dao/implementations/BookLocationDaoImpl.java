package at.fhburgenland.model.dao.implementations;

import at.fhburgenland.model.BookLocation;
import at.fhburgenland.model.BookLocationFloor;
import at.fhburgenland.model.BookLocationShelf;
import at.fhburgenland.model.dao.interfaces.BookLocationDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

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

    @Override
    public BookLocation readByFloorAndShelf(BookLocationFloor bookLocationFloor, BookLocationShelf bookLocationShelf) {
        String query = "select bookLocation from BookLocation as bookLocation where bookLocation.bookLocationShelf = :shelf and bookLocation.bookLocationFloor = :floor";
        TypedQuery<BookLocation> typedQuery = entityManager.createQuery(query, BookLocation.class);
        typedQuery.setParameter("floor", bookLocationFloor);
        typedQuery.setParameter("shelf", bookLocationShelf);
        return typedQuery.getSingleResultOrNull();
    }
}
