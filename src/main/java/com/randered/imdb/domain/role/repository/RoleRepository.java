package com.randered.imdb.domain.role.repository;

import com.randered.imdb.domain.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Override
    @Nonnull
    Optional<Role> findById(@Nonnull Integer id);

    @Nonnull
    List<Role> findAll();
}
