package com.eduk.domain.entity;

import com.eduk.domain.valueobject.ApplicationId;
import com.eduk.domain.valueobject.ApplicationStatus;

import java.time.LocalDateTime;

public class Application extends BaseEntity<ApplicationId> {
    private ApplicationStatus applicationStatus;
    private String firstResponsible;
    private Term termRequested;
    private boolean confirmed;
    private LocalDateTime dateConfirmed;
    private final Student student;
    private final Confirmation confirmation;

    private Application(Builder builder) {
        super.setId(builder.applicationId);
        applicationStatus = builder.applicationStatus;
        firstResponsible = builder.firstResponsible;
        termRequested = builder.termRequested;
        confirmed = builder.confirmed;
        dateConfirmed = builder.dateConfirmed;
        student = builder.student;
        confirmation = builder.confirmation;
    }

    public static Builder builder() {
        return new Builder();
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public String getFirstResponsible() {
        return firstResponsible;
    }

    public Term getTermRequested() {
        return termRequested;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public LocalDateTime getDateConfirmed() {
        return dateConfirmed;
    }

    public Student getStudent() {
        return student;
    }

    public Confirmation getConfirmation() {
        return confirmation;
    }


    public static final class Builder {
        private ApplicationStatus applicationStatus;
        private String firstResponsible;
        private Term termRequested;
        private boolean confirmed;
        private LocalDateTime dateConfirmed;
        private Student student;
        private Confirmation confirmation;
        private ApplicationId applicationId;

        private Builder() {
        }

        public Builder applicationStatus(ApplicationStatus val) {
            applicationStatus = val;
            return this;
        }

        public Builder firstResponsible(String val) {
            firstResponsible = val;
            return this;
        }

        public Builder termRequested(Term val) {
            termRequested = val;
            return this;
        }

        public Builder confirmed(boolean val) {
            confirmed = val;
            return this;
        }

        public Builder dateConfirmed(LocalDateTime val) {
            dateConfirmed = val;
            return this;
        }

        public Builder student(Student val) {
            student = val;
            return this;
        }

        public Builder confirmation(Confirmation val) {
            confirmation = val;
            return this;
        }

        public Builder applicationId(ApplicationId val) {
            applicationId = val;
            return this;
        }

        public Application build() {
            return new Application(this);
        }
    }
}
