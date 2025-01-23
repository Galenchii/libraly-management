package org.example.library.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.example.library.models.enums.EGenre;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "genres")
public class Genre extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private EGenre genreType;


    public EGenre getGenreType() {
        return genreType;
    }

    public void setGenreType(EGenre genreType) {
        this.genreType = genreType;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "genreType=" + genreType +
                '}';
    }
}
