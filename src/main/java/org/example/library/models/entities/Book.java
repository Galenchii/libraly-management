package org.example.library.models.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "books")
public class Book extends BaseEntity {

    private String title;
    private int publicationYear;
    private BigDecimal price;
    private boolean isAvailable;
    private String isbn;

    @ManyToOne
    @JsonManagedReference
    private Author author;

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "books_genres",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Genre> getGenre() {
        return genres;
    }

    public void setGenre(List<Genre> genre) {
        this.genres = genre;
    }
    @Override
    public String toString() {
        return String.format("Book{id=%d, title='%s', isbn='%s'}", getId(), getTitle(), getIsbn());
    }


}
