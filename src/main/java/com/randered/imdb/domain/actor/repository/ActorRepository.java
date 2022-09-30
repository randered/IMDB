package com.randered.imdb.domain.actor.repository;

import com.randered.imdb.domain.actor.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
    Optional<Actor> findByName(String name);
}
