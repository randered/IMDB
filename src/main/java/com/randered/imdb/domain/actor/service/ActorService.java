package com.randered.imdb.domain.actor.service;

import com.randered.imdb.domain.actor.actorDTO.ActorDto;
import com.randered.imdb.domain.actor.entity.Actor;
import com.randered.imdb.domain.actor.mapper.ActorMapper;
import com.randered.imdb.domain.actor.repository.ActorRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActorService {

    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;

    public void update(final Actor actor) {
        actorRepository.save(actor);
    }

    public void createActor(@NonNull final ActorDto actorDto) {
        final Actor actor = actorMapper.map(actorDto);
        actorRepository.save(actor);
    }

    public List<ActorDto> findAll() {
        return actorRepository.findAll().stream().map(actorMapper::map).collect(Collectors.toList());
    }

    public Optional<Actor> findById(final Integer id) {
        return actorRepository.findById(id);
    }

    public Optional<Actor> findByName(final String name) {
        return actorRepository.findByName(name);
    }
}
