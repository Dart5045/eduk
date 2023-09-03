package com.eduk.application.domain.entity;

import com.eduk.application.domain.valueobject.ApplicationStatus;
import com.eduk.domain.entity.AggregateRoot;
import com.eduk.domain.valueobject.StudentId;

import java.util.List;

public class Student extends AggregateRoot<StudentId> {
    private String firstName;
    private String lastName;
    private String email;
    private final List<Application> applications;

    private final ApplicationStatus applicationStatus;
    private boolean paid;

    private Student(Builder builder) {
        this.setId(builder.studentId);
        firstName = builder.firstName;
        lastName = builder.lastName;
        email = builder.email;
        applications = builder.applications;
        applicationStatus = builder.applicationStatus;
        paid = builder.paid;
    }

    public static Builder builder() {
        return new Builder();
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

    public List<Application> getApplications() {
        return applications;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public boolean isPaid() {
        return paid;
    }

    public static final class Builder {
        private String firstName;
        private String lastName;
        private String email;
        private List<Application> applications;
        private ApplicationStatus applicationStatus;
        private boolean paid;
        private StudentId studentId;

        private Builder() {
        }

        public Builder firstName(String val) {
            firstName = val;
            return this;
        }

        public Builder lastName(String val) {
            lastName = val;
            return this;
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Builder applications(List<Application> val) {
            applications = val;
            return this;
        }

        public Builder applicationStatus(ApplicationStatus val) {
            applicationStatus = val;
            return this;
        }

        public Builder paid(boolean val) {
            paid = val;
            return this;
        }

        public Builder studentId(StudentId val) {
            studentId = val;
            return this;
        }

        public Student build() {
            return new Student(this);
        }
    }
}
