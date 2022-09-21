package com.randered.imdb.domain.movie.service;

import com.randered.imdb.domain.actor.service.ActorService;
import com.randered.imdb.domain.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository repository;
    private final ActorService actorService;

}
