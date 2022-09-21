package com.randered.imdb.domain.actor.service;

import com.randered.imdb.domain.user.entity.User;
import com.randered.imdb.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActorService {

    private final UserRepository userRepository;

    private User update(final User user) {
        return userRepository.save(user);
    }

    private Optional<User> findById(final Integer id) {
        return userRepository.findById(id);
    }

    private Optional<User> findByName(final String username) {
        return userRepository.findByUsername(username);
    }
}
