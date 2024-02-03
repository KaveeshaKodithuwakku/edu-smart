package com.kaveesha.edu.view.tm;

import javafx.scene.control.ButtonBar;

public class ProgramTM {
    private int ProgramId;
    private int trainerId;
    private String programName;
    private int hours;
    private double amount;
    private ButtonBar buttonBar;

    public ProgramTM() {
    }

    public ProgramTM(int programId, String programName, int hours, double amount, ButtonBar buttonBar) {
        ProgramId = programId;
        this.programName = programName;
        this.hours = hours;
        this.amount = amount;
        this.buttonBar = buttonBar;
    }

    public int getProgramId() {
        return ProgramId;
    }

    public void setProgramId(int programId) {
        ProgramId = programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public ButtonBar getButtonBar() {
        return buttonBar;
    }

    public void setButtonBar(ButtonBar buttonBar) {
        this.buttonBar = buttonBar;
    }

    @Override
    public String toString() {
        return "ProgramTM{" +
                "ProgramId=" + ProgramId +
                ", programName='" + programName + '\'' +
                ", hours=" + hours +
                ", amount=" + amount +
                ", buttonBar=" + buttonBar +
                '}';
    }

    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }
}
