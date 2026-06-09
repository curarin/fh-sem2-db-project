package at.fhburgenland.model;

import jakarta.persistence.*;

/**
 * Book Stock Log Entity - indicates if a book is currently in stock
 */
@Entity(name = "BookStockLog")
@Table(name = "book_stock_log")
public class BookStockLog {
    @Id
    @GeneratedValue
    @Column(name = "book_stock_log_id", updatable = false, nullable = false)
    private Integer bookStockLogId;

    @Column(name = "book_is_in_stock", nullable = false, unique = true)
    private Boolean bookIsInStock;

    public Boolean getBookIsInStock() {
        return this.bookIsInStock;
    }

    public void setBookIsInStock(Boolean bookIsInStock) {
        this.bookIsInStock = bookIsInStock;
    }
}
