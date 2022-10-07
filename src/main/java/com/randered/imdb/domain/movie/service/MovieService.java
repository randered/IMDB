package com.randered.imdb.domain.movie.service;

import com.randered.imdb.domain.actor.mapper.ActorMapper;
import com.randered.imdb.domain.movie.entity.Movie;
import com.randered.imdb.domain.movie.filter.MovieFilter;
import com.randered.imdb.domain.movie.mapper.MovieMapper;
import com.randered.imdb.domain.movie.movieDTO.MovieDto;
import com.randered.imdb.domain.movie.repository.MovieRepository;
import com.randered.imdb.util.Request;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final ActorMapper actorMapper;

    public void update(final Movie movie) {
        movieRepository.save(movie);
    }

    public Movie saveOrGet(final Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie createMovie(@NonNull final MovieDto movieDto) {
        final Movie movie = movieMapper.map(movieDto);
        movie.setActors(movie.getActors());
        return movieRepository.save(movie);
    }

    public void updateMovie(@NonNull final MovieDto movieDto) {
        final Movie movie = movieRepository.findByName(movieDto.getName()).orElse(null);
        assert movie != null;
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
                .map(actorMapper::map)
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

    public Page<MovieDto> getFilteredMovies(final Request<MovieFilter> request) {
        final List<Movie> filteredMovies;
        final MovieFilter filter = request.getFilter();
        final Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize());
        if (request.getFilter().getYear() != null) {
            filteredMovies = movieRepository.getFilteredMovies(
                    checkIfNull(filter.getName()),
                    filter.getYear(),
                    checkIfNull(filter.getGenre()));
        } else {
            filteredMovies = movieRepository.getFilteredMovies(
                    checkIfNull(filter.getName()),
                    checkIfNull(filter.getGenre()));
        }

        final List<MovieDto> filteredMovieDtos = filteredMovies.stream().map(movieMapper::map).toList();
        return new PageImpl<>(filteredMovieDtos, pageable, filteredMovies.size());
    }

    protected static String checkIfNull(final String value) {
        return value == null ? "" : value;
    }
}
