package at.fhburgenland.model.dao.implementations;

import at.fhburgenland.model.CustomerCard;
import at.fhburgenland.model.dao.interfaces.CustomerCardDao;
import jakarta.persistence.EntityManager;

/**
 * Concrete implementation of CustomerCard DAO - offers CRUD operations as well as additional
 */
public class CustomerCardDaoImpl implements CustomerCardDao {
    private final EntityManager entityManager;

    public CustomerCardDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(CustomerCard model) {
        entityManager.persist(model);
    }

    @Override
    public CustomerCard readById(Integer id) {
        return entityManager.find(CustomerCard.class, id);
    }

    @Override
    public void update(CustomerCard model) {
        entityManager.merge(model);
    }

    @Override
    public void delete(CustomerCard model) {
        entityManager.remove(model);
    }
}
