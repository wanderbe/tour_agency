package ua.nure.sidorovich.DAO;

import java.sql.SQLException;
import java.util.List;

import ua.nure.sidorovich.entety.UserRole;

public interface UserRoleDao extends DAO{
	
	@Override
	UserRole getByID(long id) throws SQLException;
	
	@Override
    List<UserRole> getList() throws SQLException;
	
	@Override
	UserRole getByName(String name) throws SQLException;
}
