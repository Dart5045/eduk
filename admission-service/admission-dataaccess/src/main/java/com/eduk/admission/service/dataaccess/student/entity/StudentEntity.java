package com.eduk.admission.service.dataaccess.student.entity;

import com.eduk.admission.service.dataaccess.admission.entity.ApplicationEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students")
public class StudentEntity {
    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
}
