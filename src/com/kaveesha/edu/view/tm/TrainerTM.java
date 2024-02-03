package com.kaveesha.edu.view.tm;

import javafx.scene.control.ButtonBar;

public class TrainerTM {
    private int trainerId;
    private String trainerName;
    private String email;
    private String nic;
    private String address;
    private String status;
    private ButtonBar buttonBar;


    public TrainerTM() {
    }

    public TrainerTM(int trainerId, String trainerName, String email, String nic, String address, String status, ButtonBar buttonBar) {
        this.trainerId = trainerId;
        this.trainerName = trainerName;
        this.email = email;
        this.nic = nic;
        this.address = address;
        this.status = status;
        this.buttonBar = buttonBar;
    }

    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ButtonBar getButtonBar() {
        return buttonBar;
    }

    public void setButtonBar(ButtonBar buttonBar) {
        this.buttonBar = buttonBar;
    }

    @Override
    public String toString() {
        return "TrainerTM{" +
                "trainerId=" + trainerId +
                ", trainerName='" + trainerName + '\'' +
                ", email='" + email + '\'' +
                ", nic='" + nic + '\'' +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                ", buttonBar=" + buttonBar +
                '}';
    }
}
