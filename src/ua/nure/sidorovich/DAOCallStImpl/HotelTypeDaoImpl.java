package ua.nure.sidorovich.DAOCallStImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.nure.sidorovich.DAO.HotelTypeDao;
import ua.nure.sidorovich.entety.HotelType;
import ua.nure.sidorovich.util.ConnectionManagerImpl;

public class HotelTypeDaoImpl extends DAOSceleton implements HotelTypeDao{
	
	public HotelTypeDaoImpl(){
		super();
	}

	private static final String GET_ALL_HOTEL_TYPES = "{call FIND_ALL_HOTEL_TYPES}";
	
	private static final String GET_HOTEL_TYPE_BY_ID = "{call GET_HOTEL_TYPE_BY_ID(?)}";

	private static final String GET_HOTEL_TYPE_BY_CLASS_OF_HOTEL = "{call GET_HOTEL_TYPE_BY_CLASS_OF_HOTEL(?)}";
	
	private static final String UPDATE_HOTEL_TYPE = "{call UPDATE_HOTEL_TYPE(?, ?, ?)}";
	
	private static final String DELETE_HOTEL_TYPE_BY_ID = "{call DELETE_HOTEL_TYPE_BY_ID(?)}";
	
	private static final String INSERT_HOTEL_TYPE = "{call INSERT_HOTEL_TYPE(?, ?)}";

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
		CallableStatement callSt = null;
		
		HotelType hotelType = null;
		
		try {
			callSt = con.prepareCall(GET_HOTEL_TYPE_BY_ID);
			callSt.setLong(1, l);
			ResultSet rs = callSt.executeQuery();
			if(rs.next()){
				hotelType = createHotelTypeFromResultSet(rs);
				}
		} finally {
			closeStatement(callSt);
			con.close();
		}
		return hotelType;
	}

	@Override
	public List<HotelType> getList() throws SQLException {
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
		
		List<HotelType> hotelTypeList = new ArrayList<>();
		
		try {
			callSt = con.prepareCall(GET_ALL_HOTEL_TYPES);
			ResultSet rs = callSt.executeQuery();
			while(rs.next()){
				HotelType hotelType = createHotelTypeFromResultSet(rs);
				hotelTypeList.add(hotelType);
			}
		} finally {
			closeStatement(callSt);
			con.close();
		}
		return hotelTypeList;
	}

	@Override
	public HotelType getByHotelClass(int hotelClass) throws SQLException{
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
		
		HotelType hotelType = null;
		
		try {
			callSt = con.prepareCall(GET_HOTEL_TYPE_BY_CLASS_OF_HOTEL);
			callSt.setInt(1, hotelClass);
			ResultSet rs = callSt.executeQuery();
			if(rs.next()){
				hotelType = createHotelTypeFromResultSet(rs);
				}
		} finally {
			closeStatement(callSt);
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
		CallableStatement callSt = null;
		
		long result = -1;
		
		try {
			callSt = con.prepareCall(INSERT_HOTEL_TYPE);
			
			int k = 0;
			callSt.setInt(++k, hotelType.getHotelClass());
			callSt.setString(++k, hotelType.getDescription()); //todo
			
			if(callSt.executeUpdate() > 0){
				result = getByHotelClass(hotelType.getHotelClass()).getId();
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
		
		if(o == null || !(o instanceof HotelType) || ((HotelType)o).getId() == 0L 
				|| ((HotelType)o).getHotelClass() == 0 
				|| getByID(((HotelType)o).getId()) == null){
			return false;
		}
		
		HotelType hotelType = (HotelType)o; 
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnectionForTransactions();
		CallableStatement callSt = null;
				
		try {
			callSt = con.prepareCall(UPDATE_HOTEL_TYPE);
			
			int k = 0;
			callSt.setInt(++k, hotelType.getHotelClass());
			callSt.setString(++k, hotelType.getDescription());
			callSt.setLong(++k, hotelType.getId());
						
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
				
		if(o == null || !(o instanceof HotelType) || ((HotelType)o).getId() == 0L 
				|| ((HotelType)o).getHotelClass() == 0
				|| getByID(((HotelType)o).getId()) == null){
			return false;
		}
		
		HotelType hotelType = (HotelType)o; 
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
		
		try {
			callSt = con.prepareCall(DELETE_HOTEL_TYPE_BY_ID);
			callSt.setLong(1, hotelType.getId());
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
	public Object getByName(String name) throws SQLException{
		throw new UnsupportedOperationException();
	}
	
	

}
