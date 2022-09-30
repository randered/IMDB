package com.randered.imdb.domain.user.userDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotEmpty
    @Size(min = 2, message = "Username should be longer than 2 characters.")
    private String username;

    @NotEmpty
    @Size(min = 5, message = "Password should be longer than 4 characters and shorter than 32", max = 32)
    private String password;

    @NotEmpty
    private String fullName;
}
