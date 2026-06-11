package at.fhburgenland.model.dao.implementations;

import at.fhburgenland.model.Event;
import at.fhburgenland.model.dao.interfaces.EventDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * Concrete implementation of Event DAO - offers CRUD operations as well as additional
 * read methods for lookups based on ID, name,...
 */
public class EventDaoImpl implements EventDao {
    private final EntityManager entityManager;

    public EventDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(Event event) {
        entityManager.persist(event);
    }

    @Override
    public Event readById(Integer id) {
        return entityManager.find(Event.class, id);
    }

    @Override
    public List<Event> findAll() {
        String query = "select event from Event as event order by event.eventStartsAtTs desc";
        TypedQuery<Event> typedEventQuery = entityManager.createQuery(query, Event.class);
        return typedEventQuery.getResultList();
    }

    @Override
    public List<Event> readbyName(String eventName) {
        String query = "select event from Event as event where lower(event.eventName) like lower(:name)";
        TypedQuery<Event> typedEventQuery = entityManager.createQuery(query, Event.class);
        typedEventQuery.setParameter("name", eventName);
        return typedEventQuery.getResultList();
    }

    @Override
    public List<Event> readByEventType(String eventTypeName) {
        String query = "select event from Event as event left join event.eventType as eventType where lower(eventType.eventTypeName) like lower(:name)";
        TypedQuery<Event> typedEventQuery = entityManager.createQuery(query, Event.class);
        typedEventQuery.setParameter("name", eventTypeName);
        return typedEventQuery.getResultList();
    }

    @Override
    public void update(Event event) {
        entityManager.merge(event);
    }

    @Override
    public void delete(Event event) {
        entityManager.remove(event);
    }
}
