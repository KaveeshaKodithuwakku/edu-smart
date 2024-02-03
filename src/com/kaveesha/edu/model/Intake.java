package com.kaveesha.edu.model;

import java.time.LocalDate;

public class Intake {

    private long intakeId;
    private String intakeName;
    private LocalDate startDate;
    private long programId;

    public Intake() {
    }

    public Intake(long intakeId, String intakeName, LocalDate startDate, long programId) {
        this.intakeId = intakeId;
        this.intakeName = intakeName;
        this.startDate = startDate;
        this.programId = programId;
    }

    public long getIntakeId() {
        return intakeId;
    }

    public void setIntakeId(long intakeId) {
        this.intakeId = intakeId;
    }

    public String getIntakeName() {
        return intakeName;
    }

    public void setIntakeName(String intakeName) {
        this.intakeName = intakeName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public long getProgramId() {
        return programId;
    }

    public void setProgramId(long programId) {
        this.programId = programId;
    }

    @Override
    public String toString() {
        return "Intake{" +
                "intakeId=" + intakeId +
                ", intakeName='" + intakeName + '\'' +
                ", startDate=" + startDate +
                ", programId=" + programId +
                '}';
    }
}
