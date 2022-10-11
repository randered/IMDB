package com.randered.imdb.repository;

import com.randered.imdb.base.BaseTest;
import com.randered.imdb.domain.user.entity.User;
import com.randered.imdb.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRepositoryTest extends BaseTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void before(){
        user = buildUser();
        userService.update(user);
    }

    @Test
    void findById() {
        final User test = userRepository.findById(user.getId()).orElse(null);
        if (test != null) {
            assertEquals(test.getUsername(), user.getUsername());
        }
    }

    @Test
    void findByUsername() {
        final User test = userRepository.findByUsername("vasko5").orElse(null);
        if (test != null) {
            assertEquals(test.getUsername(), user.getUsername());
        }
    }
}
