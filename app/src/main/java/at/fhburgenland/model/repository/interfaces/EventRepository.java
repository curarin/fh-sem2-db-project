package at.fhburgenland.model.repository.interfaces;

import at.fhburgenland.model.Event;

import java.util.List;

/**
 * Repository layer which takes input from upstream controlling layer
 * and passes it further down to persistence layer with data access objects (DAO).
 */
public interface EventRepository {
    public Event findById(Integer id);

    public List<Event> findAll();

    public List<Event> findByName(String name);

    public List<Event> findByType(String eventTypeName);

    public void save(Event event);

    public void remove(Integer id);
}
