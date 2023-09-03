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
public class TermEntity {

    @Id
    private UUID id;
    private String period;
    private int year;
    private boolean active;

    @OneToMany(mappedBy = "application",cascade = CascadeType.ALL)
    private List<ApplicationEntity> applications;
}
