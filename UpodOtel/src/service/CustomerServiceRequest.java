package service;


import databaseconnect.DbConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceRequest implements CustomerService {

    @Override
    public void addCustomer(CustomerServiceModel customer) throws SQLException {

        String query = "INSERT INTO customers(first_name,last_name,address,phone_number,email) VALUES (?,?,?,?,?)";

        try (Connection connection = DbConnect.connect();
             PreparedStatement statement = connection.prepareStatement(query)){

            statement.setString(1,customer.getCostumerFirstName());
            statement.setString(2,customer.getCustomerLastName());
            statement.setString(3,customer.getCostumerAddress());
            statement.setString(4,customer.getCostumerPhone());
            statement.setString(5,customer.getCostumerEmail());

            statement.executeUpdate();

        }
    }
@Override
    public CustomerServiceModel getCustomer(int customerId) throws SQLException {

        String query = "SELECT * FROM customers WHERE customer_id=?";

        try (Connection connection = DbConnect.connect();
        PreparedStatement statement = connection.prepareStatement(query)){

            statement.setInt(1,customerId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){

                return new CustomerServiceModel(
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("address"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("email"));

            }


        }
        return  null;

    }
@Override
    public List<CustomerServiceModel> getAllCustomer() throws SQLException {

        String query = "SELECT * FROM customers";
        List<CustomerServiceModel> customers = new ArrayList<>();


        try (Connection connection = DbConnect.connect();
        PreparedStatement statement = connection.prepareStatement(query)){

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){

                customers.add(new CustomerServiceModel(resultSet.getInt("customer_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("address"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("email")));

            }

        }
        return customers;
    }

}
