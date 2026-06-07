package at.fhburgenland.model;

import jakarta.persistence.*;

@Entity(name = "BookGenre")
@Table(name = "book_genre")
public class BookGenre {
    @Id
    @GeneratedValue
    @Column(name = "book_genre_id", updatable = false, nullable = false)
    private Integer bookGenreId;

    @Column(name = "book_genre_name", nullable = false, unique = true)
    private String bookGenreName;

    public Integer getBookGenreId() {
        return this.bookGenreId;
    }

    public String getBookGenreName() {
        return this.bookGenreName;
    }

    public void setBookGenreName(String bookGenreName) {
        this.bookGenreName = bookGenreName;
    }
}
