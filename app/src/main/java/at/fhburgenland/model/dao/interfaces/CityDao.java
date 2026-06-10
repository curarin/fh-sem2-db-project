package at.fhburgenland.model.dao.interfaces;

import at.fhburgenland.model.City;

import java.util.List;

/**
 * Abstract class that provides CRUD operations for City
 */
public interface CityDao {
    void create(City model);

    City readById(Integer id);

    List<City> readByName(String name);

    void update(City model);

    void delete(City model);
}
