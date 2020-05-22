package com.tstepnik.planner.domain.mappers;

import com.tstepnik.planner.domain.user.User;
import com.tstepnik.planner.domain.user.UserRegisterDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRegisterMapper extends EntityMapper<UserRegisterDTO, User> {
}
