package at.fhburgenland.model.dao.implementations;

import at.fhburgenland.model.dao.interfaces.BookGenreDao;
import at.fhburgenland.model.BookGenre;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

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
    public List<BookGenre> readByName(String bookGenre) {
        String query = "select genre from BookGenre as genre where genre.bookGenreName = :bookGenre";
        TypedQuery<BookGenre> typedGenreQuery = entityManager.createQuery(query, BookGenre.class);
        typedGenreQuery.setParameter("bookGenre", bookGenre);
        return typedGenreQuery.getResultList();
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
