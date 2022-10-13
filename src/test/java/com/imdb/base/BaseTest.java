package com.imdb.base;

import com.imdb.domain.actor.dto.ActorDto;
import com.imdb.domain.movie.dto.MovieDto;
import com.imdb.domain.movie.entity.Movie;
import com.imdb.domain.movie.service.MovieService;
import com.imdb.domain.rating.entity.Rating;
import com.imdb.domain.rating.service.RatingService;
import com.imdb.domain.role.Role;
import com.imdb.domain.user.entity.User;
import com.imdb.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Set;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public abstract class BaseTest {

    @Autowired
    protected UserService userService;

    @Autowired
    protected MovieService movieService;

    @Autowired
    protected RatingService ratingService;

    @BeforeEach
    public void initializeBeforeTest() {
        MockitoAnnotations.openMocks(this);
    }

    protected User buildUser(final String fullName, final String username, final String password) {
        return User.builder()
                .fullName(fullName)
                .username(username)
                .password(password)
                .role(Role.USER)
                .build();
    }

    protected MovieDto buildMovieDto() {
        final MovieDto movieDto = new MovieDto();
        movieDto.setActors(Set.of(
                new ActorDto("Ivan Ivanov"),
                new ActorDto("Georgi Petkov"),
                new ActorDto("Jorjomir")));
        movieDto.setYear(2018);
        movieDto.setTrailer("https://www.youtube.com/watch?v=V9JtoEGS05I");
        movieDto.setName("Movie");
        movieDto.setGenre("movie");
        movieDto.setAverageRating(4.5);
        return movieDto;
    }

    protected Movie buildMovie(final String name) {
        return Movie.builder()
                .name(name)
                .genre("Test")
                .ratings(new ArrayList<>())
                .averageRating(4.0)
                .year(2000)
                .build();
    }

    protected User buildUser() {
        return buildUser("Vasko Vasilev", "vasko5", "user");
    }

    protected Rating buildRating(final User user, final Movie movie) {
        return Rating.builder()
                .movie(movie)
                .user(user)
                .comment("testing")
                .rating(5.0)
                .build();
    }
}
