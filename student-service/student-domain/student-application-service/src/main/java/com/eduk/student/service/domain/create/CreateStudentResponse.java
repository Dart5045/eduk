package com.eduk.student.service.domain.create;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreateStudentResponse {
    @NotNull
    private final UUID studentId;
    @NotNull
    private final String message;
}
