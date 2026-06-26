package at.fhburgenland.model.dao.interfaces;

import at.fhburgenland.model.Town;

/**
 * Interface that provides CRUD operations for Town
 */
public interface TownDao {
    void create(Town model);

    Town readById(Integer id);

    Town findByName(String name);

    void update(Town model);

    void delete(Town model);
}
