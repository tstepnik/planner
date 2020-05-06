package com.tstepnik.planner.domain;

import java.util.List;

public interface EntityMapper<T, R> {

    T toDto(R r);

    R toEntity(T userDto);

    List<T> toDto(List<R> users);

    List<R> toEntity(List<T> usersDto);
}
