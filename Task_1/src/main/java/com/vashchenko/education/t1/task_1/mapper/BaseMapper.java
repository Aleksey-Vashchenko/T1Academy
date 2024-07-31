package com.vashchenko.education.t1.task_1.mapper;

import java.util.Collection;
import java.util.List;

public interface BaseMapper<E, T> {

    E toEntity(T to);
    T toDto(E entity);
    List<T> toDtoList(Collection<E> entities);
}