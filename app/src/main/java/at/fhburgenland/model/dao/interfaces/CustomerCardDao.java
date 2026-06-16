package at.fhburgenland.model.dao.interfaces;

import at.fhburgenland.model.CustomerCard;

/**
 * Abstract class that provides CRUD operations for CustomerCard
 */
public interface CustomerCardDao {
    void create(CustomerCard model);

    CustomerCard readById(Integer id);

    void update(CustomerCard model);

    void delete(CustomerCard model);
}
