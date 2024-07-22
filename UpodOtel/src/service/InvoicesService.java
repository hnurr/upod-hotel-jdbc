package service;

import java.sql.Connection;
import java.sql.SQLException;

public interface InvoicesService {

    public void createInvoice(int customerId, int reservationId, int[] serviceIds) throws SQLException;

    public boolean validateCustomerAndReservation(Connection connection, int customerId, int reservationId) throws SQLException;

    public double calculateServiceCost(Connection connection, int[] serviceIds) throws SQLException;

    public double calculateTotalAmount(Connection connection, int reservationId) throws SQLException;
}
