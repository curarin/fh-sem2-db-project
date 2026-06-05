package at.fhburgenland.dao.implementations;

import at.fhburgenland.dao.interfaces.BookGenreDao;
import at.fhburgenland.model.BookGenre;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class BookGenreDaoImpl implements BookGenreDao {
    private final EntityManager entityManager;

    public BookGenreDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(BookGenre bookGenre) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
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
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
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
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
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
