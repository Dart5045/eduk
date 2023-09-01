package com.eduk.admission.dataaccess.admission.entity;

import com.eduk.application.domain.entity.Application;
import com.eduk.application.domain.valueobject.ConfirmationStatus;
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
public class TermEntity {

    @Id
    private UUID id;
    private String period;
    private int year;
    private boolean active;

    @OneToMany(mappedBy = "application",cascade = CascadeType.ALL)
    private List<ApplicationEntity> applications;
}
