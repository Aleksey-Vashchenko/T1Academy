package com.vashchenko.education.t1.task_3.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.security.auth.Subject;
import java.util.Collection;

public class JwtAuthentication implements Authentication {
    private final UserPrincipal userPrincipal;
    private final String accessToken;

    private WebAuthenticationDetails authenticationDetails;
    boolean isAuthenticated = true;

    public JwtAuthentication(UserPrincipal userPrincipal, String accessToken) {
        this.userPrincipal = userPrincipal;
        this.accessToken = accessToken;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userPrincipal.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return accessToken;
    }

    @Override
    public Object getDetails() {
        return authenticationDetails;
    }

    @Override
    public Object getPrincipal() {
        return userPrincipal;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated=isAuthenticated;
    }

    @Override
    public String getName() {
        return userPrincipal.getUsername();
    }

    @Override
    public boolean implies(Subject subject) {
        return Authentication.super.implies(subject);
    }

    public void setAuthenticationDetails(WebAuthenticationDetails authenticationDetails) {
        this.authenticationDetails = authenticationDetails;
    }
}
