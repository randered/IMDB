package com.randered.imdb.domain.rating.repository;

import com.randered.imdb.domain.rating.entity.Rating;
import com.randered.imdb.domain.user.entity.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    @NonNull
    Optional<Rating> findByUser(@NonNull User user);

    @Override
    @Nonnull
    Optional<Rating> findById(@Nonnull Integer id);
}
