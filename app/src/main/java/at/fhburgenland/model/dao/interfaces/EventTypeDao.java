package at.fhburgenland.model.dao.interfaces;

import at.fhburgenland.model.EventType;

import java.util.List;

/**
 * Abstract class that provides CRUD operations for Event domain
 */
public interface EventTypeDao {
    void create(EventType eventType);
    EventType readById(Integer id);
    List<EventType> readByName(String name);
    void update(EventType eventType);
    void delete(EventType eventType);
}
