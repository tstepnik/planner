package com.tstepnik.planner.domain.user.userregistrationdto;

import com.tstepnik.planner.domain.EntityMapper;
import com.tstepnik.planner.domain.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterUserMapper extends EntityMapper<UserRegisterDTO,User> {
}
