package com.kaveesha.edu.view.tm;

import javafx.scene.control.ButtonBar;

import java.time.LocalDate;

public class IntakeTM {

    private long intakeId;
    private String intakeName;
    private String programName;
    private LocalDate startDate;
    private ButtonBar buttonBar;

    public IntakeTM() {
    }

    public IntakeTM(long intakeId, String intakeName, String programName, LocalDate startDate, ButtonBar buttonBar) {
        this.intakeId = intakeId;
        this.intakeName = intakeName;
        this.programName = programName;
        this.startDate = startDate;
        this.buttonBar = buttonBar;
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

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public ButtonBar getButtonBar() {
        return buttonBar;
    }

    public void setButtonBar(ButtonBar buttonBar) {
        this.buttonBar = buttonBar;
    }

    @Override
    public String toString() {
        return "IntakeTM{" +
                "intakeId=" + intakeId +
                ", intakeName='" + intakeName + '\'' +
                ", programName='" + programName + '\'' +
                ", startDate=" + startDate +
                ", buttonBar=" + buttonBar +
                '}';
    }
}
