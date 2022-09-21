package com.randered.imdb.domain.actor.repository;

import com.randered.imdb.domain.actor.entity.Actor;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public interface ActorRepository extends JpaRepository<Actor, Integer> {

    @NonNull
    Optional<Actor> findByName(@NonNull String name);

    @Override
    @Nonnull
    Optional<Actor> findById(@Nonnull Integer id);

    @Nonnull
    List<Actor> findAll();
}
