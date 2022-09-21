package com.randered.imdb.bootStrap;

import com.randered.imdb.domain.actor.service.ActorService;
import com.randered.imdb.domain.movie.service.MovieService;
import com.randered.imdb.domain.rating.service.RatingService;
import com.randered.imdb.domain.role.entity.Role;
import com.randered.imdb.domain.role.service.RoleService;
import com.randered.imdb.domain.user.service.UserService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.Set;

@SpringComponent
@RequiredArgsConstructor
public class DataLoader {

    private static final String DEV_PROFILE = "dev";

    private final UserService userService;
    private final ActorService actorService;
    private final MovieService movieService;
    private final RatingService ratingService;
    private final Environment env;
    private final RoleService roleService;

    private Set<Role> roles;

    @PostConstruct
    private void initRoles() {
        roles = roleService.getAllRoles();
    }


    @Bean
    @Profile({"!test"})
    public CommandLineRunner loadData() {
        return loadDevData();
    }

    private CommandLineRunner loadDevData() {
        return args -> {
            final Logger logger = LoggerFactory.getLogger(getClass());
            if (userService.count() != 1L) {
                logger.info("Using existing database");
                return;
            }
            logger.info("Generating dev data");
        };
    }

    private Role getRoleName(final String roleName) {
        return roles.stream()
                .filter(role -> role.getName().equalsIgnoreCase(roleName))
                .findFirst()
                .orElse(null);
    }
}
