package com.vashchenko.education.t1.task_1.mapper;

import com.vashchenko.education.t1.task_1.model.dto.request.UserDto;
import com.vashchenko.education.t1.task_1.model.entity.User;
import org.mapstruct.Mapper;
import java.util.Collection;
import java.util.List;

@Mapper(config = MapStructConfig.class)
public interface UserMapper extends BaseMapper<User, UserDto> {

    @Override
    User toEntity(UserDto to);

    @Override
    UserDto toDto(User entity);

    @Override
    List<UserDto> toDtoList(Collection<User> users);
}