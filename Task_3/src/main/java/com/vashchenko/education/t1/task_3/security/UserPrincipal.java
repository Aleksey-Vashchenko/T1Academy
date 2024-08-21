package com.vashchenko.education.t1.task_3.security;

import com.vashchenko.education.t1.task_3.model.entity.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserPrincipal implements UserDetails {
    private UUID id;
    private String username;
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return username;
    }

}
