package com.example.CRM.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private int id;

    @NotEmpty
    @Size(min = 4, message = "Username must be minimum of 4 characters !")
    private String name;

    @Email(message = "Email address is not valid !")
    private String email;

    @NotEmpty
    @Size(min = 6, max = 10, message = "Password must be min 6 and max 10 characters !")
    private String password;

    @NotEmpty
    private String departmentName;
    @NotEmpty
    private String position;
    private Set<RoleDto> roles = new HashSet<>();
}
