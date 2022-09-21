package com.randered.imdb.domain.role.service;

import com.randered.imdb.domain.role.entity.Role;
import com.randered.imdb.domain.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;

    public Set<Role> getAllRoles() {
        return new HashSet<>(repository.findAll());
    }
}
