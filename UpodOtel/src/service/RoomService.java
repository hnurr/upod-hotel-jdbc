package service;

import java.sql.SQLException;
import java.util.List;

public interface RoomService {

    public void RoomAdd(RoomServiceModel room) throws SQLException;

    public RoomServiceModel getRoom(int roomNumber) throws SQLException;

    boolean isRoomNumberExists(int roomNumber) throws SQLException;

    public List<RoomServiceModel> getAllRooms() throws SQLException;


}
