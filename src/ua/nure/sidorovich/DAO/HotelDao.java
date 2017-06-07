package ua.nure.sidorovich.DAO;

import java.sql.SQLException;
import java.util.List;

import ua.nure.sidorovich.entety.Hotel;

public interface HotelDao extends DAO{

	@Override
	Hotel getByID(long id) throws SQLException;
	
	@Override
    List<Hotel> getList() throws SQLException;
	
	@Override
	Hotel getByName(String name) throws SQLException;
}
