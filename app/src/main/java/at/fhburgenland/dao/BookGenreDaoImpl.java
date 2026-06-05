package at.fhburgenland.dao;

import at.fhburgenland.model.BookGenre;
import jakarta.persistence.EntityManager;

public class BookGenreDaoImpl implements BookGenreDao {
    private final EntityManager entityManager;

    public BookGenreDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public void create(BookGenre bookGenre) {
        entityManager.persist(bookGenre);
    }

    @Override
    public BookGenre read(Integer bookGenreId) {
        return entityManager.find(BookGenre.class, bookGenreId);
    }

    @Override
    public void update(BookGenre bookGenre) {
        entityManager.merge(bookGenre);
    }

    @Override
    public void delete(BookGenre bookGenre) {
        entityManager.remove(bookGenre);
    }
}
