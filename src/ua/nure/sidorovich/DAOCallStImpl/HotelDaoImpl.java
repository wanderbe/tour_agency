package ua.nure.sidorovich.DAOCallStImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.nure.sidorovich.DAO.HotelDao;
import ua.nure.sidorovich.entety.Hotel;
import ua.nure.sidorovich.entety.HotelType;
import ua.nure.sidorovich.util.ConnectionManagerImpl;

public class HotelDaoImpl extends DAOSceleton implements HotelDao{
	
	public HotelDaoImpl(){
		super();
	}

	private static final String GET_ALL_HOTELS = "{call GET_ALL_HOTELS}";
	
	private static final String GET_HOTEL_BY_ID  = "{call GET_HOTEL_BY_ID(?)}";

	private static final String GET_HOTEL_BY_NAME = "{call GET_HOTEL_BY_NAME(?)}";
	
	private static final String UPDATE_HOTEL = "{call UPDATE_HOTEL(?, ?, ?, ?)}";
	
	private static final String DELETE_HOTEL_BY_ID = "{call DELETE_HOTEL_BY_ID(?)}";
	
	private static final String INSERT_HOTEL = "{call INSERT_HOTEL(?, ?, ?)}";

	private Hotel createHotelFromResultSet(ResultSet rs) throws SQLException {
		Hotel hotel = new Hotel();
		HotelType hotelType = new HotelType();
		
		int k = 0;
		hotel.setId(rs.getLong(++k));
		hotel.setName(rs.getString(++k));
		hotel.setDescription(rs.getString(++k));
		
		hotelType.setId(rs.getLong(++k));
		hotelType.setHotelClass(rs.getInt(++k));
		hotelType.setDescription(rs.getString(++k));
						
		hotel.setHotelType(hotelType);
		
		return hotel;
	}
	
	@Override
	public Hotel getByID(long l) throws SQLException{
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
		
		Hotel hotel = null;
		
		try {			
			callSt = con.prepareCall(GET_HOTEL_BY_ID);
			callSt.setLong(1, l);
			ResultSet rs = callSt.executeQuery();
			if(rs.next()){
				hotel = createHotelFromResultSet(rs);
				}
		} finally {
			closeStatement(callSt);
			con.close();
		}
		return hotel;
	}

	@Override
	public List<Hotel> getList() throws SQLException {
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
				
		List<Hotel> hotels = new ArrayList<>();
		
		try {
			callSt = con.prepareCall(GET_ALL_HOTELS);
			ResultSet rs = callSt.executeQuery();
			
			while(rs.next()){
				Hotel hotel = createHotelFromResultSet(rs);				
				hotels.add(hotel);
			}
		} finally {
			closeStatement(callSt);
			con.close();
		}
		return hotels;
	}

	@Override
	public Hotel getByName(String name) throws SQLException{
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
		
		Hotel hotel = null;
		
		try {
			callSt = con.prepareCall(GET_HOTEL_BY_NAME);
			callSt.setString(1, name);
			ResultSet rs = callSt.executeQuery();
			if(rs.next()){
				hotel = createHotelFromResultSet(rs);
				}
		} finally {
			closeStatement(callSt);
			con.close();
		}
		return hotel;
	}

	@Override
	public long save(Object o) throws SQLException{
		
		if(o == null || !(o instanceof Hotel) || ((Hotel)o).getName() == null){
			return -1;
		}
		
		Hotel hotel = (Hotel)o;
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
		
		long result = -1;
		
		try {
			callSt = con.prepareCall(INSERT_HOTEL);
			
			int k = 0;
			callSt.setString(++k, hotel.getName());
			callSt.setString(++k, hotel.getDescription());
			callSt.setLong(++k, hotel.getHotelType().getId());
			
			if(callSt.executeUpdate() > 0){
				result = getByName(hotel.getName()).getId();
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
				
		if(o == null || !(o instanceof Hotel) || ((Hotel)o).getId() == 0L || ((Hotel)o).getName() == null
				|| getByID(((Hotel)o).getId()) == null){
			return false;
		}
		
		Hotel hotel = (Hotel)o;
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnectionForTransactions();
		CallableStatement callSt = null;
				
		try {
			callSt = con.prepareCall(UPDATE_HOTEL);
			
			int k = 0;
			callSt.setString(++k, hotel.getName());
			callSt.setString(++k, hotel.getDescription());
			callSt.setLong(++k, hotel.getHotelType().getId());
			callSt.setLong(++k, hotel.getId());
						
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
				
		if(o == null || !(o instanceof Hotel) || ((Hotel)o).getId() == 0L || ((Hotel)o).getName() == null
				|| getByID(((Hotel)o).getId()) == null){
			return false;
		}
		
		Hotel hotel = (Hotel)o;
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
		
		try {
			callSt = con.prepareCall(DELETE_HOTEL_BY_ID);
			callSt.setLong(1, hotel.getId());
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
