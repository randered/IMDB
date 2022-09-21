package com.randered.imdb.domain.user.repository;

import com.randered.imdb.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Nonnull
    Optional<User> findById(@Nonnull Integer id);

    Optional<User> findByUsername(String username);

    @Nonnull
    List<User> findAll();
}
