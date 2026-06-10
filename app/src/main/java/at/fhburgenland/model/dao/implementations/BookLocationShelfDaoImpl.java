package at.fhburgenland.model.dao.implementations;

import at.fhburgenland.model.BookLocationShelf;
import at.fhburgenland.model.dao.interfaces.BookLocationShelfDao;
import jakarta.persistence.EntityManager;

public class BookLocationShelfDaoImpl implements BookLocationShelfDao {
    private final EntityManager entityManager;
    public BookLocationShelfDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(BookLocationShelf bookLocationShelf) {
        entityManager.persist(bookLocationShelf);
    }

    @Override
    public void update(BookLocationShelf bookLocationShelf) {
        entityManager.merge(bookLocationShelf);
    }

    @Override
    public void delete(BookLocationShelf bookLocationShelf) {
        entityManager.remove(bookLocationShelf);
    }

    @Override
    public BookLocationShelf readById(Integer bookLocationId) {
        return entityManager.find(BookLocationShelf.class, bookLocationId);
    }


}
