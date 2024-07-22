package service;

import java.time.LocalDate;

public class ReservationServiceModel {

    private int reservationId;
    private int customerId;
    private int roomNumber;
    private LocalDate inDate;
    private LocalDate outDate;




    public ReservationServiceModel(int reservationId, int customerId, int roomNumber, LocalDate inDate, LocalDate outDate) {
        this.reservationId = reservationId;
        this.customerId = customerId;
        this.roomNumber = roomNumber;
        this.inDate = inDate;
        this.outDate = outDate;


    }

    public ReservationServiceModel() {}

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDate getInDate() {
        return inDate;
    }

    public void setInDate(LocalDate inDate) {
        this.inDate = inDate;
    }

    public LocalDate getOutDate() {
        return outDate;
    }

    public void setOutDate(LocalDate outDate) {
        this.outDate = outDate;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public String toString() {
        return "ReservationServiceModel{" +
                "reservationId=" + reservationId +
                ", customerId=" + customerId +
                ", roomNumber=" + roomNumber +
                ", inDate=" + inDate +
                ", outDate=" + outDate +
                '}';

    }

}
