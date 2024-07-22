package service;

import databaseconnect.DbConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicesServiceRequest implements ServicesService {


    public void addService(ModelServices service) throws SQLException {
        String query = "INSERT INTO services (name, cost) VALUES (?, ?)";

        try (Connection connection = DbConnect.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, service.getServiceName());
            statement.setDouble(2, service.getServiceCost());

            statement.executeUpdate();
        }
    }

    public List<ModelServices> getAllServices() throws SQLException {
        List<ModelServices> services = new ArrayList<>();
        String query = "SELECT * FROM services";

        try (Connection connection = DbConnect.connect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                services.add(new ModelServices(resultSet.getInt("service_id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("cost")));

            }
        }

        return services;
    }
}
