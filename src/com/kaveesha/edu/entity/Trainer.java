package com.kaveesha.edu.entity;

public class Trainer {
    private int trainerId;
    private String trainerName;
    private String trainerEmail;
    private String nic;
    private String address;
    private boolean status;

    public Trainer() {
    }

    public Trainer(int trainerId, String trainerName, String trainerEmail, String nic, String address, boolean status) {
        this.trainerId = trainerId;
        this.trainerName = trainerName;
        this.trainerEmail = trainerEmail;
        this.nic = nic;
        this.address = address;
        this.status = status;
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

    public String getTrainerEmail() {
        return trainerEmail;
    }

    public void setTrainerEmail(String trainerEmail) {
        this.trainerEmail = trainerEmail;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "trainerId=" + trainerId +
                ", trainerName='" + trainerName + '\'' +
                ", trainerEmail='" + trainerEmail + '\'' +
                ", nic='" + nic + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                '}';
    }
}
