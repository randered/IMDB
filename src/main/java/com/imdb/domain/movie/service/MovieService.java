package com.imdb.domain.movie.service;

import com.imdb.domain.actor.mapper.ActorMapper;
import com.imdb.domain.movie.dto.MovieDto;
import com.imdb.domain.movie.entity.Movie;
import com.imdb.domain.movie.filter.MovieFilter;
import com.imdb.domain.movie.mapper.MovieMapper;
import com.imdb.domain.movie.repository.MovieRepository;
import com.imdb.exceptions.MovieNotFoundException;
import com.imdb.util.Request;
import com.imdb.util.file.FileUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final ActorMapper actorMapper;
    private final FileUtil fileUtil;

    public void update(final Movie movie) {
        movieRepository.save(movie);
    }

    public Movie createMovie(@NonNull final MovieDto movieDto) {
        final Movie movie = movieMapper.map(movieDto);
        movie.setActors(movie.getActors());
        movie.setRatings(new ArrayList<>());
        return movieRepository.save(movie);
    }

    public void updateMovie(@NonNull final MovieDto movieDto) {
        final Movie movie = movieRepository.findByName(movieDto.getName()).orElse(null);
        assert movie != null;
        setMovie(movieDto, movie);
        movieRepository.save(movie);
    }

    public void addImageToMovie(@NonNull final String movieName, @NonNull final MultipartFile file) {
        final Movie movie = movieRepository.findByName(movieName).orElse(null);
        if (movie != null) {
            movie.setImage(file.getName());
            movieRepository.save(movie);
            fileUtil.uploadImage(file);
        } else {
            throw new RuntimeException(String.format("Movie with name: %s not found", movieName));
        }
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
                .orElseThrow(MovieNotFoundException::new);
        movieRepository.delete(movie);
    }

    public Movie findById(final Integer id) {
        return movieRepository.findById(id).orElse(null);
    }

    public Movie findByName(final String name) {
        return movieRepository.findByName(name).orElse(null);
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
