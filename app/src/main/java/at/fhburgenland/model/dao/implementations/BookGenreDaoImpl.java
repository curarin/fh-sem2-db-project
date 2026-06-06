package at.fhburgenland.model.dao.implementations;

import at.fhburgenland.model.dao.interfaces.BookGenreDao;
import at.fhburgenland.model.BookGenre;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class BookGenreDaoImpl implements BookGenreDao {
    private final EntityManager entityManager;
    private EntityTransaction entityTransaction = null;

    public BookGenreDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(BookGenre bookGenre) {
        entityManager.persist(bookGenre);
    }

    @Override
    public BookGenre readById(Integer bookGenreId) {
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
