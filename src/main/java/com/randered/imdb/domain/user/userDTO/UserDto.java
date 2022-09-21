package com.randered.imdb.domain.user.userDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotEmpty
    private String username;

    @NotEmpty
    @Min(value = 5)
    @Max(value = 32)
    private String password;

    @NotEmpty
    private String fullName;
}
