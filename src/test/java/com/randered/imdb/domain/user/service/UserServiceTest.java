package com.randered.imdb.domain.user.service;

import com.randered.imdb.base.BaseTest;
import com.randered.imdb.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserServiceTest extends BaseTest {

    @Autowired
    private UserService userService;

    private User user;
    private User admin;

    @BeforeEach
    public void before() {
        user = buildUser();
        userService.update(user);
        admin = buildAdmin();
        userService.update(admin);
    }

    @Test
    void findById() {
        final User test = userService.findUserById(user.getId()).orElse(null);
        if (test != null) {
            assertEquals(test.getUsername(), user.getUsername());
        }
    }

    @Test
    void findByUsername() {
        final User test = userService.findUserByUsername("vasko5");
        if (test != null) {
            assertEquals(test.getUsername(), user.getUsername());
        }
    }

    @Test
    void count() {
        assertEquals(4, userService.count());
        userService.update(buildUser("Test", "test", "test"));
        assertEquals(5, userService.count());
    }
}