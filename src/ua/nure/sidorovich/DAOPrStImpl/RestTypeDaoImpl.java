package ua.nure.sidorovich.DAOPrStImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.nure.sidorovich.DAO.RestTypeDao;
import ua.nure.sidorovich.entety.RestType;
import ua.nure.sidorovich.util.ConnectionManagerImpl;

public class RestTypeDaoImpl extends DAOSceleton implements RestTypeDao{
	
	public RestTypeDaoImpl(){
		super();
	}

	private static final String GET_ALL_REST_TYPES = "SELECT * FROM rest_type";
	
	private static final String GET_REST_TYPE_BY_ID 
		= "SELECT * FROM rest_type WHERE id=?";

	private static final String GET_REST_TYPE_BY_NAME 
		= "SELECT * FROM rest_type WHERE name=?";
	
	private static final String UPDATE_REST_TYPE 
		= "UPDATE rest_type SET name =?, description =? WHERE id =?";
	
	private static final String DELETE_REST_TYPE_BY_ID 
		= "DELETE FROM rest_type WHERE id=?";
	
	private static final String INSERT_REST_TYPE 
		= "INSERT INTO rest_type (name, description) VALUES (?, ?)";

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
		PreparedStatement prepSt = null;
		
		RestType restType = null;
		
		try {
			prepSt = con.prepareStatement(GET_REST_TYPE_BY_ID);
			prepSt.setLong(1, l);
			ResultSet rs = prepSt.executeQuery();
			if(rs.next()){
				restType = createRestTypeFromResultSet(rs);
				}
		} finally {
			closeStatement(prepSt);
			con.close();
		}
		return restType;
	}

	@Override
	public List<RestType> getList() throws SQLException {
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		Statement st = null;
		
		List<RestType> restTypes = new ArrayList<>();
		
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(GET_ALL_REST_TYPES);
			while(rs.next()){
				RestType restType = createRestTypeFromResultSet(rs);
				restTypes.add(restType);
			}
		} finally {
			closeStatement(st);
			con.close();
		}
		return restTypes;
	}

	@Override
	public RestType getByName(String name) throws SQLException {
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		PreparedStatement prepSt = null;
		
		RestType restType = null;
		
		try {
			prepSt = con.prepareStatement(GET_REST_TYPE_BY_NAME);
			prepSt.setString(1, name);
			ResultSet rs = prepSt.executeQuery();
			if(rs.next()){
				restType = createRestTypeFromResultSet(rs);
				}
		} finally {
			closeStatement(prepSt);
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
		PreparedStatement prepSt = null;
		
		long result = -1;
		
		try {
			prepSt = con.prepareStatement(INSERT_REST_TYPE);
			
			int k = 0;
			prepSt.setString(++k, restType.getName());
			prepSt.setString(++k, restType.getDescription()); //todo
			
			if(prepSt.executeUpdate() > 0){
				result = getByName(restType.getName()).getId();
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
		
		if(o == null || !(o instanceof RestType) || ((RestType)o).getId() == 0L || ((RestType)o).getName() == null
				|| getByID(((RestType)o).getId()) == null){
			return false;
		}
		
		RestType restType = (RestType)o;
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnectionForTransactions();
		PreparedStatement prepSt = null;
				
		try {
			prepSt = con.prepareStatement(UPDATE_REST_TYPE);
			
			int k = 0;
			prepSt.setString(++k, restType.getName());
			prepSt.setString(++k, restType.getDescription());
			prepSt.setLong(++k, restType.getId());
						
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
				
		if(o == null || !(o instanceof RestType) || ((RestType)o).getId() == 0L || ((RestType)o).getName() == null
				|| getByID(((RestType)o).getId()) == null){
			return false;
		}
		
		RestType restType = (RestType)o;
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		PreparedStatement prepSt = null;
		
		try {
			prepSt = con.prepareStatement(DELETE_REST_TYPE_BY_ID);
			prepSt.setLong(1, restType.getId());
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
