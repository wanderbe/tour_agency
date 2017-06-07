package ua.nure.sidorovich.DAO;

import java.sql.SQLException;
import java.util.List;

import ua.nure.sidorovich.entety.User;

public interface UserDao extends DAO{
	
	@Override
	User getByID(long id) throws SQLException;
	
	@Override
    List<User> getList() throws SQLException;
	
	@Override
	User getByName(String name) throws SQLException;
	
	User getByLogin(String login) throws SQLException;

	User getByEmail(String parameter) throws SQLException;
}
