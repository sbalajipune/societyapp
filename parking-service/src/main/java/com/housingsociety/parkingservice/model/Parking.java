package com.housingsociety.parkingservice.model;

import java.util.Objects;

public class Parking {
    private String parkingId;
    private String apartmentId;
    private int level;
    private String vehicles;

    public Parking(){

    }

    public Parking(String parkingId, String apartmentId, int level, String vehicles) {
        this.parkingId = parkingId;
        this.apartmentId = apartmentId;
        this.level = level;
        this.vehicles = vehicles;
    }

    public String getParkingId() {
        return parkingId;
    }

    public void setParkingId(String parkingId) {
        this.parkingId = parkingId;
    }

    public String getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getVehicles() {
        return vehicles;
    }

    public void setVehicles(String vehicles) {
        this.vehicles = vehicles;
    }
}
