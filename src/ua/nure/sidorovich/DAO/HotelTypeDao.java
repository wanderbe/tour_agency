package ua.nure.sidorovich.DAO;

import java.sql.SQLException;
import java.util.List;

import ua.nure.sidorovich.entety.HotelType;

public interface HotelTypeDao extends DAO{
	
	@Override
	HotelType getByID(long id) throws SQLException;
	
	@Override
    List<HotelType> getList() throws SQLException;
	
	HotelType getByHotelClass(int hotelType) throws SQLException;
}
