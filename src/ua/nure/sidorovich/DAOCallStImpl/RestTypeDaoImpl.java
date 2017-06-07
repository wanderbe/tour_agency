package ua.nure.sidorovich.DAOCallStImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.nure.sidorovich.DAO.RestTypeDao;
import ua.nure.sidorovich.entety.RestType;
import ua.nure.sidorovich.util.ConnectionManagerImpl;

public class RestTypeDaoImpl extends DAOSceleton implements RestTypeDao{
	
	public RestTypeDaoImpl(){
		super();
	}

	private static final String GET_ALL_REST_TYPES = "{call GET_ALL_REST_TYPES}";
	
	private static final String GET_REST_TYPE_BY_ID = "{call GET_REST_TYPE_BY_ID(?)}";

	private static final String GET_REST_TYPE_BY_NAME = "{call GET_REST_TYPE_BY_NAME(?)}";
	
	private static final String UPDATE_REST_TYPE = "{call UPDATE_REST_TYPE(?, ?, ?)}";
	
	private static final String DELETE_REST_TYPE_BY_ID = "{call DELETE_REST_TYPE_BY_ID(?)}";
	
	private static final String INSERT_REST_TYPE = "{call INSERT_REST_TYPE(?, ?)}";

	private RestType createRestTypeFromResultSet(ResultSet rs) throws SQLException {
		RestType restType = new RestType();
		int k = 0;
		restType.setId(rs.getInt(++k));
		restType.setName(rs.getString(++k));
		restType.setDescription(rs.getString(++k));
		
		return restType;
	}
	
	@Override
	public RestType getByID(long l) throws SQLException{
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callpSt = null;
		
		RestType restType = null;
		
		try {
			callpSt = con.prepareCall(GET_REST_TYPE_BY_ID);
			callpSt.setLong(1, l);
			ResultSet rs = callpSt.executeQuery();
			if(rs.next()){
				restType = createRestTypeFromResultSet(rs);
				}
		} finally {
			closeStatement(callpSt);
			con.close();
		}
		return restType;
	}

	@Override
	public List<RestType> getList() throws SQLException {
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
		
		List<RestType> restTypes = new ArrayList<>();
		
		try {
			callSt = con.prepareCall(GET_ALL_REST_TYPES);
			ResultSet rs = callSt.executeQuery();
			while(rs.next()){
				RestType restType = createRestTypeFromResultSet(rs);
				restTypes.add(restType);
			}
		} finally {
			closeStatement(callSt);
			con.close();
		}
		return restTypes;
	}

	@Override
	public RestType getByName(String name) throws SQLException {
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
		
		RestType restType = null;
		
		try {
			callSt = con.prepareCall(GET_REST_TYPE_BY_NAME);
			callSt.setString(1, name);
			ResultSet rs = callSt.executeQuery();
			if(rs.next()){
				restType = createRestTypeFromResultSet(rs);
				}
		} finally {
			closeStatement(callSt);
			con.close();
		}
		return restType;
	}

	@Override
	public long save(Object o) throws SQLException{
		
		if(o == null || !(o instanceof RestType) || ((RestType)o).getName() == null){   //todo
			return -1;
		}
		
		RestType restType = (RestType)o; 
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
		
		long result = -1;
		
		try {
			callSt = con.prepareCall(INSERT_REST_TYPE);
			
			int k = 0;
			callSt.setString(++k, restType.getName());
			callSt.setString(++k, restType.getDescription()); //todo
			
			if(callSt.executeUpdate() > 0){
				result = getByName(restType.getName()).getId();
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
		
		if(o == null || !(o instanceof RestType) || ((RestType)o).getId() == 0L || ((RestType)o).getName() == null
				|| getByID(((RestType)o).getId()) == null){
			return false;
		}
		
		RestType restType = (RestType)o;
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnectionForTransactions();
		CallableStatement callSt = null;
				
		try {
			callSt = con.prepareCall(UPDATE_REST_TYPE);
			
			int k = 0;
			callSt.setString(++k, restType.getName());
			callSt.setString(++k, restType.getDescription());
			callSt.setLong(++k, restType.getId());
						
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
				
		if(o == null || !(o instanceof RestType) || ((RestType)o).getId() == 0L || ((RestType)o).getName() == null
				|| getByID(((RestType)o).getId()) == null){
			return false;
		}
		
		RestType restType = (RestType)o;
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
		
		try {
			callSt = con.prepareCall(DELETE_REST_TYPE_BY_ID);
			callSt.setLong(1, restType.getId());
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
	
	

}
