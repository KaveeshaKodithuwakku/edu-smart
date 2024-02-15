package com.kaveesha.edu.dto;

import java.time.LocalDate;

public class IntakeDto {

    private long intakeId;
    private String intakeName;
    private LocalDate startDate;
    private long programId;
    private String programName;

    public IntakeDto() {
    }

    public IntakeDto(long intakeId, String intakeName, LocalDate startDate, long programId, String programName) {
        this.intakeId = intakeId;
        this.intakeName = intakeName;
        this.startDate = startDate;
        this.programId = programId;
        this.programName = programName;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
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
                ", programName='" + programName + '\'' +
                '}';
    }
}
