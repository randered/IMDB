package com.randered.imdb.domain.user.service;

import com.randered.imdb.domain.user.entity.User;
import com.randered.imdb.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User update(final User user) {
        return userRepository.save(user);
    }

    public Optional<User> findById(final Integer id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByName(final String username) {
        return userRepository.findByUsername(username);
    }

    public int count() {
        return (int) userRepository.count();
    }
}
