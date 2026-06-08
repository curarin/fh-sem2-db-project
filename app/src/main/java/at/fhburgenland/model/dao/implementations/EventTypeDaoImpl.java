package at.fhburgenland.model.dao.implementations;

import at.fhburgenland.model.EventType;
import at.fhburgenland.model.dao.interfaces.EventTypeDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class EventTypeDaoImpl implements EventTypeDao {
    private final EntityManager entityManager;

    public EventTypeDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(EventType eventType) {
        entityManager.persist(eventType);
    }

    @Override
    public EventType readById(Integer id) {
        return entityManager.find(EventType.class, id);
    }

    @Override
    public List<EventType> readByName(String eventTypeName) {
        String query = "select eventType from EventType eventType where lower(eventType.eventTypeName) = lower(:name)";
        TypedQuery<EventType> typedEventTypeQuery = entityManager.createQuery(query, EventType.class);
        typedEventTypeQuery.setParameter("name", eventTypeName);
        return typedEventTypeQuery.getResultList();
    }

    @Override
    public void update(EventType eventType) {
        entityManager.merge(eventType);
    }

    @Override
    public void delete(EventType eventType) {
        entityManager.remove(eventType);
    }
}
