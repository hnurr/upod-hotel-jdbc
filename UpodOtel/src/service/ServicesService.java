package service;

import java.sql.SQLException;
import java.util.List;

public interface ServicesService {
    public void addService(ModelServices service) throws SQLException;

    public List<ModelServices> getAllServices() throws SQLException;
}
