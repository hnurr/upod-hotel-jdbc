package model;

import service.RoomServiceModel;

import java.util.List;

public class Room {

    private int roomNumber;
    private int capacity;
    private String features;
    private Boolean isAvailable;


    public Room(int roomNumber, int capacity, String features, Boolean isAvailable) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.features = features;
        this.isAvailable = isAvailable;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public Boolean getIsAvalible() {
        return isAvailable;
    }

    public void setIsAvalible(Boolean isAvalible) {
        this.isAvailable = isAvailable;
    }


}

