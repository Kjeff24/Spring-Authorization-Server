package com.bexos.authorization_server.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {
    TEACHER_CREATE_COURSE("teacher_create_course"),
    STUDENT_REGISTER_COURSE("student_register_course");

    private final String permission;
}
