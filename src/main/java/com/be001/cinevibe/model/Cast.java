package com.be001.cinevibe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@ToString
@Entity
@Table(name = "casts")
public class Cast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String imageUrl;
    private String biography;

    @ManyToMany(mappedBy = "cast")
    @ToString.Exclude
    Set<Movie> movies = new HashSet<>();

    public Cast(Long id, String name, String imageUrl, String biography, Set<Movie> movies) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.biography = biography;
        this.movies = movies;
    }

    public Cast() {
    }
}
