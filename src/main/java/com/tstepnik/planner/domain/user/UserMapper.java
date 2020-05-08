package com.tstepnik.planner.domain.user;

import com.tstepnik.planner.domain.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO,User> {
}

