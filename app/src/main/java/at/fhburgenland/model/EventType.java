package at.fhburgenland.model;

import jakarta.persistence.*;

/**
 * Event Type Entity - 1:n relationship with books
 */
@Entity(name = "EventType")
@Table(name = "event_type")
public class EventType {
    @Id
    @GeneratedValue
    @Column(name = "event_type_id", updatable = false, nullable = false)
    private Integer eventTypeId;

    @Column(name = "event_type_name", nullable = false, unique = true)
    private String eventTypeName;

    public Integer getEventTypeId() {
        return this.eventTypeId;
    }

    public String getEventTypeName() {
        return this.eventTypeName;
    }

    public void setEventTypeName(String eventTypeName) {
        this.eventTypeName = eventTypeName;
    }
}
