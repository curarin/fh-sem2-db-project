package at.fhburgenland.model.dao.interfaces;

import at.fhburgenland.model.Event;

import java.util.List;

/**
 * Interface that provides CRUD operations for Event domain
 */
public interface EventDao {
    void create(Event event);

    Event readById(Integer id);

    List<Event> findAll();

    List<Event> readbyName(String name);

    List<Event> readByEventType(String eventTypeName);

    void update(Event event);

    void delete(Event event);
}
