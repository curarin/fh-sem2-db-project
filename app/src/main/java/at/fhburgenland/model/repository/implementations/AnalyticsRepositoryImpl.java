package at.fhburgenland.model.repository.implementations;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.BookStockLog;
import at.fhburgenland.model.Event;
import at.fhburgenland.model.dto.CustomerAnalyticsDto;
import at.fhburgenland.model.repository.interfaces.AnalyticsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class AnalyticsRepositoryImpl implements AnalyticsRepository {
    private final EntityManagerFactory entityManagerFactory;

    public AnalyticsRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<Book> getBooksLoanedByCustomer(Integer customerId, LocalDate startDate, LocalDate endDate) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            String query = """
                    select
                        book
                    from BookCirculationLog bookCirculationLog
                    join bookCirculationLog.fkStockid bookStockLog
                    join bookStockLog.book book
                    where
                        bookCirculationLog.customer.customerId = :customerId
                        and bookCirculationLog.loanStartsAtDate >= :startDate
                        and bookCirculationLog.loanEndsAtDate <= :endDate
                    """;
            TypedQuery<Book> bookQuery = entityManager.createQuery(query, Book.class);
            bookQuery.setParameter("customerId", customerId);
            bookQuery.setParameter("startDate", startDate);
            bookQuery.setParameter("endDate", endDate);
            return bookQuery.getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<BookStockLog> getBookStockLogByBookIsbn(String isbn) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            String query = """
                    select
                        bookStockLog
                    from BookStockLog bookStockLog
                    where
                        bookStockLog.book.isbn = :isbn
                    """;
            TypedQuery<BookStockLog> bookStockLogQuery = entityManager.createQuery(query, BookStockLog.class);
            bookStockLogQuery.setParameter("isbn", isbn);
            return bookStockLogQuery.getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<CustomerAnalyticsDto> getActivityCountsPerCustomerByThreshold(Integer threshold) {
        return List.of();
    }

    @Override
    public List<Event> getEventsWithMoreThanAverageAttendantCount() {
        return List.of();
    }
}
