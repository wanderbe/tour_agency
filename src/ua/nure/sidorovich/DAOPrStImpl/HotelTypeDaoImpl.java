package ua.nure.sidorovich.DAOPrStImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.nure.sidorovich.DAO.HotelTypeDao;
import ua.nure.sidorovich.entety.HotelType;
import ua.nure.sidorovich.util.ConnectionManagerImpl;

public class HotelTypeDaoImpl extends DAOSceleton implements HotelTypeDao{
	
	public HotelTypeDaoImpl(){
		super();
	}

	private static final String FIND_ALL_HOTEL_TYPES = "SELECT * FROM hotel_types";
	
	private static final String GET_HOTEL_TYPE_BY_ID 
		= "SELECT * FROM hotel_types WHERE id=?";

	private static final String GET_REST_TYPE_BY_CLASS_OF_HOTEL 
		= "SELECT * FROM hotel_types WHERE class=?";
	
	private static final String UPDATE_HOTEL_TYPE 
		= "UPDATE hotel_types SET class =?, description =? WHERE id =?";
	
	private static final String DELETE_HOTEL_TYPE_BY_ID 
		= "DELETE FROM hotel_types WHERE id=?";
	
	private static final String INSERT_HOTEL_TYPE 
		= "INSERT INTO hotel_types (class, description) VALUES (?, ?)";

	private HotelType createHotelTypeFromResultSet(ResultSet rs) throws SQLException {
		HotelType hotelType = new HotelType();
		int k = 0;
		hotelType.setId(rs.getInt(++k));
		hotelType.setHotelClass(rs.getInt(++k));
		hotelType.setDescription(rs.getString(++k));
		
		return hotelType;
	}
	
	@Override
	public HotelType getByID(long l) throws SQLException{
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		PreparedStatement prepSt = null;
		
		HotelType hotelType = null;
		
		try {
			prepSt = con.prepareStatement(GET_HOTEL_TYPE_BY_ID);
			prepSt.setLong(1, l);
			ResultSet rs = prepSt.executeQuery();
			if(rs.next()){
				hotelType = createHotelTypeFromResultSet(rs);
				}
		} finally {
			closeStatement(prepSt);
			con.close();
		}
		return hotelType;
	}

	@Override
	public List<HotelType> getList() throws SQLException {
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		Statement st = null;
		
		List<HotelType> hotelTypeList = new ArrayList<>();
		
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(FIND_ALL_HOTEL_TYPES);
			while(rs.next()){
				HotelType hotelType = createHotelTypeFromResultSet(rs);
				hotelTypeList.add(hotelType);
			}
		} finally {
			closeStatement(st);
			con.close();
		}
		return hotelTypeList;
	}

	@Override
	public HotelType getByHotelClass(int hotelClass) throws SQLException{
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		PreparedStatement prepSt = null;
		
		HotelType hotelType = null;
		
		try {
			prepSt = con.prepareStatement(GET_REST_TYPE_BY_CLASS_OF_HOTEL);
			prepSt.setInt(1, hotelClass);
			ResultSet rs = prepSt.executeQuery();
			if(rs.next()){
				hotelType = createHotelTypeFromResultSet(rs);
				}
		} finally {
			closeStatement(prepSt);
			con.close();
		}
		return hotelType;
	}

	@Override
	public long save(Object o) throws SQLException {
		
		if(o == null || !(o instanceof HotelType) || ((HotelType)o).getHotelClass() == 0){   //todo
			return -1;
		}
		
		HotelType hotelType = (HotelType)o; 
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		PreparedStatement prepSt = null;
		
		long result = -1;
		
		try {
			prepSt = con.prepareStatement(INSERT_HOTEL_TYPE);
			
			int k = 0;
			prepSt.setInt(++k, hotelType.getHotelClass());
			prepSt.setString(++k, hotelType.getDescription()); //todo
			
			if(prepSt.executeUpdate() > 0){
				result = getByHotelClass(hotelType.getHotelClass()).getId();
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
		
		if(o == null || !(o instanceof HotelType) || ((HotelType)o).getId() == 0L 
				|| ((HotelType)o).getHotelClass() == 0 
				|| getByID(((HotelType)o).getId()) == null){
			return false;
		}
		
		HotelType hotelType = (HotelType)o; 
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnectionForTransactions();
		PreparedStatement prepSt = null;
				
		try {
			prepSt = con.prepareStatement(UPDATE_HOTEL_TYPE);
			
			int k = 0;
			prepSt.setInt(++k, hotelType.getHotelClass());
			prepSt.setString(++k, hotelType.getDescription());
			prepSt.setLong(++k, hotelType.getId());
						
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
				
		if(o == null || !(o instanceof HotelType) || ((HotelType)o).getId() == 0L 
				|| ((HotelType)o).getHotelClass() == 0
				|| getByID(((HotelType)o).getId()) == null){
			return false;
		}
		
		HotelType hotelType = (HotelType)o; 
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		PreparedStatement prepSt = null;
		
		try {
			prepSt = con.prepareStatement(DELETE_HOTEL_TYPE_BY_ID);
			prepSt.setLong(1, hotelType.getId());
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
	public Object getByName(String name) throws SQLException{
		throw new UnsupportedOperationException();
	}
	
	

}
