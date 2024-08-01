package com.vashchenko.education.t1.task_1.web.controllers;

import com.vashchenko.education.t1.task_1.model.dto.request.UserDto;
import com.vashchenko.education.t1.task_1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    Object createUser(@RequestBody UserDto userDto){
        return userService.createUser(userDto);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    Object getAllUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    Object getAllUsers(@PathVariable UUID userId){
        return userService.findUserById(userId);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Object deleteUser(@PathVariable UUID userId){
        userService.deleteUserById(userId);
        return null;
    }
    @PatchMapping("/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Object updateUser(@PathVariable UUID userId,
                    @RequestBody UserDto userDto){
        userService.updateUser(userDto,userId);
        return null;
    }
}
