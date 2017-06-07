package ua.nure.sidorovich.DAOPrStImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.nure.sidorovich.DAO.UserRoleDao;
import ua.nure.sidorovich.entety.UserRole;
import ua.nure.sidorovich.util.ConnectionManagerImpl;

public class UserRoleDaoImpl extends DAOSceleton implements UserRoleDao{
	
	public UserRoleDaoImpl(){
		super();
	}

	private static final String GET_ALL_USER_ROLES = "SELECT * FROM user_role";
	
	private static final String GET_USER_ROLE_BY_ID 
		= "SELECT * FROM user_role WHERE id=?";

	private static final String GET_USER_ROLE_BY_NAME 
		= "SELECT * FROM user_role WHERE name=?";
	
	private static final String UPDATE_USER_ROLE 
		= "UPDATE user_role SET name =?, description =? WHERE id =?";
	
	private static final String DELETE_USER_ROLE_BY_ID 
		= "DELETE FROM user_role WHERE id=?";
	
	private static final String INSERT_USER_ROLE 
		= "INSERT INTO user_role (name, description) VALUES (?, ?)";

	private UserRole createUserRoleFromResultSet(ResultSet rs) throws SQLException {
		UserRole userRole = new UserRole();
		int k = 0;
		userRole.setId(rs.getInt(++k));
		userRole.setName(rs.getString(++k));
		userRole.setDescription(rs.getString(++k));
		
		return userRole;
	}
	
	@Override
	public UserRole getByID(long id) throws SQLException{
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		PreparedStatement prepSt = null;
		
		UserRole userRole = null;
		
		try {
			prepSt = con.prepareStatement(GET_USER_ROLE_BY_ID);
			prepSt.setLong(1, id);
			
			ResultSet rs = prepSt.executeQuery();
			if(rs.next()){
				userRole = createUserRoleFromResultSet(rs);
				}
		} finally {
			closeStatement(prepSt);
			con.close();
		}	
		return userRole;
	}

	@Override
	public List<UserRole> getList() throws SQLException{
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		Statement st = null;
		
		List<UserRole> userRoles = new ArrayList<>();
		
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(GET_ALL_USER_ROLES);
			while(rs.next()){
				UserRole userRole = createUserRoleFromResultSet(rs);				
				userRoles.add(userRole);
			}
		} finally {
			closeStatement(st);
			con.close();
		}	
		return userRoles;
	}

	@Override
	public UserRole getByName(String name) throws SQLException {
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		PreparedStatement prepSt = null;
		UserRole userRole = null;
		
		try {
			prepSt = con.prepareStatement(GET_USER_ROLE_BY_NAME);
			prepSt.setString(1, name);
			ResultSet rs = prepSt.executeQuery();
			if(rs.next()){
				userRole = createUserRoleFromResultSet(rs);
				}
		} finally {
			closeStatement(prepSt);
			con.close();
		}	
		return userRole;
	}

	@Override
	public long save(Object o) throws SQLException {
		
		if(o == null || !(o instanceof UserRole || ((UserRole)o).getName() == null)){
			return -1;
		}
		
		UserRole userRole = (UserRole)o;
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		PreparedStatement prepSt = null;
		
		long result = -1;
		
		try {
			prepSt = con.prepareStatement(INSERT_USER_ROLE);
			
			int k = 0;
			prepSt.setString(++k, userRole.getName());
			prepSt.setString(++k, userRole.getDescription()); //todo
			
			if(prepSt.executeUpdate() > 0){
				result = getByName(userRole.getName()).getId();
			}
		} finally {
			closeStatement(prepSt);
			con.close();
		}	
		return result;
	}

	@Override
	public boolean update(Object o) throws SQLException {
		
		boolean result = false;
		
		if(o == null || !(o instanceof UserRole) || ((UserRole)o).getId() == 0L 
				|| ((UserRole)o).getName() == null || getByID(((UserRole)o).getId()) == null){
			return false;
		}
		
		UserRole userRole = (UserRole)o;
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnectionForTransactions();
		PreparedStatement prepSt = null;
				
		try {
			prepSt = con.prepareStatement(UPDATE_USER_ROLE);
			
			int k = 0;
			prepSt.setString(++k, userRole.getName());
			prepSt.setString(++k, userRole.getDescription());
			prepSt.setLong(++k, userRole.getId());
						
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
	public boolean remove(Object o) throws SQLException {
		
		if(o == null || !(o instanceof UserRole) || ((UserRole)o).getId() == 0L 
				|| ((UserRole)o).getName() == null || getByID(((UserRole)o).getId()) == null){
			return false;
		}
		
		UserRole userRole = (UserRole)o;
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		PreparedStatement prepSt = null;
		
		try {
			prepSt = con.prepareStatement(DELETE_USER_ROLE_BY_ID);
			prepSt.setLong(1, userRole.getId());
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
