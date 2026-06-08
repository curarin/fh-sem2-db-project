package at.fhburgenland.model.repository.implementations;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.Event;
import at.fhburgenland.model.dao.implementations.BookDaoImpl;
import at.fhburgenland.model.dao.implementations.EventDaoImpl;
import at.fhburgenland.model.dao.implementations.EventTypeDaoImpl;
import at.fhburgenland.model.dao.interfaces.BookDao;
import at.fhburgenland.model.dao.interfaces.EventDao;
import at.fhburgenland.model.dao.interfaces.EventTypeDao;
import at.fhburgenland.model.repository.interfaces.EventRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class EventRepositoryImpl implements EventRepository {
    private final EntityManagerFactory entityManagerFactory;

    public EventRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Event findById(Integer id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            EventDao eventDao = new EventDaoImpl(entityManager);
            return eventDao.readById(id);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Event> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            EventDao eventDao = new EventDaoImpl(entityManager);
            return eventDao.findAll();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Event> findByName(String eventName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            EventDao eventDao = new EventDaoImpl(entityManager);
            return eventDao.readbyName(eventName);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Event> findByType(String eventTypeName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            EventDao eventDao = new EventDaoImpl(entityManager);
            return eventDao.readByEventType(eventTypeName);
        } finally {
            entityManager.close();
        }
    }

    /**
     * Implements save logic - works the same as in BookRepositoryImpl > checks if dependent objects already exist &
     * handles logic. E.g. if object already exists, it reads the existing entity and passes it into the Book Object.
     * this is done so we don't violate unique constraints set in JPA Entity
     *
     * @param updatedEvent Event object which shall be saved
     */
    @Override
    public void save(Event updatedEvent) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = null;

        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();

            // Check if Genre already exists - if yes, insert existing one
            EventTypeDao eventTypeDao = new EventTypeDaoImpl(entityManager);
            updatedEvent.setEventType(eventTypeDao.readByName(updatedEvent.getEventType().getEventTypeName()).stream().findFirst().orElse(updatedEvent.getEventType()));

            // Check if Book already exists insert existing one
            BookDao bookDao = new BookDaoImpl(entityManager);
            Set<Book> checkedBookes = new HashSet<>();
            for (Book book : updatedEvent.getBooks()) {
                Book existingBook = bookDao.readByIsbn(book.getIsbn());
                checkedBookes.add(Objects.requireNonNullElse(existingBook, book));
            }
            updatedEvent.setBooks(checkedBookes);

            EventDao eventDao = new EventDaoImpl(entityManager);
            Event existingEvent = null;

            if (updatedEvent.getEventId() != null) {
                existingEvent = eventDao.readById(updatedEvent.getEventId());
            }

            if (existingEvent == null) {
                eventDao.create(updatedEvent);
            } else {
                eventDao.update(updatedEvent);
            }
            entityTransaction.commit();
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
        } finally {
            entityManager.close();
        }

    }

    @Override
    public void remove(Integer id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = null;

        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            EventDao eventDao = new EventDaoImpl(entityManager);
            Event existingEvent = eventDao.readById(id);
            eventDao.delete(existingEvent);
            entityTransaction.commit();
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }
}
