package com.vashchenko.education.t1.task_1.service.dbImpl;

import com.vashchenko.education.t1.task_1.exception.UserAlreadyExistsException;
import com.vashchenko.education.t1.task_1.exception.UserIsNotFoundException;
import com.vashchenko.education.t1.task_1.mapper.UserMapper;
import com.vashchenko.education.t1.task_1.model.dto.request.UserDto;
import com.vashchenko.education.t1.task_1.model.entity.User;
import com.vashchenko.education.t1.task_1.repository.UserRepository;
import com.vashchenko.education.t1.task_1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JpaUserService implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto createUser(UserDto dtoToCreate) {
        if(!userRepository.existsByEmailIgnoreCase(dtoToCreate.email())){
            User updateUser = userMapper.toEntity(dtoToCreate);
            return userMapper.toDto(userRepository.save(updateUser));
        }
        else {
            throw new UserAlreadyExistsException(dtoToCreate.email());
        }
    }

    @Override
    public void updateUser(UserDto dtoToUpdate, UUID userId) {
        if(!userRepository.existsById(userId)){
            User updateUser = userMapper.toEntity(dtoToUpdate);
            updateUser.setId(userId);
            userRepository.save(updateUser);
        }
        else {
            throw new UserIsNotFoundException("id",userId);
        }
    }

    @Override
    public void deleteUserById(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserIsNotFoundException("id",userId)
        );

        userRepository.delete(user);
    }

    @Override
    public UserDto findUserByName(String name) {
        return userMapper.toDto(
                userRepository.findByName(name).orElseThrow(
                    () -> new UserIsNotFoundException("name",name)
                )
        );
    }

    @Override
    public List<UserDto> findAllUsers() {
        return userMapper.toDtoList(userRepository.findAll());
    }
}
