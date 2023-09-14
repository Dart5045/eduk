package com.eduk.admission.service.domain;

import com.eduk.admission.service.domain.entity.Application;
import com.eduk.domain.entity.AggregateRoot;
import com.eduk.admission.service.domain.valueobject.TermId;

import java.util.List;

public class Term extends AggregateRoot<TermId> {
    private String period;
    private int year;
    private final List<Application> applications;
    private boolean active;

    private Term(Builder builder) {
        super.setId(builder.termId);
        period = builder.period;
        year = builder.year;
        applications = builder.applications;
        active = builder.active;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getPeriod() {
        return period;
    }

    public int getYear() {
        return year;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public boolean isActive() {
        return active;
    }


    public static final class Builder {
        private String period;
        private int year;
        private List<Application> applications;
        private boolean active;
        private TermId termId;

        private Builder() {
        }

        public Builder period(String val) {
            period = val;
            return this;
        }

        public Builder year(int val) {
            year = val;
            return this;
        }

        public Builder applications(List<Application> val) {
            applications = val;
            return this;
        }

        public Builder active(boolean val) {
            active = val;
            return this;
        }

        public Builder termId(TermId val) {
            termId =  val;
            return this;
        }

        public Term build() {
            return new Term(this);
        }
    }
}
