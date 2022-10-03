package com.randered.imdb.domain.movie.service;

import com.randered.imdb.domain.actor.entity.Actor;
import com.randered.imdb.domain.actor.service.ActorService;
import com.randered.imdb.domain.movie.entity.Movie;
import com.randered.imdb.domain.movie.movieDTO.MovieDto;
import com.randered.imdb.domain.movie.repository.MovieRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieService {
    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;
    private final ActorService actorService;

    public void update(final Movie movie) {
        movieRepository.save(movie);
    }

    public void createMovie(@NonNull final MovieDto movieDto) {
        final Movie movie = modelMapper.map(movieDto, Movie.class);
        movie.setActors(movie.getActors());
        movieRepository.save(movie);
    }

    public void updateMovie(@NonNull final MovieDto movieDto) {
        final Movie movie = movieRepository.findByName(movieDto.getName())
                .orElseThrow();

        setMovie(movieDto, movie);
        movieRepository.save(movie);
    }


    private void setMovie(final MovieDto movieDto, final Movie movie) {

        movie.setName(movieDto.getName());
        movie.setGenre(movieDto.getGenre());
        movie.setYear(movieDto.getYear());
        if (null != movieDto.getTrailer()) {
            movie.setTrailer(movieDto.getTrailer());
        }

        movie.setActors(movieDto
                .getActors()
                .stream()
                .map(actorDto -> modelMapper.map(actorDto, Actor.class))
                .collect(Collectors.toSet()));
    }

    public void deleteMovie(@NonNull final Integer id) {
        final Movie movie = movieRepository.findById(id)
                .orElseThrow();
        movieRepository.delete(movie);
    }

    public Optional<Movie> findById(final Integer id) {
        return movieRepository.findById(id);
    }

    public Optional<Movie> findByName(final String username) {
        return movieRepository.findByName(username);
    }

//
//    private Set<Actor> getActors(final Set<Actor> actors) {
//        final Set<Actor> tempActors = new HashSet<>();
//        for (Actor actor : actors) {
//            tempActors.add(actorService.findByName(actor.getName()).orElse(null));
//        }
//        return tempActors;
//    }

}
