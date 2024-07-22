package service;

import java.sql.SQLException;
import java.util.List;

public interface CustomerService {

    public void addCustomer(CustomerServiceModel customer) throws SQLException;

    public CustomerServiceModel getCustomer(int customerId) throws SQLException;

    public List<CustomerServiceModel> getAllCustomer() throws SQLException;

}
