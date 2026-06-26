package at.fhburgenland.model.dao.interfaces;

import at.fhburgenland.model.CustomerEventMap;
import at.fhburgenland.model.CustomerEventMapId;

import java.util.List;

/**
 * Interface that provides CRUD operations for CustomerEventMap
 */
public interface CustomerEventMapDao {
    void create(CustomerEventMap model);

    CustomerEventMap readById(CustomerEventMapId id);

    void update(CustomerEventMap model);

    void delete(CustomerEventMap model);

    List<CustomerEventMap> findByEventId(int eventId);
}
