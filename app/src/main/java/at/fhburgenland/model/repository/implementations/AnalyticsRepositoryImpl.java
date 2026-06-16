package at.fhburgenland.model.repository.implementations;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.BookStockLog;
import at.fhburgenland.model.Customer;
import at.fhburgenland.model.Event;
import at.fhburgenland.model.dto.CustomerAnalyticsDto;
import at.fhburgenland.model.repository.interfaces.AnalyticsRepository;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class AnalyticsRepositoryImpl implements AnalyticsRepository {
    private final EntityManagerFactory entityManagerFactory;

    public AnalyticsRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    @Override
    public List<Book> getBooksLoanedByCustomer(Customer customer) {
        return List.of();
    }

    @Override
    public List<BookStockLog> getBookStockLogByBook(Book book) {
        return List.of();
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
