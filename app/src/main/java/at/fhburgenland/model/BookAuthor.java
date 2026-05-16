package at.fhburgenland.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity(name="BookAuthor")
@Table(name="book_author")
public class BookAuthor {
    @Id
    @GeneratedValue
    @Column(name="book_author_id", updatable = false, nullable = false)
    private Integer bookAuthorId;

    @Column(name="book_author_name", nullable = false)
    private String bookAuthorName;

    @ManyToMany(mappedBy = "bookAuthors", fetch = FetchType.EAGER)
    Set<Book> books;

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
