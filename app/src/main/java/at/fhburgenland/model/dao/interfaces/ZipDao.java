package at.fhburgenland.model.dao.interfaces;

import at.fhburgenland.model.Zip;

/**
 * Interface that provides CRUD operations for Zip
 */
public interface ZipDao {
    void create(Zip model);

    Zip readById(Integer id);

    Zip findByZip(String name);

    void update(Zip model);

    void delete(Zip model);
}
