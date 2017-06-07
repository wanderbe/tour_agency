package ua.nure.sidorovich.DAO;

import java.sql.SQLException;
import java.util.List;

import ua.nure.sidorovich.entety.RestType;

public interface RestTypeDao extends DAO{
	
	@Override
	RestType getByID(long id) throws SQLException;
	
	@Override
    List<RestType> getList() throws SQLException;
	
	@Override
    RestType getByName(String name) throws SQLException;
}
