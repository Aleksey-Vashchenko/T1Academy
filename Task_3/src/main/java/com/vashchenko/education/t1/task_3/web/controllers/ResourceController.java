package com.vashchenko.education.t1.task_3.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/resource")
public class ResourceController {

    @GetMapping("/public")
    public ResponseEntity<String> getPublicResource(){
        String greeting = "Welcome to public resource, "+SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(greeting,HttpStatus.OK);
    }

    @GetMapping("/authenticated")
    public ResponseEntity<String> getAuthenticatedResource(){
        String greeting = "Welcome to authenticated resource, "+SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(greeting,HttpStatus.OK);
    }

    @GetMapping("/private")
    @Secured(value = "ADMIN")
    public ResponseEntity<String> getPrivateResource(){
        String greeting = "Welcome to private resource, "+SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(greeting,HttpStatus.OK);
    }
}
