package com.eduk.admission.service.domain;

import com.eduk.domain.entity.AggregateRoot;
import com.eduk.domain.valueobject.StudentId;

public class Student extends AggregateRoot<StudentId> {
    private String firstName;
    private String lastName;
    private String email;

    public Student(StudentId studentId, String firstName, String lastName, String email) {
        super.setId(studentId);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Student(StudentId studentId) {
        super.setId(studentId);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
