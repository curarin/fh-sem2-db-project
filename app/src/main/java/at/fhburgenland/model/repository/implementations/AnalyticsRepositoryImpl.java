package at.fhburgenland.model.repository.implementations;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.BookStockLog;
import at.fhburgenland.model.Customer;
import at.fhburgenland.model.Event;
import at.fhburgenland.model.dto.CustomerAnalyticsDto;
import at.fhburgenland.model.repository.interfaces.AnalyticsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.ArrayList;
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
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<CustomerAnalyticsDto> customerAnalyticsDtoList = new ArrayList<>();

        try {
            String query = """
                    select
                        customer,
                        count(distinct bookCirculationLog.bookCirculationLogId),
                        count(distinct customerEventMap.event.eventId),
                        count(distinct bookCirculationLog.bookCirculationLogId) + count(distinct customerEventMap.event.eventId)
                    from
                        Customer customer
                    left join BookCirculationLog bookCirculationLog
                        on bookCirculationLog.customer = customer
                    left join CustomerEventMap customerEventMap
                        on customerEventMap.customer = customer
                    group by 1
                    having count(distinct bookCirculationLog.bookCirculationLogId) + count(distinct customerEventMap.event.eventId) >= :threshold
                    """;
            TypedQuery<Object[]> typedQuery = entityManager.createQuery(query, Object[].class);
            typedQuery.setParameter("threshold", threshold);
            for (Object[] object : typedQuery.getResultList()) {
                CustomerAnalyticsDto dto = new CustomerAnalyticsDto(
                        (Customer) object[0],
                        ((Long) object[1]).intValue(),
                        ((Long) object[2]).intValue(),
                        ((Long) object[3]).intValue()
                );
                customerAnalyticsDtoList.add(dto);
            }
            return customerAnalyticsDtoList;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Event> getEventsWithMoreThanAverageAttendantCount() {
        return List.of();
    }
}
