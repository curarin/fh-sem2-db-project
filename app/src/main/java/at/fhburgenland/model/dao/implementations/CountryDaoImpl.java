package at.fhburgenland.model.dao.implementations;

import at.fhburgenland.model.Country;

import at.fhburgenland.model.dao.interfaces.CountryDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * Concrete implementation of Country DAO - offers CRUD operations
 */
public class CountryDaoImpl implements CountryDao {
    private final EntityManager entityManager;

    public CountryDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(Country model) {
        entityManager.persist(model);
    }

    @Override
    public Country readById(Integer id) {
        return entityManager.find(Country.class, id);
    }

    @Override
    public List<Country> readByName(String name) {
        String query = "select country from Country country where lower(country.countryName) = lower(:name)";
        TypedQuery<Country> typedQuery = entityManager.createQuery(query, Country.class);
        typedQuery.setParameter("name", name);
        return typedQuery.getResultList();
    }

    @Override
    public void update(Country model) {
        entityManager.merge(model);
    }

    @Override
    public void delete(Country model) {
        entityManager.remove(model);
    }
}
