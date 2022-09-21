package com.randered.imdb.domain.actor.entity;

import com.randered.imdb.domain.base.BaseEntity;
import com.randered.imdb.domain.movie.entity.Movie;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "actors", uniqueConstraints = {
        @UniqueConstraint(name = "uk_actors_name", columnNames = {"name"})})
public class Actor extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "actors", fetch = FetchType.LAZY)
    private Set<Movie> movies;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return Objects.equals(name, actor.name) && Objects.equals(movies, actor.movies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, movies);
    }
}
