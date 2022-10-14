package com.imdb.domain.movie.entity;

import com.imdb.domain.actor.entity.Actor;
import com.imdb.domain.base.BaseEntity;
import com.imdb.domain.rating.entity.Rating;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "movies")
@NamedEntityGraph(
        name = "movie",
        attributeNodes = {
                @NamedAttributeNode(value = "ratings"),
                @NamedAttributeNode(value = "actors")
        }
)
public class Movie extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer year;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    private List<Rating> ratings;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private Set<Actor> actors;

    @Column(nullable = false)
    private String genre;

    private String image;

    @Transient
    private double averageRating;

    @PostLoad
    public void loadAverage() {
        averageRating = ratings.stream().mapToDouble(Rating::getRating).average().orElse(0);
    }

    private String trailer;

    public Movie() {
        this.ratings = new ArrayList<>();
        this.actors = new HashSet<>();
    }
}
