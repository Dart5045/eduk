package com.eduk.student.service.domain.create;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudentCommand {
    @NotNull
    private  UUID studentId;
    @NotNull
    private  String firstName;
    @NotNull
    private  String lastName;
    @NotNull
    private  String email;
}
