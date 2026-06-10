package at.fhburgenland.model;

import jakarta.persistence.*;

/**
 * Location Floor entity, represents on which floor
 * the physical copy of the book is located
 */
@Entity(name = "BookLocationFloor")
@Table(name = "book_location_floor")
public class BookLocationFloor {

    @Id
    @GeneratedValue
    @Column(name = "book_location_floor_id", updatable = false, nullable = false)
    private Integer bookLocationFloorId;

    @Column(name = "book_location_floor_number", nullable = false, unique = true)
    private Integer bookLocationFloorNumber;

    public Integer getBookLocationFloorId() {
        return this.bookLocationFloorId;
    }

    public void setBookLocationFloorNumber(Integer bookLocationFloorNumber) {
        this.bookLocationFloorNumber = bookLocationFloorNumber;
    }

    public Integer getBookLocationFloorNumber() {
        return this.bookLocationFloorNumber;
    }
}
