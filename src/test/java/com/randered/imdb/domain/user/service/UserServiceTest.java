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
//
//    @Test
//    void update() {
//        assertEquals("Admin", admin.getFullName());
//        admin.setFullName("Test");
//        userService.update(admin);
//        assertEquals("Test", admin.getFullName());
//    }

    @Test
    void findById() {
        final User test = userService.findById(user.getId()).orElse(null);
        if (test != null) {
            assertEquals(test.getUsername(), user.getUsername());
        }
    }

    @Test
    void findByName() {
        final User test = userService.findByName("Vasko Vasilev").orElse(null);
        if (test != null) {
            assertEquals(test.getFullName(), user.getFullName());
        }
    }

    @Test
    void count() {
        assertEquals(4, userService.count());
        userService.update(buildUser("Test", "test", "test"));
        assertEquals(5, userService.count());
    }
}