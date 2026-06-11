package at.fhburgenland.model.dao.implementations;

import at.fhburgenland.model.Town;

import at.fhburgenland.model.dao.interfaces.TownDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * Concrete implementation of Town DAO - offers CRUD operations
 */
public class TownDaoImpl implements TownDao {
    private final EntityManager entityManager;

    public TownDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(Town model) {
        entityManager.persist(model);
    }

    @Override
    public Town readById(Integer id) {
        return entityManager.find(Town.class, id);
    }

    @Override
    public List<Town> readByName(String name) {
        String query = "select m from Town m where lower(m.townName) = lower(:name)";
        TypedQuery<Town> typedQuery = entityManager.createQuery(query, Town.class);
        typedQuery.setParameter("name", name);
        return typedQuery.getResultList();
    }

    @Override
    public void update(Town model) {
        entityManager.merge(model);
    }

    @Override
    public void delete(Town model) {
        entityManager.remove(model);
    }
}
