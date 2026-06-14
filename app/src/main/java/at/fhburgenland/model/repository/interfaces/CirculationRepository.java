package at.fhburgenland.model.repository.interfaces;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.BookCirculationLog;
import at.fhburgenland.model.Customer;

import java.util.List;

public interface CirculationRepository {
    void borrowBook(Customer customer, Book book);
    void returnBook(int circulationLogId);
}
