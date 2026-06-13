package at.fhburgenland.model.dao.implementations;

import at.fhburgenland.model.Zip;

import at.fhburgenland.model.dao.interfaces.ZipDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * Concrete implementation of Zip DAO - offers CRUD operations
 */
public class ZipDaoImpl implements ZipDao {
    private final EntityManager entityManager;

    public ZipDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(Zip model) {
        entityManager.persist(model);
    }

    @Override
    public Zip readById(Integer id) {
        return entityManager.find(Zip.class, id);
    }

    @Override
    public Zip findByZip(String name) {
        String query = "select m from Zip m where lower(m.zipCode) = lower(:name)";
        TypedQuery<Zip> typedQuery = entityManager.createQuery(query, Zip.class);
        typedQuery.setParameter("name", name);
        return typedQuery.getResultList().getFirst();
    }

    @Override
    public void update(Zip model) {
        entityManager.merge(model);
    }

    @Override
    public void delete(Zip model) {
        entityManager.remove(model);
    }
}
