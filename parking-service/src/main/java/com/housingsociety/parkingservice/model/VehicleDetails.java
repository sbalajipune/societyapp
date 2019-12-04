package com.housingsociety.parkingservice.model;

public class VehicleDetails {
    private String registrationId;
    private Member member;
    private String parkingId;
    private String model;
    private String wheelsType;

    public VehicleDetails(){

    }
    public VehicleDetails(String registrationId, Member member, String parkingId, String model, String wheelsType) {
        this.registrationId = registrationId;
        this.member = member;
        this.parkingId = parkingId;
        this.model = model;
        this.wheelsType = wheelsType;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getParkingId() {
        return parkingId;
    }

    public void setParkingId(String parkingId) {
        this.parkingId = parkingId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getWheelsType() {
        return wheelsType;
    }

    public void setWheelsType(String wheelsType) {
        this.wheelsType = wheelsType;
    }
}
