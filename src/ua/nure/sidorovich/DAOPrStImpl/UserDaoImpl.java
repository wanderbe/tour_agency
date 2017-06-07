package ua.nure.sidorovich.DAOPrStImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

	private static final String GET_ALL_USERS
		= "SELECT users.id, users.name, users.birthday, users.email, users.phone, users.login, users.password, "
			+ "user_role.id, user_role.name, user_role.description "
			+ "FROM users LEFT JOIN user_role ON users.id_role=user_role.id ";
	
	private static final String GET_USER_BY_ID = GET_ALL_USERS + " WHERE users.id=?";

	private static final String GET_USER_BY_NAME = GET_ALL_USERS + " WHERE users.name=?";
		
	private static final String GET_USER_BY_LOGIN = GET_ALL_USERS + " WHERE users.login=?";
	
	private static final String GET_USER_BY_EMAIL = GET_ALL_USERS + " WHERE users.email=?";
	
	private static final String UPDATE_USER
		= "UPDATE users SET name =?, birthday =?, email =?, "
				+ "phone=?, login=?, password=?, id_role=? WHERE id =?";
	
	private static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE id=?";
	
	private static final String INSERT_USER 
		= "INSERT INTO users (name, birthday, email, phone, login, password, id_role) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";	

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
		PreparedStatement prepSt = null;
		
		User user = null;
		
		try {
			prepSt = con.prepareStatement(GET_USER_BY_ID);
			prepSt.setLong(1, l);
			ResultSet rs = prepSt.executeQuery();
			if(rs.next()){				
				user = createUserFromResultSet(rs);
				}
		} finally {
			closeStatement(prepSt);
			con.close();
		}
		return user;
	}

	@Override
	public List<User> getList() throws SQLException {
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		Statement st = null;
		
		List<User> users = new ArrayList<>();
		
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(GET_ALL_USERS);
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
		PreparedStatement prepSt = null;
		
		User user = null;
		
		try {
			prepSt = con.prepareStatement(GET_USER_BY_NAME);
			prepSt.setString(1, name);
			ResultSet rs = prepSt.executeQuery();
			if(rs.next()){
				user = createUserFromResultSet(rs);
				}
		} finally {
			closeStatement(prepSt);
			con.close();
		}
		return user;
	}
	
	@Override
	public User getByLogin(String login) throws SQLException {
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		PreparedStatement prepSt = null;
		
		User user = null;
		
		try {
			prepSt = con.prepareStatement(GET_USER_BY_LOGIN);
			prepSt.setString(1, login);
			ResultSet rs = prepSt.executeQuery();
			if(rs.next()){
				user = createUserFromResultSet(rs);
				}
		} finally {
			closeStatement(prepSt);
			con.close();
		}
		return user;
	}
	
	@Override
	public User getByEmail(String login) throws SQLException {
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		PreparedStatement prepSt = null;
		
		User user = null;
		
		try {
			prepSt = con.prepareStatement(GET_USER_BY_EMAIL);
			prepSt.setString(1, login);
			ResultSet rs = prepSt.executeQuery();
			if(rs.next()){
				user = createUserFromResultSet(rs);
				}
		} finally {
			closeStatement(prepSt);
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
		PreparedStatement prepSt = null;
		
		long result = -1;
		
		try {
			prepSt = con.prepareStatement(INSERT_USER);
			
			int k = 0;
			prepSt.setString(++k, user.getName());
			prepSt.setDate(++k, user.getBirthday());
			prepSt.setString(++k, user.getEmail());
			prepSt.setString(++k, user.getPhone());
			prepSt.setString(++k, user.getLogin());
			prepSt.setString(++k, user.getPassword());
			prepSt.setLong(++k, user.getRole().getId());
			
			if(prepSt.executeUpdate() > 0){
				result = getByLogin(user.getLogin()).getId();
			}
		} finally {
			closeStatement(prepSt);
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
		PreparedStatement prepSt = null;
				
		try {
			prepSt = con.prepareStatement(UPDATE_USER);
			
			int k = 0;
			prepSt.setString(++k, user.getName());
			prepSt.setDate(++k, user.getBirthday());
			prepSt.setString(++k, user.getEmail());
			prepSt.setString(++k, user.getPhone());
			prepSt.setString(++k, user.getLogin());
			prepSt.setString(++k, user.getPassword());
			prepSt.setLong(++k, user.getRole().getId());
			prepSt.setLong(++k, user.getId());
						
			result = prepSt.executeUpdate() > 0;
			con.commit();	
		} catch (SQLException e) {
			result = false;
			con.rollback();
		} finally {
			closeStatement(prepSt);
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
		PreparedStatement prepSt = null;
		
		try {
			prepSt = con.prepareStatement(DELETE_USER_BY_ID);
			prepSt.setLong(1, user.getId());
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

}
