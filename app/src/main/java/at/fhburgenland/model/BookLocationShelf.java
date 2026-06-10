package at.fhburgenland.model;

import jakarta.persistence.*;

/**
 * Location shelf entity, represents in which shelf a
 * physical copy of a book is located
 */
@Entity(name = "BookLocationShelf")
@Table(name = "book_location_shelf")
public class BookLocationShelf {

    @Id
    @GeneratedValue
    @Column(name = "book_location_shelf_id", updatable = false, nullable = false)
    private Integer bookLocationShelfId;

    @Column(name = "book_location_floor_number", nullable = false, unique = true)
    private Integer bookLocationShelfNumber;

    public Integer getBookLocationShelfId() {
        return this.bookLocationShelfId;
    }

    public Integer getBookLocationShelfNumber() {
        return this.bookLocationShelfNumber;
    }

    public void setBookLocationShelfNumber(Integer bookLocationShelfNumber) {
        this.bookLocationShelfNumber = bookLocationShelfNumber;
    }
}
