package com.randered.imdb.domain.user.repository;

import com.randered.imdb.domain.user.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Override
    <S extends User> Optional<S> findOne(Example<S> example);

    Optional<User> findByUsername(String username);
}
