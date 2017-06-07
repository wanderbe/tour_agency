package ua.nure.sidorovich.DAO;

import java.sql.SQLException;
import java.util.List;

import ua.nure.sidorovich.entety.BlockedUser;
import ua.nure.sidorovich.entety.User;

public interface BlockedUserDao extends DAO{
		
	
	BlockedUser getByUserID(long userId) throws SQLException;
	
	BlockedUser getByUser(User user) throws SQLException;
	
	@Override
    List<BlockedUser> getList() throws SQLException;
	
}
