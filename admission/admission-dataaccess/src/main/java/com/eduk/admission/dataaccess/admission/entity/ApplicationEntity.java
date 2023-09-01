package com.eduk.admission.dataaccess.admission.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "students")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationEntity {

    @Id
    private UUID id;
    private String firstResponsible;
    private boolean confirmed;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "STUDENT_ID")
    private  StudentEntity student;

    @ManyToOne(cascade = CascadeType.ALL)
    private TermEntity termRequested;

    @OneToOne
    private  ConfirmationEntity confirmation;
}
