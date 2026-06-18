package at.fhburgenland.model.repository.implementations;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.Event;
import at.fhburgenland.model.repository.interfaces.EventBookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class EventBookRepositoryImpl implements EventBookRepository {
    private final EntityManagerFactory entityManagerFactory;

    public EventBookRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    @Override
    public void addBookToEvent(Book book, Event event) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Event managedEvent = entityManager.find(Event.class, event.getEventId());
            Book managedBook = entityManager.find(Book.class, book.getIsbn());

            managedEvent.getBooks().add(managedBook);
            managedBook.getBookEvents().add(managedEvent);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        } finally {
            entityManager.close();
        }

    }

    @Override
    public void removeBookFromEvent(Book book, Event event) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Event managedEvent = entityManager.find(Event.class, event.getEventId());
            Book managedBook = entityManager.find(Book.class, book.getIsbn());

            managedEvent.getBooks().remove(managedBook);
            managedBook.getBookEvents().remove(managedEvent);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
        finally {
            entityManager.close();
        }

    }

    @Override
    public List<Book> getBooksFromEvent(Event event) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            Event managedEvent = entityManager.find(Event.class, event.getEventId());
            return List.copyOf(managedEvent.getBooks());
        } finally {
            entityManager.close();
        }
    }
}
