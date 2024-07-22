package service;

import databaseconnect.DbConnect;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ReservationServiceRequest implements ReservationService {


    @Override
    public void addReservation(int customerId, LocalDate inDate, LocalDate outDate) throws SQLException {


        // Müşterinin kayıtlı olup olmadığını kontrol et

        String customerQuery = "SELECT COUNT(*) AS count FROM customers WHERE customer_id = ?";

        String roomQuery = "SELECT room_number FROM rooms WHERE is_available = TRUE LIMIT 1";

        String reserveQuery = "INSERT INTO reservations(customer_id, room_number, check_in_date, check_out_date) VALUES (?, ?, ?, ?)";

        String updateRoomAvailability = "UPDATE rooms SET is_available = FALSE WHERE room_number = ?";

        try (Connection connection = DbConnect.connect();
             PreparedStatement customerStatement = connection.prepareStatement(customerQuery)) {

            // Müşteri kontrolü

            customerStatement.setInt(1, customerId);
            try (ResultSet customerResult = customerStatement.executeQuery()) {
                if (customerResult.next() && customerResult.getInt("count") > 0) {

                    // Müşteri kayıtlı, rezervasyon işlemini başlat

                    try (PreparedStatement roomStatement = connection.prepareStatement(roomQuery);
                         ResultSet roomResult = roomStatement.executeQuery()) {

                        if (roomResult.next()) {
                            int roomNumber = roomResult.getInt("room_number");

                            try (PreparedStatement reserveRoomStatement = connection.prepareStatement(reserveQuery);
                                 PreparedStatement updateRoomAvailabilityStatement = connection.prepareStatement(updateRoomAvailability)) {

                                // Rezervasyon ekle
                                reserveRoomStatement.setInt(1, customerId);
                                reserveRoomStatement.setInt(2, roomNumber);
                                reserveRoomStatement.setDate(3, Date.valueOf(inDate));
                                reserveRoomStatement.setDate(4, Date.valueOf(outDate));
                                reserveRoomStatement.executeUpdate();

                                // Oda uygunluk durumunu güncelle
                                long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(inDate, outDate);
                                if (daysBetween >= 1) {
                                    updateRoomAvailabilityStatement.setInt(1, roomNumber);
                                    updateRoomAvailabilityStatement.executeUpdate();
                                }
                            }
                        } else {
                            System.out.println("Uygun oda bulunamadı.");
                        }
                    }
                } else {
                    // Müşteri kayıtlı değil
                    System.out.println("Müşteri kayıtlı değil, lütfen önce kayıt olun.");
                }
            }
        }
    }


    @Override
    public List<ReservationServiceModel> getReservationsByCustomerId(int customerId) throws SQLException {
        List<ReservationServiceModel> reservations = new ArrayList<>();
        String query = "SELECT reservation_id, customer_id, room_number, check_in_date, check_out_date FROM reservations WHERE customer_id = ?";

        try (Connection connection = DbConnect.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, customerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ReservationServiceModel reservation = new ReservationServiceModel();
                    reservation.setReservationId(resultSet.getInt("reservation_id"));
                    reservation.setCustomerId(resultSet.getInt("customer_id"));
                    reservation.setRoomNumber(resultSet.getInt("room_number"));
                    reservation.setInDate(resultSet.getDate("check_in_date").toLocalDate());
                    reservation.setOutDate(resultSet.getDate("check_out_date").toLocalDate());
                    reservations.add(reservation);
                }
            }
        }

        return reservations;
    }


    public ReservationServiceModel getReservation(int reservationId) throws SQLException {
        String query = "SELECT reservation_id, customer_id, room_number, check_in_date, check_out_date FROM reservations WHERE reservation_id = ?";

        try (Connection connection = DbConnect.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, reservationId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    ReservationServiceModel reservation = new ReservationServiceModel();
                    reservation.setReservationId(resultSet.getInt("reservation_id"));
                    reservation.setCustomerId(resultSet.getInt("customer_id"));
                    reservation.setRoomNumber(resultSet.getInt("room_number"));
                    reservation.setInDate(resultSet.getDate("check_in_date").toLocalDate());
                    reservation.setOutDate(resultSet.getDate("check_out_date").toLocalDate());
                    return reservation;
                } else {
                    return null;
                }
            }
        }

    }

    @Override
    public void updateReservation(ReservationServiceModel reservation) throws SQLException {

        String query = "UPDATE reservations SET room_number=?, check_in_date=?, check_out_date=? WHERE reservation_id=?";

        try (Connection connection = DbConnect.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Belirtilen tarihler arasında odanın müsait olup olmadığını kontrol et
            if (!isRoomAvailable(reservation.getRoomNumber(), reservation.getInDate(), reservation.getOutDate())) {
                System.out.println("Bu tarihler arasında seçilen oda müsait değil.");
                return;
            }

            statement.setInt(1, reservation.getRoomNumber());
            statement.setDate(2, Date.valueOf(reservation.getInDate()));
            statement.setDate(3, Date.valueOf(reservation.getOutDate()));
            statement.setInt(4, reservation.getReservationId());

            // Tarih farkını gün cinsinden hesapla
            long daysBetween = ChronoUnit.DAYS.between(reservation.getInDate(), reservation.getOutDate());

            // Gün farkı 1 veya daha fazlaysa işlemi gerçekleştir
            if (Math.abs(daysBetween) >= 1) {
                statement.executeUpdate();
                System.out.println("Rezervasyon başarıyla güncellendi.");
            } else {
                System.out.println("Rezervasyon güncellemesi yapılamadı: Gün farkı 1 veya daha fazla olmalıdır.");
            }
        }
    }


    @Override
    public void checkInDate(int reservationId, LocalDate checkInDate) throws SQLException {
        String query = "UPDATE reservations SET check_in_date=? WHERE reservation_id=?";

        try (Connection connection = DbConnect.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setTimestamp(1, Timestamp.valueOf(checkInDate.atStartOfDay()));
            statement.setInt(2, reservationId);

            statement.executeUpdate();
        }
    }


    @Override
    public void checkOutDate(int reservationId, LocalDate checkOutDate) throws SQLException {
        String query = "UPDATE reservations SET check_out_date = ? WHERE reservation_id = ?";

        try (Connection connection = DbConnect.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setDate(1, Date.valueOf(checkOutDate));
            statement.setInt(2, reservationId);

            statement.executeUpdate();
        }
    }


    @Override
    public List<ReservationServiceModel> getAllReservation() throws SQLException {

        String query = "SELECT * FROM reservations";

        List<ReservationServiceModel> reservations = new ArrayList<>();

        try (Connection connection = DbConnect.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                reservations.add(new ReservationServiceModel(resultSet.getInt("reservation_id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getInt("room_number"),
                        resultSet.getDate("check_in_date").toLocalDate(),
                        resultSet.getDate("check_out_date").toLocalDate()));

            }

        }
        return reservations;
    }

    public boolean isRoomAvailable(int roomNumber, LocalDate inDate, LocalDate outDate) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM reservations WHERE room_number = ? AND (check_in_date < ? AND check_out_date > ?)";

        try (Connection connection = DbConnect.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, roomNumber);
            statement.setDate(2, Date.valueOf(outDate));
            statement.setDate(3, Date.valueOf(inDate));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next() && resultSet.getInt("count") == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}

