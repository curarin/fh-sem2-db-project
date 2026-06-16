package at.fhburgenland.model.dao.implementations;

import at.fhburgenland.model.CustomerEventMap;
import at.fhburgenland.model.CustomerEventMapId;
import at.fhburgenland.model.dao.interfaces.CustomerEventMapDao;
import jakarta.persistence.EntityManager;

/**
 * Concrete implementation of CustomerEventMap DAO - offers CRUD operations as well as additional
 */
public class CustomerEventMapDaoImpl implements CustomerEventMapDao {
    private final EntityManager entityManager;

    public CustomerEventMapDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(CustomerEventMap model) {
        entityManager.persist(model);
    }

    @Override
    public CustomerEventMap readById(CustomerEventMapId id) {
        return entityManager.find(CustomerEventMap.class, id);
    }

    @Override
    public void update(CustomerEventMap model) {
        entityManager.merge(model);
    }

    @Override
    public void delete(CustomerEventMap model) {
        entityManager.remove(model);
    }
}
