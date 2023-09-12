package com.eduk.student.service.domain.entity;


import com.eduk.domain.entity.AggregateRoot;
import com.eduk.domain.valueobject.StudentId;

public class Student extends AggregateRoot<StudentId> {
    private String firstName;
    private String lastName;
    private String email;


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Student(StudentId studentId, String firstName, String lastName, String email) {
        super.setId(studentId);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
