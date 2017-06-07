package ua.nure.sidorovich.DAOPrStImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

	private static final String GET_ALL_BLOCKED_USERS 
		= "SELECT blocked_users.time_start_block, blocked_users.description, users.id, users.name, "
				+ "users.birthday, users.email, users.phone, users.login, users.password, user_role.id, "
				+ "user_role.name, user_role.description "
				+ "FROM blocked_users "
				+ "LEFT JOIN users ON blocked_users.id_user=users.id "
				+ "LEFT JOIN user_role ON users.id_role=user_role.id";

	private static final String GET_BLOCKED_USER_BY_ID = GET_ALL_BLOCKED_USERS + " WHERE blocked_users.id_user=?";
			
	private static final String INSERT_BLOCKED_USER 
		= "INSERT INTO blocked_users (id_user, time_start_block, description) VALUES (?, ?, ?)";

	private static final String DELETE_BLOCKED_USER = "DELETE FROM blocked_users WHERE id_user=?";
	
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
		PreparedStatement prepSt = null;
		
		BlockedUser blockedUser = null;
		
		try {
			prepSt = con.prepareStatement(GET_BLOCKED_USER_BY_ID);
			
			int i = 0;
			prepSt.setLong(++i, userId);
			
			ResultSet rs = prepSt.executeQuery();
			
			while(rs.next()){
				blockedUser = createBlockedUserFromResultSet(rs);
			}
		} finally {
			closeStatement(prepSt);
			con.close();
			}
		return blockedUser;
	}
	
	@Override
	public List<BlockedUser> getList() throws SQLException {
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		Statement st = null;
				
		List<BlockedUser> blockedUserList = new ArrayList<>();
		
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(GET_ALL_BLOCKED_USERS);
			
			while(rs.next()){
				BlockedUser blockedUser = createBlockedUserFromResultSet(rs);
				blockedUserList.add(blockedUser);
			}
		} finally {
			closeStatement(st);
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
		PreparedStatement prepSt = null;
				
		long result = -1;
		
		try {
			prepSt = con.prepareStatement(INSERT_BLOCKED_USER);
			
			int k = 0;
			prepSt.setLong(++k, blockedUser.getUser().getId());
			prepSt.setTimestamp(++k, blockedUser.getBlockTime());
			prepSt.setString(++k, blockedUser.getDescription());
			
			result = prepSt.executeUpdate();			
		} finally {
			closeStatement(prepSt);
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
		PreparedStatement prepSt = null;
		
		try {
			prepSt = con.prepareStatement(DELETE_BLOCKED_USER);
			prepSt.setLong(1, blockedUser.getUser().getId());
			int rs = prepSt.executeUpdate();
			if(rs > 0){
				return true;
				}
		} finally {
			closeStatement(prepSt);
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
