package com.eduk.admission.application.service.dataaccess.admission.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Table(name = "students")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentEntity {

    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
    private List<ApplicationEntity> applications;
}
