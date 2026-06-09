package at.fhburgenland.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Book Entity - represents one physical copy of a book defined by its ISBN.
 */
@Entity(name = "Book")
@Table(name = "book")
public class Book {

    /**
     * Each physical book copy can participate in one or many events
     */
    @ManyToMany(mappedBy = "books", fetch = FetchType.EAGER)
    Set<Event> events = new HashSet<>();
    /**
     * Unique identifier of a physical book copy
     */
    @Id
    @Column(name = "isbn", updatable = false, nullable = false)
    private String isbn;
    /**
     * Title of the book as user defined String
     */
    @Column(name = "book_title", nullable = false)
    private String bookTitle;
    /**
     * One or many authors of each physical book copy
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "author_book_map", joinColumns = @JoinColumn(name = "isbn"), inverseJoinColumns = @JoinColumn(name = "book_author_id"))
    private Set<BookAuthor> bookAuthors = new HashSet<>();
    /**
     * Genre category of each physical book copy
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "book_genre_id", nullable = false)
    private BookGenre bookGenre;

    /**
     * Publisher responsible for publishing the physical book copy
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "book_publisher_id", nullable = false)
    private BookPublisher bookPublisher;

    /**
     * Indicator whether a book is currently in stock
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "book_stock_log_id", nullable = false)
    private BookStockLog bookStockLog;

    public Book(String isbn, String bookTitle, BookGenre bookGenre, BookPublisher bookPublisher, BookStockLog bockIsInStock) {
        this.isbn = isbn;
        this.bookTitle = bookTitle;
        this.bookGenre = bookGenre;
        this.bookPublisher = bookPublisher;
        this.bookStockLog = bockIsInStock;
    }

    public Book() {
    }

    /**
     * Returns book title
     *
     * @return Title of the current book
     */
    public String getBookTitle() {
        return this.bookTitle;
    }

    /**
     * Sets a new book title
     *
     * @param bookTitle New title of the book
     */
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    /**
     * Returns the book genre
     *
     * @return BookGenre
     */
    public BookGenre getBookGenre() {
        return this.bookGenre;
    }

    /**
     * Sets the book Genre
     *
     * @param bookGenre Genre of the book
     */
    public void setBookGenre(BookGenre bookGenre) {
        this.bookGenre = bookGenre;
    }

    /**
     * Returns the book publisher
     *
     * @return BookPublisher
     */
    public BookPublisher getBookPublisher() {
        return this.bookPublisher;
    }

    /**
     * Sets the books publisher
     *
     * @param bookPublisher Publisher name
     */
    public void setBookPublisher(BookPublisher bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    /**
     * Returns the Set of book authors (1...n)
     *
     * @return BookAuthors
     */
    public Set<BookAuthor> getBookAuthors() {
        return this.bookAuthors;
    }

    /**
     * Sets the Book Authors
     *
     * @param bookAuthors Set of book Authors
     */
    public void setBookAuthors(Set<BookAuthor> bookAuthors) {
        this.bookAuthors = new HashSet<>(bookAuthors);
    }

    /**
     * Returns the set of events, where the book was part of it (m...n)
     *
     * @return Events
     */
    public Set<Event> getBookEvents() {
        return this.events;
    }

    /**
     * Sets the book events
     *
     * @param events Set of events
     */
    public void setBookEvents(Set<Event> events) {
        this.events = new HashSet<>(events);
    }

    /**
     * Returns International Standard Book Number (ISBN)
     *
     * @return ISBN
     */
    public String getIsbn() {
        return this.isbn;
    }

    /**
     * Sets the International Standard Book Number (ISBN)
     *
     * @param isbn ISBN
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Gets the current book stock state
     * @return
     */
    public BookStockLog getBookStockLog() {
        return this.bookStockLog;
    }

    /**
     * Sets a new Book stock state
     * @param bookStockLog Indicator if a book is currently in stock or not
     */
    public void setBookStockLog(BookStockLog bookStockLog) {
        this.bookStockLog = bookStockLog;
    }

}
