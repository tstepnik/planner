package com.tstepnik.planner.domain.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private String login;
    private String firstName;
    private String lastName;
    private String email;
}
