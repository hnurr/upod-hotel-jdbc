package service;

import databaseconnect.DbConnect;

import java.sql.*;

public class InvoicesServiceRequest implements InvoicesService {


    public void createInvoice(int customerId, int reservationId, int[] serviceIds) throws SQLException {

        String insertInvoicequery = "INSERT INTO invoices(customer_id, reservation_id, total_amount, date) VALUES (?, ?, ?, ?)";

        try (Connection connection = DbConnect.connect();
             PreparedStatement statement = connection.prepareStatement(insertInvoicequery, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Müşteri ve rezervasyon bilgilerini doğrulama
            if (!validateCustomerAndReservation(connection, customerId, reservationId)) {
                System.out.println("Geçersiz müşteri veya rezervasyon.");
                return;
            }

            // Toplam hizmet maliyetini hesaplama
            double totalServiceCost = calculateServiceCost(connection, serviceIds);

            // Fatura oluşturma
            double totalAmount = calculateTotalAmount(connection, reservationId) + totalServiceCost;

            statement.setInt(1, customerId);
            statement.setInt(2, reservationId);
            statement.setDouble(3, totalAmount);
            statement.setDate(4, new Date(System.currentTimeMillis()));
            statement.executeUpdate();

            // Fatura ID'yi alma
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int invoiceId = generatedKeys.getInt(1);

                // Hizmetleri faturaya ekleme
                for (int serviceId : serviceIds) {
                    String insertServiceQuery = "INSERT INTO invoice_services (invoice_id, service_id) VALUES (?, ?)";
                    try (PreparedStatement servicePstmt = connection.prepareStatement(insertServiceQuery)) {
                        servicePstmt.setInt(1, invoiceId);
                        servicePstmt.setInt(2, serviceId);
                        servicePstmt.executeUpdate();
                    }
                }
            }
        }
        System.out.println("Fatura başarıyla oluşturuldu.");
    }

    public boolean validateCustomerAndReservation(Connection connection, int customerId, int reservationId) throws SQLException {
        String validateCustomerQuery = "SELECT COUNT(*) FROM customers WHERE customer_id = ?";
        String validateReservationQuery = "SELECT COUNT(*) FROM reservations WHERE reservation_id = ?";

        try (PreparedStatement customerStmt = connection.prepareStatement(validateCustomerQuery);
             PreparedStatement reservationStmt = connection.prepareStatement(validateReservationQuery)) {

            customerStmt.setInt(1, customerId);
            reservationStmt.setInt(1, reservationId);

            try (ResultSet customerRs = customerStmt.executeQuery();
                 ResultSet reservationRs = reservationStmt.executeQuery()) {

                if (customerRs.next() && reservationRs.next()) {
                    return customerRs.getInt(1) > 0 && reservationRs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public double calculateServiceCost(Connection connection, int[] serviceIds) throws SQLException {
        double totalCost = 0;
        for (int serviceId : serviceIds) {
            String query = "SELECT cost FROM services WHERE service_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, serviceId);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    totalCost += rs.getDouble("cost");
                }
            }
        }
        return totalCost;
    }

    public double calculateTotalAmount(Connection connection, int reservationId) throws SQLException {
        String query = "SELECT DATEDIFF(check_out_date, check_in_date) * (50) AS total_amount FROM reservations WHERE reservation_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, reservationId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total_amount");
            }
        }
        return 0;
    }

}

