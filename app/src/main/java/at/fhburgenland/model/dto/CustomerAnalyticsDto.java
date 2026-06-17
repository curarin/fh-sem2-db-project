package at.fhburgenland.model.dto;

import at.fhburgenland.model.Customer;

/**
 * DTO for analytics query which holds specifically aggregated input from db
 *
 * @param customer             Customer object
 * @param countBooksAtLoan     Number of books at loan
 * @param countVisitedEvents   number of visited events
 * @param countTotalActivities sum of borrowed books + visited events
 */
public record CustomerAnalyticsDto(Customer customer, Integer countBooksAtLoan, Integer countVisitedEvents,
                                   Integer countTotalActivities) {
}