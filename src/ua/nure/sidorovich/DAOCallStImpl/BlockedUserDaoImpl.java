package ua.nure.sidorovich.DAOCallStImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.nure.sidorovich.DAO.BlockedUserDao;
import ua.nure.sidorovich.entety.BlockedUser;
import ua.nure.sidorovich.entety.User;
import ua.nure.sidorovich.entety.UserRole;
import ua.nure.sidorovich.util.ConnectionManagerImpl;

public class BlockedUserDaoImpl extends DAOSceleton implements BlockedUserDao{
	
	public BlockedUserDaoImpl(){
		super();
	}

	private static final String GET_ALL_BLOCKED_USERS = "{call GET_ALL_BLOCKED_USERS}";

	private static final String GET_BLOCKED_USER_BY_ID = "{call GET_BLOCKED_USER_BY_ID(?)}";
			
	private static final String INSERT_BLOCKED_USER = "{call INSERT_BLOCKED_USER(?, ?, ?)}";

	private static final String DELETE_BLOCKED_USER = "{call DELETE_BLOCKED_USER(?)}";
	
	private BlockedUser createBlockedUserFromResultSet(ResultSet rs) throws SQLException {
		BlockedUser blockedUser = new BlockedUser();
		User user = new User();
		UserRole userRole = new UserRole();
				
		int k = 0;
						
		blockedUser.setBlockTime(rs.getTimestamp(++k));
		blockedUser.setDescription(rs.getString(++k));
		
		user.setId(rs.getLong(++k));
		user.setName(rs.getString(++k));
		user.setBirthday(rs.getDate(++k));
		user.setEmail(rs.getString(++k));
		user.setPhone(rs.getString(++k));
		user.setLogin(rs.getString(++k));
		user.setPassword(rs.getString(++k));
		
		userRole.setId(rs.getLong(++k));
		userRole.setName(rs.getString(++k));
		userRole.setDescription(rs.getString(++k));
		
		user.setRole(userRole);
		blockedUser.setUser(user);
		
		return blockedUser;
	}
	

	@Override
	public BlockedUser getByUserID(long userId) throws SQLException{
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
		
		BlockedUser blockedUser = null;
		
		try {
			callSt = con.prepareCall(GET_BLOCKED_USER_BY_ID);
			
			int i = 0;
			callSt.setLong(++i, userId);
			
			ResultSet rs = callSt.executeQuery();
			
			while(rs.next()){
				blockedUser = createBlockedUserFromResultSet(rs);
			}
		} finally {
			closeStatement(callSt);
			con.close();
			}
		return blockedUser;
	}
	
	@Override
	public List<BlockedUser> getList() throws SQLException {
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
				
		List<BlockedUser> blockedUserList = new ArrayList<>();
		
		try {
			callSt = con.prepareCall(GET_ALL_BLOCKED_USERS);
			ResultSet rs = callSt.executeQuery();
			
			while(rs.next()){
				BlockedUser blockedUser = createBlockedUserFromResultSet(rs);
				blockedUserList.add(blockedUser);
			}
		} finally {
			closeStatement(callSt);
			con.close();
		}
		return blockedUserList;
	}
		
	@Override
	public long save(Object o) throws SQLException {
		
		if(o == null || !(o instanceof BlockedUser)  || ((BlockedUser)o).getBlockTime() == null){
			return -1;
		}
		
		BlockedUser blockedUser = (BlockedUser)o;
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
				
		long result = -1;
		
		try {
			callSt = con.prepareCall(INSERT_BLOCKED_USER);
			
			int k = 0;
			callSt.setLong(++k, blockedUser.getUser().getId());
			callSt.setTimestamp(++k, blockedUser.getBlockTime());
			callSt.setString(++k, blockedUser.getDescription());
			
			result = callSt.executeUpdate();			
		} finally {
			closeStatement(callSt);
			con.close();
		}
		return result;
	}


	@Override
	public boolean remove(Object o) throws SQLException{
		if(o == null || !(o instanceof BlockedUser)  || ((BlockedUser)o).getBlockTime() == null
				|| getByUserID(((BlockedUser)o).getUser().getId()) == null){
			return false;
		}
		
		BlockedUser blockedUser = (BlockedUser)o;
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
		
		try {
			callSt = con.prepareCall(DELETE_BLOCKED_USER);
			callSt.setLong(1, blockedUser.getUser().getId());
			int rs = callSt.executeUpdate();
			if(rs > 0){
				return true;
				}
		} finally {
			closeStatement(callSt);
			con.close();
		}
		return false;
	}

	@Override
	public Object getByName(String name) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean update(Object o) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getByID(long id) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BlockedUser getByUser(User user) throws SQLException {
		return getByUserID(user.getId());
	}

}
