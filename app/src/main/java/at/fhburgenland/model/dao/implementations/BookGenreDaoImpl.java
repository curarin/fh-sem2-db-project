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
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.persist(bookGenre);
            entityTransaction.commit();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    @Override
    public BookGenre read(Integer bookGenreId) {
        return entityManager.find(BookGenre.class, bookGenreId);
    }

    @Override
    public void update(BookGenre bookGenre) {
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.merge(bookGenre);
            entityTransaction.commit();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(BookGenre bookGenre) {
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.remove(bookGenre);
            entityTransaction.commit();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }
}
