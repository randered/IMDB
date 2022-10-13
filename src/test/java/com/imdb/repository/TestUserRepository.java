package com.imdb.repository;

import com.imdb.domain.user.entity.User;
import com.imdb.base.BaseTest;
import com.imdb.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TestUserRepository extends BaseTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void before() {
        user = buildUser();
        userService.update(user);
    }

    @Test
    void findById() {
        final Optional<User> test = userRepository.findById(user.getId());
        assertTrue(test.isPresent());
        assertEquals(test.get().getUsername(), user.getUsername());
    }

    @Test
    void findByUsername() {
        final User test = userRepository.findByUsername("vasko5").orElse(null);
        if (test != null) {
            assertEquals(test.getUsername(), user.getUsername());
        }
    }
}
