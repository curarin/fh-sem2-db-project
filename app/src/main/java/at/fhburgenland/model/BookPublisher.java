package at.fhburgenland.model;

import jakarta.persistence.*;

/**
 * Book Publisher entity - 1:n to Book entity
 */
@Entity(name="BookPublisher")
@Table(name="book_publisher")
public class BookPublisher {
    @Id
    @GeneratedValue
    @Column(name="book_publisher_id", updatable = false, nullable = false)
    private Integer bookPublisherId;

    @Column(name="book_publisher_name", nullable = false, unique = true)
    private String bookPublisherName;

    public String getBookPublisherName() {
        return this.bookPublisherName;
    }

    public void setBookPublisherName(String bookPublisherName) {
        this.bookPublisherName = bookPublisherName;
    }

    public Integer getBookPublisherId() {
        return this.bookPublisherId;
    }
}
