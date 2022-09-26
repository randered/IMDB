package com.randered.imdb.domain.user.entity;

import com.randered.imdb.domain.base.BaseEntity;
import com.randered.imdb.domain.rating.entity.Rating;
import com.randered.imdb.domain.user.authentication.authority.Authority;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName;

    @ManyToOne
    @JoinColumn(name = "rating_id")
    private Rating rating;

    @Enumerated(EnumType.STRING)
    private Authority authority;
}
