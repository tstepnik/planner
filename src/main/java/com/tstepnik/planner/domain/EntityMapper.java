package com.tstepnik.planner.domain;

import java.util.List;

public interface EntityMapper<T, R> {

    T toDto(R r);

    R toEntity(T t);

    List<T> toDto(List<R> r);

    List<R> toEntity(List<T> t);
}
