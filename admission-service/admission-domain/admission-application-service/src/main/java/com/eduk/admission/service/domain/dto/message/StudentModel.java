package com.eduk.admission.service.domain.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class StudentModel {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
}
