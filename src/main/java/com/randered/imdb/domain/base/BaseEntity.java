package com.randered.imdb.domain.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BaseEntity other)) {
            return false; // null or other class
        }
        if (id != null) {
            return id.equals(other.id);
        }
        return super.equals(other);
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        }
        return super.hashCode();
    }
}
