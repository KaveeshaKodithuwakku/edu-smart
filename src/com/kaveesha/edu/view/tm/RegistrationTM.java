package com.kaveesha.edu.view.tm;

import javafx.scene.control.ButtonBar;

import java.time.LocalDate;

public class RegistrationTM {
    private int registrationId;
    private LocalDate regDate;
    private String programName;
    private long intakeId;
    private String studentName;
    private ButtonBar buttonBar;

    public RegistrationTM() {
    }


    public RegistrationTM(int registrationId, LocalDate regDate, String programName, long intakeId, String studentName, ButtonBar buttonBar) {
        this.registrationId = registrationId;
        this.regDate = regDate;
        this.programName = programName;
        this.intakeId = intakeId;
        this.studentName = studentName;
        this.buttonBar = buttonBar;
    }

    public int getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
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

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }



    public ButtonBar getButtonBar() {
        return buttonBar;
    }

    public void setButtonBar(ButtonBar buttonBar) {
        this.buttonBar = buttonBar;
    }

    @Override
    public String toString() {
        return "RegistrationTM{" +
                "registrationId=" + registrationId +
                ", regDate=" + regDate +
                ", programName='" + programName + '\'' +
                ", intakeId=" + intakeId +
                ", studentName='" + studentName + '\'' +
                ", buttonBar=" + buttonBar +
                '}';
    }
}
