package at.fhburgenland.model;

import jakarta.persistence.*;

import java.util.Set;

/**
 * Book Author entity - m:n to books
 */
@Entity(name = "BookAuthor")
@Table(name = "book_author")
public class BookAuthor {
    @ManyToMany(mappedBy = "bookAuthors", fetch = FetchType.EAGER)
    Set<Book> books;
    @Id
    @GeneratedValue
    @Column(name = "book_author_id", updatable = false, nullable = false)
    private Integer bookAuthorId;
    @Column(name = "book_author_name", nullable = false, unique = true)
    private String bookAuthorName;

    public Integer getBookAuthorId() {
        return this.bookAuthorId;
    }

    public String getBookAuthorName() {
        return this.bookAuthorName;
    }

    public void setBookAuthorName(String bookAuthorName) {
        this.bookAuthorName = bookAuthorName;
    }

}
