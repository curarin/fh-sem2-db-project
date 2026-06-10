package at.fhburgenland.model.dao.implementations;

import at.fhburgenland.model.City;

import at.fhburgenland.model.dao.interfaces.CityDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class CityDaoImpl implements CityDao {
    private final EntityManager entityManager;

    public CityDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(City model) {
        entityManager.persist(model);
    }

    @Override
    public City readById(Integer id) {
        return entityManager.find(City.class, id);
    }

    @Override
    public List<City> readByName(String customerName) {
        String query = "select m from City m where lower(m.cityName) = lower(:name)";
        TypedQuery<City> typedQuery = entityManager.createQuery(query, City.class);
        typedQuery.setParameter("name", customerName);
        return typedQuery.getResultList();
    }

    @Override
    public void update(City model) {
        entityManager.merge(model);
    }

    @Override
    public void delete(City model) {
        entityManager.remove(model);
    }
}
