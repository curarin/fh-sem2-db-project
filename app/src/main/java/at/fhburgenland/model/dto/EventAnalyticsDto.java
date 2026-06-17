package at.fhburgenland.model.dto;

import at.fhburgenland.model.Event;

/**
 * DTO for analytics query which holds specifically aggregated input from db
 * @param event Concrete event
 * @param participantCount number of participants
 */
public record EventAnalyticsDto(Event event, int participantCount) {}
