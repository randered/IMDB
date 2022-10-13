package com.imdb.domain.actor.repository;

import com.imdb.domain.actor.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
    Optional<Actor> findByName(String name);
}
