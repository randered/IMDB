package com.randered.imdb.base;

import com.randered.imdb.domain.actor.actorDTO.ActorDto;
import com.randered.imdb.domain.movie.movieDTO.MovieDto;
import com.randered.imdb.domain.user.authentication.authority.Authority;
import com.randered.imdb.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@SpringBootTest
@Transactional
public abstract class BaseTest {

    @BeforeEach
    public void initializeBeforeTest() {
        MockitoAnnotations.openMocks(this);

    }

    protected User buildUser(final String fullName, final String username, final String password) {
        return User.builder()
                .fullName(fullName)
                .username(username)
                .password(password)
                .authority(Authority.USER)
                .build();
    }

    protected User buildAdmin(final String fullName, final String username, final String password) {
        return User.builder()
                .fullName(fullName)
                .username(username)
                .password(password)
                .authority(Authority.ADMIN)
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
        movieDto.setName("1000 puti");
        movieDto.setGenre("musical");
        return movieDto;
    }

    protected User buildUser() {
        return buildUser("Vasko Vasilev", "vasko5", "user");
    }

    protected User buildAdmin() {
        return buildAdmin("Admin", "admin", "admin");
    }
}
