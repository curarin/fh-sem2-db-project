package at.fhburgenland.model.dao.implementations;

import at.fhburgenland.model.dao.interfaces.BookAuthorDao;
import at.fhburgenland.model.BookAuthor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class BookAuthorDaoImpl implements BookAuthorDao {

    private final EntityManager entityManager;
    public BookAuthorDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(BookAuthor bookAuthor) {
        entityManager.persist(bookAuthor);
    }

    @Override
    public BookAuthor readById(Integer bookAuthorId) {
        return entityManager.find(BookAuthor.class, bookAuthorId);
    }

    @Override
    public List<BookAuthor> readByName(String authorName) {
        String query = "select author from BookAuthor as author where lower(author.bookAuthorName) like lower(:bookAuthorName)";
        TypedQuery<BookAuthor> typedAuthorQuery = entityManager.createQuery(query, BookAuthor.class);
        typedAuthorQuery.setParameter("bookAuthorName", authorName);
        return typedAuthorQuery.getResultList();
    }

    @Override
    public void update(BookAuthor bookAuthor) {
        entityManager.merge(bookAuthor);
    }

    @Override
    public void delete(BookAuthor bookAuthor) {
        entityManager.remove(bookAuthor);
    }
}
