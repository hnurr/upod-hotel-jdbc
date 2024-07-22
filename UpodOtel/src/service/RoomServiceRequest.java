package service;

import databaseconnect.DbConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


//ResultSet Sorgu sonucund averi döndürür bu sebepler sorguun sonucunda bi veri getiriceksek ResultSet kullanırız.....
public class RoomServiceRequest implements RoomService {

    @Override
    public void RoomAdd(RoomServiceModel room) throws SQLException {
        String query = "INSERT INTO rooms(room_number, capacity, features, is_available) VALUES (?, ?, ?, ?)";


        try (Connection connection = DbConnect.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {


            statement.setInt(1, room.getRoomNumber());
            statement.setInt(2, room.getCapacity());
            statement.setString(3, room.getFeatures());
            statement.setBoolean(4, room.getIsAvalible());

            if (room.getCapacity() > 1) {
                statement.executeUpdate();
            } else {
                System.out.println("Oda kapasitesi 1'den az olamaz !!");
            }

        }
    }


    @Override
    public RoomServiceModel getRoom(int roomNumber) throws SQLException {

        String query = "SELECT * FROM rooms WHERE room_number='?'";

        try (Connection connection = DbConnect.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, roomNumber);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new RoomServiceModel(resultSet.getInt("room_number"),
                        resultSet.getInt("capacity"),
                        resultSet.getString("features"),
                        resultSet.getBoolean("is_available"));


            }
        }


        return null;
    }

    @Override
    public List<RoomServiceModel> getAllRooms() throws SQLException {

        String query = "SELECT * FROM rooms";
        List<RoomServiceModel> rooms = new ArrayList<>();

        try (Connection connection = DbConnect.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                rooms.add(new RoomServiceModel(resultSet.getInt("room_number"),
                        resultSet.getInt("capacity"),
                        resultSet.getString("features"),
                        resultSet.getBoolean("is_available")));
            }

        }
        return rooms;
    }


        public boolean isRoomNumberExists(int roomNumber) throws SQLException {
            String query = "SELECT COUNT(*) FROM rooms WHERE room_number = ?";
            try (Connection connection = DbConnect.connect();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setInt(1, roomNumber);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
            return false; // Eğer bir hata oluşursa veya oda numarası mevcut değilse false döner
        }
    }

    //sonuç almak istediğimiz durumlarda ResultSet kullanıyoruz.......




