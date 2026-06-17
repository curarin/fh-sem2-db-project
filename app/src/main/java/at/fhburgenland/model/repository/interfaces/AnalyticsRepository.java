package at.fhburgenland.model.repository.interfaces;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.BookStockLog;
import at.fhburgenland.model.Event;
import at.fhburgenland.model.dto.CustomerAnalyticsDto;
import at.fhburgenland.model.dto.EventAnalyticsDto;

import java.time.LocalDate;
import java.util.List;

public interface AnalyticsRepository {
    // Geben Sie alle von einem bestimmten Kunden ausgeliehenen Bücher inklusive
    // Ausleihdatum zurück, die in einer gewissen Zeitspanne ausgeliehen wurden.
    public List<Book> getBooksLoanedByCustomer(Integer customerId, LocalDate startDate, LocalDate endDate);

    // Geben Sie alle verfügbaren Exemplare eines bestimmten Buches
    // inklusive Standort (Regal und Stockwerk) aus.
    public List<BookStockLog> getBookStockLogByBookIsbn(String isbn);

    // Ermitteln Sie für jeden Kunden die Anzahl ausgeliehener Bücher, die Anzahl besuchter
    // Veranstaltungen und deren Gesamtaktivität (= Summe beider Werte). Geben Sie nur Kunden aus,
    // deren Gesamtaktivität über einem vorgegebenen Wert liegen.
    public List<CustomerAnalyticsDto> getActivityCountsPerCustomerByThreshold(Integer threshold);

    // Ermitteln Sie für jede Veranstaltung die Anzahl der Teilnehmer und geben Sie nur Veranstaltungen aus,
    // die mehr Teilnehmer als der Durchschnitt aller Veranstaltungen haben.
    public List<EventAnalyticsDto> getEventsWithMoreThanAverageAttendantCount();
}
