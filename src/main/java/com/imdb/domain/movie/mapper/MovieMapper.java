package com.imdb.domain.movie.mapper;

import com.imdb.domain.movie.dto.MovieDto;
import com.imdb.domain.movie.entity.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieDto map(Movie movie);

    Movie map(MovieDto movieDto);
}
