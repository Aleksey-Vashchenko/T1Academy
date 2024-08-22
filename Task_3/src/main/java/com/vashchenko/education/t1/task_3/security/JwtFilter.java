package com.vashchenko.education.t1.task_3.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vashchenko.education.t1.task_3.service.JwtService;
import com.vashchenko.education.t1.task_3.web.dto.response.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final static String BEARER_PREFIX ="Bearer ";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenFromHeader = request.getHeader(AUTHORIZATION);
        if(tokenFromHeader!=null){
            try {
                JwtAuthentication jwtAuthentication =jwtService.authenticate(tokenFromHeader.substring(BEARER_PREFIX.length()));
                WebAuthenticationDetailsSource detailsSource = new WebAuthenticationDetailsSource();
                WebAuthenticationDetails details = detailsSource.buildDetails(request);
                jwtAuthentication.setAuthenticationDetails(details);
                SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);
            }
            catch (Exception e){
                ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonResponse = objectMapper.writeValueAsString(errorResponse);
                response.getWriter().write(jsonResponse);
                response.getWriter().flush();
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
