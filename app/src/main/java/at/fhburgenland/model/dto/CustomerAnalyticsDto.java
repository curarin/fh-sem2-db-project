package at.fhburgenland.model.dto;

import at.fhburgenland.model.Customer;

/**
 * DTO for analytics query which holds specifically aggregated input from db
 *
 * @param customer           Customer object
 * @param countLoanedBooks   Number of books at loan
 * @param countVisitedEvents number of visited events
 */
public record CustomerAnalyticsDto(Customer customer, Integer countLoanedBooks, Integer countVisitedEvents) {
}