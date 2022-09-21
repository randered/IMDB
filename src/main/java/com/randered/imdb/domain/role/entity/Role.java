package com.randered.imdb.domain.role.entity;

import com.randered.imdb.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles", uniqueConstraints = @UniqueConstraint(name = "UK_roles_role_name", columnNames = "name"))
public class Role extends BaseEntity {

    @NotEmpty
    @Column(nullable = false, unique = true)
    private String name;

    @Override
    public String toString() {
        return getName();
    }

}
