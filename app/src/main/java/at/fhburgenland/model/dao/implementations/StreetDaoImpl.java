package at.fhburgenland.model.dao.implementations;

import at.fhburgenland.model.Street;
import at.fhburgenland.model.dao.interfaces.StreetDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

/**
 * Concrete implementation of Street DAO - offers CRUD operations
 */
public class StreetDaoImpl implements StreetDao {
    private final EntityManager entityManager;

    public StreetDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(Street model) {
        entityManager.persist(model);
    }

    @Override
    public Street readById(Integer id) {
        return entityManager.find(Street.class, id);
    }

    @Override
    public Street findByName(String name) {
        String query = "select street from Street street where lower(street.street) = lower(:name)";
        TypedQuery<Street> typedQuery = entityManager.createQuery(query, Street.class);
        typedQuery.setParameter("name", name);
        return typedQuery.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public void update(Street model) {
        entityManager.merge(model);
    }

    @Override
    public void delete(Street model) {
        entityManager.remove(model);
    }
}
