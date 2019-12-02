package com.housingsociety.parkingservice.model;

public class ParkingDetails {
    private String parkingId;
    private Member owner;
    private int level;
    private VehicleDetails vehicleDetails;

    public ParkingDetails(){

    }

    public ParkingDetails(String parkingId, Member owner, int level, VehicleDetails vehicleDetails) {
        this.parkingId = parkingId;
        this.owner = owner;
        this.level = level;
        this.vehicleDetails = vehicleDetails;
    }

    public String getParkingId() {
        return parkingId;
    }

    public void setParkingId(String parkingId) {
        this.parkingId = parkingId;
    }

    public Member getOwner() {
        return owner;
    }

    public void setOwner(Member owner) {
        this.owner = owner;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public VehicleDetails getVehicleDetails() {
        return vehicleDetails;
    }

    public void setVehicleDetails(VehicleDetails vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }
}
