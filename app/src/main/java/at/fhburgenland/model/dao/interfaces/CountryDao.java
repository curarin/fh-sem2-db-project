package at.fhburgenland.model.dao.interfaces;

import at.fhburgenland.model.Country;

/**
 * Interface that provides CRUD operations for Country
 */
public interface CountryDao {
    void create(Country model);

    Country readById(Integer id);

    Country findByName(String name);

    void update(Country model);

    void delete(Country model);
}
