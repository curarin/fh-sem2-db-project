package at.fhburgenland.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Event Entity - represents one event (e.g. reading, signing,...). One event can contain multiple books - and
 * one book can be part of many events. (M:N relationship)
 */
@Entity(name = "Event")
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue
    @Column(name = "event_id", updatable = false, nullable = false)
    private Integer eventId;

    @Column(name = "event_name", nullable = false)
    private String eventName;

    @Column(name = "event_starts_at_ts", nullable = false)
    private LocalDateTime eventStartsAtTs;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "book_event_map", joinColumns = @JoinColumn(name = "isbn"), inverseJoinColumns = @JoinColumn(name = "book_event_id"))
    private Set<Book> books = new HashSet<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "event_type_id", nullable = false)
    private EventType eventType;

    public Integer getEventId() {
        return this.eventId;
    }

    public LocalDateTime getEventStartsAtTs() {
        return this.eventStartsAtTs;
    }

    public void setEventStartsAtTs(LocalDateTime eventStartsAtTs) {
        this.eventStartsAtTs = eventStartsAtTs;
    }

    public String getEventName() {
        return this.eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Set<Book> getBooks() {
        return this.books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public EventType getEventType() {
        return this.eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
}
