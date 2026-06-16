package at.fhburgenland.model;

import jakarta.persistence.*;

/**
 * Book Stock Log Entity - indicates if a book is currently in stock
 */
@Entity(name = "BookStockLog")
@Table(name = "book_stock_log")
public class BookStockLog {
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "isbn", nullable = false)
    Book book;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "book_location_id", nullable = false)
    BookLocation bookLocation;
    @Id
    @GeneratedValue
    @Column(name = "book_stock_log_id", updatable = false, nullable = false)
    private Integer bookStockLogId;
    @Column(name = "book_is_in_stock", nullable = false)
    private Boolean bookIsInStock;

    public Boolean getBookIsInStock() {
        return this.bookIsInStock;
    }

    public void setBookIsInStock(Boolean bookIsInStock) {
        this.bookIsInStock = bookIsInStock;
    }

    public BookLocation getBookLocation() {
        return this.bookLocation;
    }

    public void setBookLocation(BookLocation bookLocation) {
        this.bookLocation = bookLocation;
    }

    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
