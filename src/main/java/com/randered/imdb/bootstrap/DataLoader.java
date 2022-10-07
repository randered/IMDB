package com.randered.imdb.bootstrap;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.randered.imdb.domain.actor.entity.Actor;
import com.randered.imdb.domain.actor.service.ActorService;
import com.randered.imdb.domain.role.Role;
import com.randered.imdb.domain.movie.entity.Movie;
import com.randered.imdb.domain.movie.service.MovieService;
import com.randered.imdb.domain.rating.entity.Rating;
import com.randered.imdb.domain.rating.service.RatingService;
import com.randered.imdb.domain.user.entity.User;
import com.randered.imdb.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataLoader {

    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "da0vsli58",
            "api_key", "572576397362646",
            "api_secret", "p8y74iFVm0OyQ3XNgigeeSs-Y4w"));

    private final UserService userService;
    private final ActorService actorService;
    private final MovieService movieService;
    private final RatingService ratingService;
    private final PasswordEncoder passwordEncoder;
    private final Environment env;


    @Bean
    @Profile({"!test"})
    public CommandLineRunner loadData() {
        return loadDevData();
    }

    private CommandLineRunner loadDevData() {
        return args -> {
            final Logger logger = LoggerFactory.getLogger(getClass());
            if (userService.count() > 0) {
                logger.info("Using existing database");
                return;
            }
            logger.info("Generating dev data");

            logger.info("Loading User and Admin");
            User admin = User.builder()
                    .fullName("Admin Adminkov")
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .role(Role.ADMIN)
                    .build();
            userService.update(admin);

            User user = User.builder()
                    .fullName("Petko Petkov")
                    .username("user")
                    .password(passwordEncoder.encode("user"))
                    .role(Role.USER)
                    .build();
            userService.update(user);

            logger.info("Loading Movies");
            Movie thor = Movie.builder()
                    .name("Thor")
                    .actors(Set.of())
                    .ratings(List.of())
                    .genre("Action")
                    .trailer("https://www.youtube.com/watch?v=JOddp-nlNvQ")
                    .year(2011)
                    .build();
            movieService.update(thor);

            Movie ironMan = Movie.builder()
                    .name("Iron Man")
                    .actors(Set.of())
                    .ratings(List.of())
                    .genre("Fantastic")
                    .trailer("https://www.youtube.com/watch?v=8ugaeA-nMTc")
                    .year(2008)
                    .build();
            movieService.update(ironMan);

            logger.info("Loading Actors");
            Actor actor = Actor.builder()
                    .name("Robert Downey Jr.")
                    .movies(Set.of(ironMan))
                    .build();
            actorService.update(actor);

            Actor actor2 = Actor.builder()
                    .name("Chris Hemsworth")
                    .movies(Set.of(thor))
                    .build();
            actorService.update(actor2);

            logger.info("Loading Ratings");
            Rating ratingThor = Rating.builder()
                    .user(user)
                    .rating(4.5)
                    .movie(thor)
                    .comment("test")
                    .build();
            ratingService.update(ratingThor);

            thor.setRatings(List.of(ratingThor));
            thor.setActors(Set.of(actor2));
            movieService.update(thor);

            Rating ratingIronMan = Rating.builder()
                    .user(user)
                    .rating(5.0)
                    .movie(ironMan)
                    .comment("Good")
                    .build();
            ratingService.update(ratingIronMan);

            ironMan.setRatings(List.of(ratingIronMan));
            ironMan.setActors(Set.of(actor));
            movieService.update(ironMan);
        };
    }
}
