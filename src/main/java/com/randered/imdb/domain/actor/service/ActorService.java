package com.randered.imdb.domain.actor.service;

import com.randered.imdb.domain.actor.entity.Actor;
import com.randered.imdb.domain.actor.repository.ActorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActorService {

    private final ActorRepository actorRepository;

    public void update(final Actor actor) {
        actorRepository.save(actor);
    }

    public Optional<Actor> findById(final Integer id) {
        return actorRepository.findById(id);
    }

    public Optional<Actor> findByName(final String name) {
        return actorRepository.findByName(name);
    }
}
