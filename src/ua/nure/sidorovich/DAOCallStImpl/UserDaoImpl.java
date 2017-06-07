package ua.nure.sidorovich.DAOCallStImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.nure.sidorovich.DAO.UserDao;
import ua.nure.sidorovich.entety.User;
import ua.nure.sidorovich.entety.UserRole;
import ua.nure.sidorovich.util.ConnectionManagerImpl;

public class UserDaoImpl extends DAOSceleton implements UserDao{
	
	public UserDaoImpl(){
		super();
	}

	private static final String GET_ALL_USERS = "{call GET_ALL_USERS}";
	
	private static final String GET_USER_BY_ID = "{call GET_USER_BY_ID(?)}";

	private static final String GET_USER_BY_NAME = "{call GET_USER_BY_NAME(?)}";
		
	private static final String GET_USER_BY_LOGIN = "{call GET_USER_BY_LOGIN(?)}";
	
	private static final String GET_USER_BY_EMAIL = "{call GET_USER_BY_EMAIL(?)}";
	
	private static final String UPDATE_USER = "{call UPDATE_USER(?, ?, ?, ?, ?, ?, ?, ?)}";
	
	private static final String DELETE_USER_BY_ID = "{call DELETE_USER_BY_ID(?)}";
	
	private static final String INSERT_USER = "{call INSERT_USER(?, ?, ?, ?, ?, ?, ?)}";

	private User createUserFromResultSet(ResultSet rs) throws SQLException {
		User user = new User();
		UserRole userRole = new UserRole();
		
		int k = 0;
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
		
		return user;
	}
	
	@Override
	public User getByID(long l) throws SQLException{
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
		
		User user = null;
		
		try {
			callSt = con.prepareCall(GET_USER_BY_ID);
			callSt.setLong(1, l);
			ResultSet rs = callSt.executeQuery();
			if(rs.next()){				
				user = createUserFromResultSet(rs);
				}
		} finally {
			closeStatement(callSt);
			con.close();
		}
		return user;
	}

	@Override
	public List<User> getList() throws SQLException {
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement st = null;
		
		List<User> users = new ArrayList<>();
		
		try {
			st = con.prepareCall(GET_ALL_USERS);
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				User user = createUserFromResultSet(rs);				
				users.add(user);
			}
		} finally {
			closeStatement(st);
			con.close();
		}
		return users;
	}

	@Override
	public User getByName(String name) throws SQLException {
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
		
		User user = null;
		
		try {
			callSt = con.prepareCall(GET_USER_BY_NAME);
			callSt.setString(1, name);
			ResultSet rs = callSt.executeQuery();
			if(rs.next()){
				user = createUserFromResultSet(rs);
				}
		} finally {
			closeStatement(callSt);
			con.close();
		}
		return user;
	}
	
	@Override
	public User getByLogin(String login) throws SQLException {
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
		
		User user = null;
		
		try {
			callSt = con.prepareCall(GET_USER_BY_LOGIN);
			callSt.setString(1, login);
			ResultSet rs = callSt.executeQuery();
			if(rs.next()){
				user = createUserFromResultSet(rs);
				}
		} finally {
			closeStatement(callSt);
			con.close();
		}
		return user;
	}
	
	@Override
	public User getByEmail(String login) throws SQLException {
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
		
		User user = null;
		
		try {
			callSt = con.prepareCall(GET_USER_BY_EMAIL);
			callSt.setString(1, login);
			ResultSet rs = callSt.executeQuery();
			if(rs.next()){
				user = createUserFromResultSet(rs);
				}
		} finally {
			closeStatement(callSt);
			con.close();
		}
		return user;
	}

	@Override
	public long save(Object o) throws SQLException{
		
		if(o == null || !(o instanceof User)  || ((User)o).getName() == null){
			return -1;
		}
		
		User user = (User)o;
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
		
		long result = -1;
		
		try {
			callSt = con.prepareCall(INSERT_USER);
			
			int k = 0;
			callSt.setString(++k, user.getName());
			callSt.setDate(++k, user.getBirthday());
			callSt.setString(++k, user.getEmail());
			callSt.setString(++k, user.getPhone());
			callSt.setString(++k, user.getLogin());
			callSt.setString(++k, user.getPassword());
			callSt.setLong(++k, user.getRole().getId());
			
			if(callSt.executeUpdate() > 0){
				result = getByLogin(user.getLogin()).getId();
			}
		} finally {
			closeStatement(callSt);
			con.close();
		}
		return result;
	}

	@Override
	public boolean update(Object o) throws SQLException{
		
		boolean result = false;
		
		if(o == null || !(o instanceof User) || ((User)o).getId() == 0L || 
				((User)o).getName() == null || getByID(((User)o).getId()) == null){
			return false;
		}
		
		User user = (User)o;
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnectionForTransactions();
		CallableStatement callSt = null;
				
		try {
			callSt = con.prepareCall(UPDATE_USER);
			
			int k = 0;
			callSt.setString(++k, user.getName());
			callSt.setDate(++k, user.getBirthday());
			callSt.setString(++k, user.getEmail());
			callSt.setString(++k, user.getPhone());
			callSt.setString(++k, user.getLogin());
			callSt.setString(++k, user.getPassword());
			callSt.setLong(++k, user.getRole().getId());
			callSt.setLong(++k, user.getId());
						
			result = callSt.executeUpdate() > 0;
			con.commit();	
		} catch (SQLException e) {
			result = false;
			con.rollback();
		} finally {
			closeStatement(callSt);
			con.close();
		}
		return result;			
	}

	@Override
	public boolean remove(Object o) throws SQLException{
		
		if(o == null || !(o instanceof User) || ((User)o).getId() == 0L || 
				((User)o).getName() == null || getByID(((User)o).getId()) == null){
			return false;
		}
		
		User user = (User)o;
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
		
		try {
			callSt = con.prepareCall(DELETE_USER_BY_ID);
			callSt.setLong(1, user.getId());
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

	
public static void main(String[] args) throws SQLException{
		
		System.out.println(new UserDaoImpl().getByID(1));
	}
}
