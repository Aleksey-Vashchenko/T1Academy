package com.vashchenko.education.t1.task_3.web.controllers;

import com.vashchenko.education.t1.task_3.model.entity.Role;
import com.vashchenko.education.t1.task_3.security.JwtAuthentication;
import com.vashchenko.education.t1.task_3.security.PasswordValidator;
import com.vashchenko.education.t1.task_3.security.SecurityConfig;
import com.vashchenko.education.t1.task_3.security.UserPrincipal;
import com.vashchenko.education.t1.task_3.service.AccessTokenService;
import com.vashchenko.education.t1.task_3.service.JwtService;
import com.vashchenko.education.t1.task_3.service.RefreshTokenService;
import com.vashchenko.education.t1.task_3.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = {SecurityConfig.class, ResourceController.class, JwtService.class, PasswordValidator.class})
@AutoConfigureMockMvc
class ResourceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    AccessTokenService accessTokenService;

    @MockBean
    RefreshTokenService refreshTokenService;


    @Test
    void getPublicResourceWithoutAuthentication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/resource/public"))
                .andExpect(status().isOk())
                .andReturn();
    }
    @Test
    void getPublicResourceWithAuthentication() throws Exception {
        UserPrincipal userPrincipal = new UserPrincipal(UUID.randomUUID(),"123", Set.of(Role.USER));
        when(accessTokenService.toAuthentication(anyString())).thenReturn(new JwtAuthentication(userPrincipal,"accessToken"));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/resource/public")
                        .header("Authorization","Bearer "+"accessToken"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void getAuthenticatedResourceWithoutAuthentication() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/resource/authenticated"))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    void getAuthenticatedResourceWithAuthentication() throws Exception{
        UserPrincipal userPrincipal = new UserPrincipal(UUID.randomUUID(),"123", Set.of(Role.USER));
        when(accessTokenService.toAuthentication(anyString())).thenReturn(new JwtAuthentication(userPrincipal,"accessToken"));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/resource/authenticated")
                        .header("Authorization","Bearer "+"accessToken"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void getPrivateResourceWithoutAuthentication() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/resource/private"))
                .andExpect(status().isForbidden())
                .andReturn();
    }
    @Test
    void getPrivateResourceWithUserAuthentication() throws Exception{
        UserPrincipal userPrincipal = new UserPrincipal(UUID.randomUUID(),"123", Set.of(Role.USER));
        when(accessTokenService.toAuthentication(anyString())).thenReturn(new JwtAuthentication(userPrincipal,"accessToken"));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/resource/private")
                        .header("Authorization","Bearer "+"accessToken"))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    void getPrivateResourceWithAdminAuthentication() throws Exception{
        UserPrincipal userPrincipal = new UserPrincipal(UUID.randomUUID(),"123", Set.of(Role.ADMIN));
        when(accessTokenService.toAuthentication(anyString())).thenReturn(new JwtAuthentication(userPrincipal,"accessToken"));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/resource/private")
                        .header("Authorization","Bearer "+"accessToken"))
                .andExpect(status().isOk())
                .andReturn();
    }
}