package com.vashchenko.education.t1.task_1.service;

import com.vashchenko.education.t1.task_1.model.dto.request.UserDto;
import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDto createUser(UserDto dtoToCreate);
    void updateUser(UserDto dtoToUpdate, UUID id);
    void deleteUserById(UUID userId);
    UserDto findUserByName(String name);
    List<UserDto> findAllUsers();
}
