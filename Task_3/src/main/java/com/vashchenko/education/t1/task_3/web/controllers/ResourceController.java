package com.vashchenko.education.t1.task_3.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(
        name = "ResourceController",
        description = "Controller responsible for access to resources"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/resource")
public class ResourceController {

    @Operation(
            summary = "Return a welcome string",
            description = "A public resource that is accessible to everyone"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful access"
            )
    })
    @GetMapping("/public")
    public ResponseEntity<String> getPublicResource(){
        String greeting = "Welcome to public resource, "+SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(greeting,HttpStatus.OK);
    }

    @Operation(
            summary = "Return a welcome string",
            description = "A authenticated resource that is accessible only to authenticated users"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful access"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized access"
            ),
    })
    @GetMapping("/authenticated")
    public ResponseEntity<String> getAuthenticatedResource(){
        String greeting = "Welcome to authenticated resource, "+SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(greeting,HttpStatus.OK);
    }

    @Operation(
            summary = "Return a welcome string",
            description = "A authenticated resource that is accessible only to Admin users"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful access"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized access, dont have role Admin"
            ),
    })
    @GetMapping("/private")
    @Secured(value = "ADMIN")
    public ResponseEntity<String> getPrivateResource(){
        String greeting = "Welcome to private resource, "+SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(greeting,HttpStatus.OK);
    }
}
