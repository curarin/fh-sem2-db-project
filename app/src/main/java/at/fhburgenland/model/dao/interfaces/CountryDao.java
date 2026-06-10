package at.fhburgenland.model.dao.interfaces;

import at.fhburgenland.model.Country;

import java.util.List;

/**
 * Abstract class that provides CRUD operations for Country
 */
public interface CountryDao {
    void create(Country model);

    Country readById(Integer id);

    List<Country> readByName(String name);

    void update(Country model);

    void delete(Country model);
}
