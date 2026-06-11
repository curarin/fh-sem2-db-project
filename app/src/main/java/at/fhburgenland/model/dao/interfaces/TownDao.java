package at.fhburgenland.model.dao.interfaces;

import at.fhburgenland.model.Town;

import java.util.List;

/**
 * Abstract class that provides CRUD operations for Town
 */
public interface TownDao {
    void create(Town model);

    Town readById(Integer id);

    List<Town> readByName(String name);

    void update(Town model);

    void delete(Town model);
}
