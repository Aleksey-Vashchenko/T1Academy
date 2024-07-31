package com.vashchenko.education.t1.task_1.model.dto.request;

import java.util.UUID;

public record UserDto(UUID id, String email, String name) {
}
