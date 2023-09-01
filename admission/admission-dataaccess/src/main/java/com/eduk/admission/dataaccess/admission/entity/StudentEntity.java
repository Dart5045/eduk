package com.eduk.admission.dataaccess.admission.entity;

import com.eduk.application.domain.entity.Application;
import com.eduk.application.domain.valueobject.ApplicationStatus;
import com.eduk.application.domain.valueobject.ConfirmationStatus;
import com.eduk.domain.valueobject.ApplicationId;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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
