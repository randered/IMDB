package com.randered.imdb.domain.user.userDTO;

import com.randered.imdb.domain.role.Role;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserDto {

    @NotEmpty
    @Size(min = 2, message = "Username should be longer than 2 characters.")
    private String username;

    @NotEmpty
    @Size(min = 5, message = "Password should be longer than 4 characters and shorter than 32", max = 32)
    private String password;

    @NotEmpty
    private String fullName;

    private Role role;
}
