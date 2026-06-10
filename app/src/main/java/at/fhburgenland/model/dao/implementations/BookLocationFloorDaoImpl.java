package at.fhburgenland.model.dao.implementations;

import at.fhburgenland.model.BookLocationFloor;
import at.fhburgenland.model.dao.interfaces.BookLocationFloorDao;
import jakarta.persistence.EntityManager;

public class BookLocationFloorDaoImpl implements BookLocationFloorDao {
    private final EntityManager entityManager;

    public BookLocationFloorDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public void create(BookLocationFloor bookLocationFloor) {
        entityManager.persist(bookLocationFloor);
    }

    @Override
    public void update(BookLocationFloor bookLocationFloor) {
        entityManager.merge(bookLocationFloor);
    }

    @Override
    public void delete(BookLocationFloor bookLocationFloor) {
        entityManager.remove(bookLocationFloor);
    }

    @Override
    public BookLocationFloor readById(Integer bookLocationFloorId) {
        return entityManager.find(BookLocationFloor.class, bookLocationFloorId);
    }
}
