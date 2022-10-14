package com.imdb.web;

import com.imdb.base.BaseMvcTest;
import com.imdb.domain.actor.dto.ActorDto;
import com.imdb.domain.movie.dto.MovieDto;
import com.imdb.domain.movie.service.MovieService;
import com.imdb.util.common.Constants;
import com.imdb.util.validation.ValidationResponse;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TestAdminMovieController extends BaseMvcTest {

    @MockBean
    protected MovieService movieService;

    @Test
    @WithMockUser(authorities = Constants.ADMIN)
    void createMovieTest() throws Exception {
        final ArgumentCaptor<MovieDto> movieDtoArgumentCaptor = ArgumentCaptor.forClass(MovieDto.class);
        final MovieDto movieDto = buildMovieDto();

        final var response = performPostRequest(Constants.ADMIN_CREATE_MOVIE, movieDto,
                status().isCreated());

        verify(movieService).createMovie(movieDtoArgumentCaptor.capture());

        final MovieDto capturedMovieDto = movieDtoArgumentCaptor.getValue();

        assertThat(response.getMessages()).contains(Constants.MOVIE_CREATED);
        AssertionsForClassTypes.assertThat(capturedMovieDto).usingRecursiveComparison().isEqualTo(movieDto);
    }

    @Test
    void testCreateMovieNotAuthenticated() throws Exception {
        performPostRequest(Constants.BASE_PATH + Constants.PATH_ADMIN, buildMovieDto(),
                status().isForbidden());
        verify(movieService, never()).updateMovie(any(MovieDto.class));
    }

    @Test
    @WithMockUser(authorities = Constants.ADMIN)
    void testDeleteMovie() throws Exception {
        final String integer = String.valueOf(ThreadLocalRandom.current().nextInt(1, 10 + 1));
        final ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        final LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", integer);

        final ValidationResponse response =
                performDeleteRequest(Constants.BASE_PATH + Constants.ADMIN_DELETE_MOVIE, status().isOk(), params);

        verify(movieService).deleteMovie(integerArgumentCaptor.capture());

        final Integer capturedInteger = integerArgumentCaptor.getValue();

        assertThat(response.getMessages()).contains(Constants.MOVIE_DELETED);
        assertThat(capturedInteger).isEqualTo(Integer.valueOf(integer));
    }

    @Test
    @WithMockUser(authorities = Constants.ADMIN)
    void testUpdateMovie() throws Exception {
        final ArgumentCaptor<MovieDto> movieDtoArgumentCaptor = ArgumentCaptor.forClass(MovieDto.class);
        final MovieDto movieDto = buildMovieDto();

        final var response =
                performPutRequest(Constants.BASE_PATH + Constants.PATH_ADMIN, movieDto, status().isOk());

        verify(movieService).updateMovie(movieDtoArgumentCaptor.capture());

        final MovieDto capturedMovieDto = movieDtoArgumentCaptor.getValue();

        assertThat(response.getMessages()).contains(Constants.MOVIE_UPDATED);
        assertThat(capturedMovieDto).usingRecursiveComparison().isEqualTo(movieDto);
    }

    @Test
    @WithMockUser(authorities = Constants.ADMIN)
    void testUploadImage() throws Exception {
        final String movieName = "Thor";
        final ArgumentCaptor<MultipartFile> fileArgumentCaptor = ArgumentCaptor.forClass(MultipartFile.class);
        final ArgumentCaptor<String> movieNameArgumentCaptor = ArgumentCaptor.forClass(String.class);

        final MockMultipartFile file = new MockMultipartFile("image", "hello.txt",
                MediaType.IMAGE_JPEG_VALUE, "Hello".getBytes());
        final LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("movieName", movieName);

        final ValidationResponse response = performPostFileRequest(Constants.BASE_PATH + Constants.PATH_ADMIN + Constants.IMAGE_MOVIE, file.getName(),
                file, params, status().isCreated());

        verify(movieService).addImageToMovie(movieNameArgumentCaptor.capture(), fileArgumentCaptor.capture());

        final MultipartFile capturedFile = fileArgumentCaptor.getValue();
        final String capturedMovieName = movieNameArgumentCaptor.getValue();

        assertThat(response.getMessages()).contains(Constants.SUCCESSFUL_IMAGE_UPLOAD);
        AssertionsForClassTypes.assertThat(file.getBytes()).isEqualTo(capturedFile.getBytes());
        assertThat(movieName).isEqualTo(capturedMovieName);
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
}