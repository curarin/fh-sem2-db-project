package at.fhburgenland.model.dao.interfaces;

import at.fhburgenland.model.Street;

import java.util.List;

/**
 * Abstract class that provides CRUD operations for Street
 */
public interface StreetDao {
    void create(Street model);

    Street readById(Integer id);

    Street findByName(String name);

    void update(Street model);

    void delete(Street model);
}
