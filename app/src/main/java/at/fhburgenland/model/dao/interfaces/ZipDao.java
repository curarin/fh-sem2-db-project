package at.fhburgenland.model.dao.interfaces;

import at.fhburgenland.model.Zip;

import java.util.List;

/**
 * Abstract class that provides CRUD operations for Zip
 */
public interface ZipDao {
    void create(Zip model);

    Zip readById(Integer id);

    List<Zip> readByName(String name);

    void update(Zip model);

    void delete(Zip model);
}
