package com.tstepnik.planner.domain.user;

import org.mapstruct.Mapper;
import java.util.List;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO userToUserDTO(User user);
    List<UserDTO> userToUserDTO(List<User> users);

}
