package service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ReservationService {

    public void addReservation(int customerId, LocalDate checkInDate, LocalDate checkOutDate) throws SQLException;

    public List<ReservationServiceModel> getReservationsByCustomerId(int customerId) throws SQLException;

    public void updateReservation(ReservationServiceModel reservation) throws SQLException;

    public void checkInDate(int reservationId, LocalDate inDate) throws SQLException;

    public void checkOutDate(int reservationId, LocalDate outDate) throws SQLException;

    public List<ReservationServiceModel> getAllReservation() throws SQLException;

    public boolean isRoomAvailable(int roomNumber, LocalDate inDate, LocalDate outDate) throws SQLException;

    public ReservationServiceModel getReservation(int reservationId) throws SQLException;

}
