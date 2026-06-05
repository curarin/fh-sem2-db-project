package at.fhburgenland.dao;

import at.fhburgenland.model.Book;
import jakarta.persistence.EntityManager;

public class BookDaoImpl implements BookDao {
    private final EntityManager entityManager;

    public BookDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(Book book) {
        entityManager.persist(book);
    }

    @Override
    public Book read(String bookIsbn) {
        return entityManager.find(Book.class, bookIsbn);
    }

    @Override
    public void update(Book book) {
        entityManager.merge(book);
    }

    @Override
    public void delete(Book book) {
        entityManager.remove(book);
    }
}
