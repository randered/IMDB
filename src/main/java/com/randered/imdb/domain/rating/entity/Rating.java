package com.randered.imdb.domain.rating.entity;

import com.randered.imdb.domain.base.BaseEntity;
import com.randered.imdb.domain.movie.entity.Movie;
import com.randered.imdb.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ratings",
        uniqueConstraints = @UniqueConstraint(name = "uk_ratings_user_movie", columnNames = {"user_id", "movie_id"}))
public class Rating extends BaseEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_ratings_users"))
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", foreignKey = @ForeignKey(name = "fk_ratings_movies"))
    private Movie movie;

    @Column(nullable = false)
    private double rating;

    private String comment;
}
