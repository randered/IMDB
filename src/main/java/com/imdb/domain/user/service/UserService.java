package com.imdb.domain.user.service;

import com.imdb.domain.user.entity.User;
import com.imdb.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User update(final User user) {
        return userRepository.save(user);
    }

    public Optional<User> findUserById(final Integer id) {
        return userRepository.findById(id);
    }

    public User findUserByUsername(final String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public int count() {
        return (int) userRepository.count();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
