package com.kaveesha.edu.model;

public class ProgramContent {
    private Long propertyId;
    private String header;
    private Long programId;

    public ProgramContent() {
    }

    public ProgramContent(Long propertyId, String header, Long programId) {
        this.propertyId = propertyId;
        this.header = header;
        this.programId = programId;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }

    @Override
    public String toString() {
        return "ProgramContent{" +
                "propertyId=" + propertyId +
                ", header='" + header + '\'' +
                ", programId=" + programId +
                '}';
    }
}
