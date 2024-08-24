package com.vashchenko.education.t1.task_3.service;

import com.vashchenko.education.t1.task_3.exception.UserIsNotFoundException;
import com.vashchenko.education.t1.task_3.exception.WrongLoginOrPasswordException;
import com.vashchenko.education.t1.task_3.model.entity.Role;
import com.vashchenko.education.t1.task_3.model.entity.User;
import com.vashchenko.education.t1.task_3.security.UserPrincipal;
import com.vashchenko.education.t1.task_3.web.dto.request.RegistrationRequestDto;
import org.instancio.Instancio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Import({UserService.class, BCryptPasswordEncoder.class})
class UserServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @AfterEach
    public void clearDatabase() {
        jdbcTemplate.update("DELETE FROM refresh_token");
        jdbcTemplate.update("DELETE FROM user_roles");
        jdbcTemplate.update("DELETE FROM users_tbl");
    }

    @Test
    void findByEmail() {
        RegistrationRequestDto dto = Instancio.of(RegistrationRequestDto.class).create();
        assertThrows(WrongLoginOrPasswordException.class, () -> {
            userService.findByEmail(dto.email());
        });
        userService.registration(dto);
        assertNotNull(userService.findByEmail(dto.email()));
    }

    @Test
    void findById() {
        RegistrationRequestDto dto = Instancio.of(RegistrationRequestDto.class).create();
        assertThrows(UserIsNotFoundException.class, () -> {
            userService.findById(UUID.randomUUID());
        });
        userService.registration(dto);
        User user = userService.findByEmail(dto.email());
        assertEquals(user.getId(),userService.findByEmail(dto.email()).getId());
    }

    @Test
    void registration() {
        RegistrationRequestDto dto = Instancio.of(RegistrationRequestDto.class).create();
        userService.registration(dto);
        User user = userService.findByEmail(dto.email());
        assertEquals(user.getUsername(),dto.username());
        assertEquals(user.getEmail(),dto.email());
        assertTrue(passwordEncoder.matches(dto.password(),user.getPasswordHash()));
        assertEquals(user.getRoles(), Set.of(Role.USER));
        assertNotNull(user.getId());
    }

    @Test
    void updateLevelToAdmin() {
        RegistrationRequestDto dto = new RegistrationRequestDto("123","Lesha","123");
        userService.registration(dto);
        User user = userService.findByEmail("123");
        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setId(user.getId());
        userService.updateLevelToAdmin(userPrincipal);
        User updatedUser = userService.findByEmail("123");
        assertEquals(updatedUser.getRoles(),Set.of(Role.ADMIN,Role.USER));
    }
}