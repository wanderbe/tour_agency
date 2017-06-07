package ua.nure.sidorovich.DAOPrStImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

	private static final String GET_ALL_HOTELS 
		= "SELECT hotels.id, hotels.name, hotels.description, hotel_types.id, hotel_types.class, "
				+ "hotel_types.description "
				+ "FROM hotels LEFT JOIN hotel_types ON hotels.hotel_type_id=hotel_types.id";
	
	private static final String GET_HOTEL_BY_ID = GET_ALL_HOTELS + " WHERE hotels.id=?";

	private static final String GET_HOTEL_BY_NAME = GET_ALL_HOTELS + " WHERE hotels.name=?";
	
	private static final String UPDATE_HOTEL 
		= "UPDATE hotels SET name =?, description =?, hotel_type_id=? WHERE id =?";
	
	private static final String DELETE_HOTEL_BY_ID = "DELETE FROM hotels WHERE id=?";
	
	private static final String INSERT_HOTEL 
		= "INSERT INTO hotels (name, description, hotel_type_id) VALUES (?, ?, ?)";

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
		PreparedStatement prepSt = null;
		
		Hotel hotel = null;
		
		try {			
			prepSt = con.prepareStatement(GET_HOTEL_BY_ID);
			prepSt.setLong(1, l);
			ResultSet rs = prepSt.executeQuery();
			if(rs.next()){
				hotel = createHotelFromResultSet(rs);
				}
		} finally {
			closeStatement(prepSt);
			con.close();
		}
		return hotel;
	}

	@Override
	public List<Hotel> getList() throws SQLException {
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		Statement st = null;
				
		List<Hotel> hotels = new ArrayList<>();
		
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(GET_ALL_HOTELS);
			
			while(rs.next()){
				Hotel hotel = createHotelFromResultSet(rs);				
				hotels.add(hotel);
			}
		} finally {
			closeStatement(st);
			con.close();
		}
		return hotels;
	}

	@Override
	public Hotel getByName(String name) throws SQLException{
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		PreparedStatement prepSt = null;
		
		Hotel hotel = null;
		
		try {
			prepSt = con.prepareStatement(GET_HOTEL_BY_NAME);
			prepSt.setString(1, name);
			ResultSet rs = prepSt.executeQuery();
			if(rs.next()){
				hotel = createHotelFromResultSet(rs);
				}
		} finally {
			closeStatement(prepSt);
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
		PreparedStatement prepSt = null;
		
		long result = -1;
		
		try {
			prepSt = con.prepareStatement(INSERT_HOTEL);
			
			int k = 0;
			prepSt.setString(++k, hotel.getName());
			prepSt.setString(++k, hotel.getDescription()); //todo
			prepSt.setLong(++k, hotel.getHotelType().getId());
			
			if(prepSt.executeUpdate() > 0){
				result = getByName(hotel.getName()).getId();
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
				
		if(o == null || !(o instanceof Hotel) || ((Hotel)o).getId() == 0L || ((Hotel)o).getName() == null
				|| getByID(((Hotel)o).getId()) == null){
			return false;
		}
		
		Hotel hotel = (Hotel)o;
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnectionForTransactions();
		PreparedStatement prepSt = null;
				
		try {
			prepSt = con.prepareStatement(UPDATE_HOTEL);
			
			int k = 0;
			prepSt.setString(++k, hotel.getName());
			prepSt.setString(++k, hotel.getDescription());
			prepSt.setLong(++k, hotel.getHotelType().getId());
			prepSt.setLong(++k, hotel.getId());
						
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
				
		if(o == null || !(o instanceof Hotel) || ((Hotel)o).getId() == 0L || ((Hotel)o).getName() == null
				|| getByID(((Hotel)o).getId()) == null){
			return false;
		}
		
		Hotel hotel = (Hotel)o;
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		PreparedStatement prepSt = null;
		
		try {
			prepSt = con.prepareStatement(DELETE_HOTEL_BY_ID);
			prepSt.setLong(1, hotel.getId());
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
