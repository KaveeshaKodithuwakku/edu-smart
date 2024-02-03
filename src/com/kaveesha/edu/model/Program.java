package com.kaveesha.edu.model;

import java.util.List;

public class Program {
    private int programId;
    private String programName;
    private int hours;
    private double amount;
    private String userEmail;
    private long trainerId;
    private List<String> contents;

    public Program() {
    }

    public Program(int programId, String programName, int hours, double amount, String userEmail, long trainerId, List<String> contents) {
        this.programId = programId;
        this.programName = programName;
        this.hours = hours;
        this.amount = amount;
        this.userEmail = userEmail;
        this.trainerId = trainerId;
        this.contents = contents;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(long trainerId) {
        this.trainerId = trainerId;
    }

    public List<String> getContents() {
        return contents;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "Program{" +
                "programId=" + programId +
                ", programName='" + programName + '\'' +
                ", hours=" + hours +
                ", amount=" + amount +
                ", userEmail='" + userEmail + '\'' +
                ", trainerId=" + trainerId +
                ", contents=" + contents +
                '}';
    }
}
