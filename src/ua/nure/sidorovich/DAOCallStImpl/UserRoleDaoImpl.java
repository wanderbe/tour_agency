package ua.nure.sidorovich.DAOCallStImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.nure.sidorovich.DAO.UserRoleDao;
import ua.nure.sidorovich.entety.UserRole;
import ua.nure.sidorovich.util.ConnectionManagerImpl;

public class UserRoleDaoImpl extends DAOSceleton implements UserRoleDao{
	
	public UserRoleDaoImpl(){
		super();
	}

	private static final String GET_ALL_USER_ROLES = "{call GET_ALL_USER_ROLES}";
	
	private static final String GET_USER_ROLE_BY_ID  = "{call GET_USER_ROLE_BY_ID(?)}";

	private static final String GET_USER_ROLE_BY_NAME = "{call GET_USER_ROLE_BY_NAME(?)}";
	
	private static final String UPDATE_USER_ROLE = "{call UPDATE_USER_ROLE(?, ?, ?)}";
	
	private static final String DELETE_USER_ROLE_BY_ID  = "{call DELETE_USER_ROLE_BY_ID(?)}";
	
	private static final String INSERT_USER_ROLE = "{call INSERT_USER_ROLE(?, ?)}";

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
		CallableStatement callSt = null;
		
		UserRole userRole = null;
		
		try {
			callSt = con.prepareCall(GET_USER_ROLE_BY_ID);
			callSt.setLong(1, id);
			
			ResultSet rs = callSt.executeQuery();
			if(rs.next()){
				userRole = createUserRoleFromResultSet(rs);
				}
		} finally {
			closeStatement(callSt);
			con.close();
		}	
		return userRole;
	}

	@Override
	public List<UserRole> getList() throws SQLException{
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement st = null;
		
		List<UserRole> userRoles = new ArrayList<>();
		
		try {
			st = con.prepareCall(GET_ALL_USER_ROLES);
			ResultSet rs = st.executeQuery();
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
		CallableStatement callSt = null;
		UserRole userRole = null;
		
		try {
			callSt = con.prepareCall(GET_USER_ROLE_BY_NAME);
			callSt.setString(1, name);
			ResultSet rs = callSt.executeQuery();
			if(rs.next()){
				userRole = createUserRoleFromResultSet(rs);
				}
		} finally {
			closeStatement(callSt);
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
		CallableStatement callSt = null;
		
		long result = -1;
		
		try {
			callSt = con.prepareCall(INSERT_USER_ROLE);
			
			int k = 0;
			callSt.setString(++k, userRole.getName());
			callSt.setString(++k, userRole.getDescription()); //todo
			
			if(callSt.executeUpdate() > 0){
				result = getByName(userRole.getName()).getId();
			}
		} finally {
			closeStatement(callSt);
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
		CallableStatement callSt = null;
				
		try {
			callSt = con.prepareCall(UPDATE_USER_ROLE);
			
			int k = 0;
			callSt.setString(++k, userRole.getName());
			callSt.setString(++k, userRole.getDescription());
			callSt.setLong(++k, userRole.getId());
						
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
	public boolean remove(Object o) throws SQLException {
		
		if(o == null || !(o instanceof UserRole) || ((UserRole)o).getId() == 0L 
				|| ((UserRole)o).getName() == null || getByID(((UserRole)o).getId()) == null){
			return false;
		}
		
		UserRole userRole = (UserRole)o;
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
		
		try {
			callSt = con.prepareCall(DELETE_USER_ROLE_BY_ID);
			callSt.setLong(1, userRole.getId());
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
		
		System.out.println(new UserRoleDaoImpl().getByID(1));
		
		UserRole u = new UserRole();
		u.setDescription("userast1");
		u.setId(1);
		u.setName("user");
		
		System.out.println(new UserRoleDaoImpl().update(u));
		
		UserRole u1 = new UserRole();
		u1.setDescription("userast11");
		u1.setName("user1");
		
		//System.out.println(new UserRoleDaoImpl().save(u1));
		System.out.println(new UserRoleDaoImpl().remove(new UserRoleDaoImpl().getByName(u1.getName())));
		
		
		System.out.println(new UserRoleDaoImpl().getList());
	}
	
	

}
