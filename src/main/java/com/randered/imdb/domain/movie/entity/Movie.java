package com.randered.imdb.domain.movie.entity;

import com.randered.imdb.domain.actor.entity.Actor;
import com.randered.imdb.domain.base.BaseEntity;
import com.randered.imdb.domain.rating.entity.Rating;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(
        name = "movie",
        attributeNodes = {
                @NamedAttributeNode(value = "actors"),
                @NamedAttributeNode(value = "ratings")
        }
)
@Table(name = "movies")
public class Movie extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private int year;

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
    private void loadAverage() {
        averageRating = ratings.stream().mapToDouble(Rating::getRating).average().orElse(0);
    }

    private String trailer;
}
