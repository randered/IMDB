package com.randered.imdb.domain.movie.repository;

import com.randered.imdb.domain.movie.entity.Movie;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    Optional<Movie> findById(Integer id);

    Optional<Movie> findByName(String name);

    List<Movie> findAll();

    @EntityGraph(value = "movie")
    @Query(value = "SELECT m FROM Movie m " +
                   "WHERE (m.name LIKE %:name%) " +
                   "AND (m.year = :year) " +
                   "AND (m.genre LIKE %:genre%) ")
    List<Movie> getFilteredMovies(@Param("name") String name,
                                  @Param("year") Integer year,
                                  @Param("genre") String genre);

    @EntityGraph(value = "movie")
    @Query(value = "SELECT m FROM Movie m " +
                   "WHERE (m.name LIKE %:name%) " +
                   "AND (m.genre LIKE %:genre%) ")
    List<Movie> getFilteredMovies(@Param("name") String name,
                                  @Param("genre") String genre);

}
