package at.fhburgenland.model;

import jakarta.persistence.*;

/**
 * Book Location Entity - represents a physical location of a
 * physical copy of a book (whereas a book is represented by an ISBN)
 */
@Entity(name = "BookLocation")
@Table(name = "book_location")
public class BookLocation {
    @Id
    @GeneratedValue
    @Column(name = "book_location_id", updatable = false, nullable = false)
    private Integer bookLocationId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "book_location_floor_id", nullable = false)
    private BookLocationFloor bookLocationFloor;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "book_location_shelf_id", nullable = false)
    private BookLocationShelf bookLocationShelf;

    public Integer getBookLocationId() {
        return this.bookLocationId;
    }

    public BookLocationFloor getBookLocationFloor() {
        return this.bookLocationFloor;
    }

    public void setBookLocationFloor(BookLocationFloor bookLocationFloor) {
        this.bookLocationFloor = bookLocationFloor;
    }

    public BookLocationShelf getBookLocationShelf() {
        return this.bookLocationShelf;
    }

    public void setBookLocationShelf(BookLocationShelf bookLocationShelf) {
        this.bookLocationShelf = bookLocationShelf;
    }
}
