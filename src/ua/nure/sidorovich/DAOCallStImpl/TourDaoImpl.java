package ua.nure.sidorovich.DAOCallStImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.nure.sidorovich.DAO.TourDao;
import ua.nure.sidorovich.entety.Hotel;
import ua.nure.sidorovich.entety.HotelType;
import ua.nure.sidorovich.entety.RestType;
import ua.nure.sidorovich.entety.Tour;
import ua.nure.sidorovich.util.ConnectionManagerImpl;

public class TourDaoImpl extends DAOSceleton implements TourDao{
		
	public TourDaoImpl(){
		super();
	}

	private static final String GET_ALL_TOURS = "{call GET_ALL_TOURS}";
	
	private static final String GET_TOUR_BY_ID = "{call GET_TOUR_BY_ID(?)}";

	private static final String GET_TOUR_BY_NAME = "{call GET_TOUR_BY_NAME(?)}";
	
	private static final String GET_TOURS_BY_PARAMETRS = "{call GET_TOURS_BY_PARAMETRS(?, ?, ?, ?, ?, ?, ?, ?)}";
	
	private static final String UPDATE_TOUR = "{call UPDATE_TOUR(?, ?, ?, ?, ?, ?, ?, ?)}";
	
	private static final String DELETE_TOUR_BY_ID = "{call DELETE_TOUR_BY_ID(?)}";
	
	private static final String INSERT_TOUR = "{call INSERT_TOUR(?, ?, ?, ?, ?, ?, ?)}";


	private Tour createTourFromResultSet(ResultSet rs) throws SQLException {
				
		Tour tour = new Tour();
		RestType restType = new RestType();
		Hotel hotel = new Hotel();
		HotelType hotelType = new HotelType();
		
		int k = 0;
		tour.setId(rs.getLong(++k));
		tour.setName(rs.getString(++k));
		tour.setDescription(rs.getString(++k));
		tour.setPlaces(rs.getInt(++k));
		tour.setPrice(rs.getDouble(++k));
		tour.setItHot(rs.getBoolean(++k));
		
		restType.setId(rs.getLong(++k));
		restType.setName(rs.getString(++k));
		restType.setDescription(rs.getString(++k));
		
		hotel.setId(rs.getLong(++k));
		hotel.setName(rs.getString(++k));
		hotel.setDescription(rs.getString(++k));
		
		hotelType.setId(rs.getLong(++k));
		hotelType.setHotelClass(rs.getInt(++k));
		hotelType.setDescription(rs.getString(++k));
		
		hotel.setHotelType(hotelType);	
		tour.setHotel(hotel);
		tour.setRestType(restType);
		
		return tour;
	}
	
	@Override
	public List<Tour> findByParametrs(long lookingRestTypeId, int lookingStartPrice, int lookingFinishPrice,
			int lookingPersonsAmount, long lookingHotelTypeId) throws SQLException{
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();	
		PreparedStatement prepSt = null;
		List<Tour> tours = new ArrayList<>();
		
		try {
			prepSt = con.prepareStatement(GET_TOURS_BY_PARAMETRS);
			
			int i = 0;
			
			if(lookingRestTypeId < 0){
				prepSt.setLong(++i, 0);
				prepSt.setLong(++i, Integer.MAX_VALUE);  //todo
			} else {
				prepSt.setLong(++i, lookingRestTypeId);
				prepSt.setLong(++i, lookingRestTypeId);
			}
			
			if(lookingStartPrice < 0){
				prepSt.setLong(++i, 0);
			} else{
				prepSt.setLong(++i, lookingStartPrice);
			}
			
			if(lookingFinishPrice < 0){
				prepSt.setLong(++i, Integer.MAX_VALUE);
			} else{
				prepSt.setLong(++i, lookingFinishPrice);
			}			
			
			if(lookingPersonsAmount < 0){
				prepSt.setLong(++i, 0);
				prepSt.setLong(++i, Integer.MAX_VALUE);  //todo
			} else {
				prepSt.setLong(++i, lookingPersonsAmount);
				prepSt.setLong(++i, lookingPersonsAmount);
			}
			
			if(lookingHotelTypeId < 0){
				prepSt.setLong(++i, 0);
				prepSt.setLong(++i, Integer.MAX_VALUE);  //todo
			} else {
				prepSt.setLong(++i, lookingHotelTypeId);
				prepSt.setLong(++i, lookingHotelTypeId);
			}
						
			ResultSet rs = prepSt.executeQuery();
			while(rs.next()){
				Tour tour = createTourFromResultSet(rs);		
				tours.add(tour);		
			}
		} finally {
			closeStatement(prepSt);
			con.close();
		}
		
		return tours;
	}	
	
	@Override
	public Tour getByID(long l) throws SQLException{
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		
		Tour tour = null;
		CallableStatement callSt = null;
		
		try {
			callSt = con.prepareCall(GET_TOUR_BY_ID);
			callSt.setLong(1, l);
			ResultSet rs = callSt.executeQuery();
			if(rs.next()){
				tour = createTourFromResultSet(rs);
				}
		} finally {
			closeStatement(callSt);
			con.close();
		}
		return tour;
	}

	@Override
	public List<Tour> getList() throws SQLException{
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		
		List<Tour> tours = new ArrayList<>();
		CallableStatement callSt = null;
		
		try {
			callSt = con.prepareCall(GET_ALL_TOURS);
			ResultSet rs = callSt.executeQuery();
			while(rs.next()){
				Tour tour = new Tour();
				RestType restType = new RestType();
				Hotel hotel = new Hotel();
				HotelType hotelType = new HotelType();
				
				int k = 0;
				tour.setId(rs.getLong(++k));
				tour.setName(rs.getString(++k));
				tour.setDescription(rs.getString(++k));
				tour.setPlaces(rs.getInt(++k));
				tour.setPrice(rs.getDouble(++k));
				tour.setItHot(rs.getBoolean(++k));
				
				restType.setId(rs.getLong(++k));
				restType.setName(rs.getString(++k));
				restType.setDescription(rs.getString(++k));
				
				hotel.setId(rs.getLong(++k));
				hotel.setName(rs.getString(++k));
				hotel.setDescription(rs.getString(++k));
				
				hotelType.setId(rs.getLong(++k));
				hotelType.setHotelClass(rs.getInt(++k));
				hotelType.setDescription(rs.getString(++k));
				
				hotel.setHotelType(hotelType);	
				tour.setHotel(hotel);
				tour.setRestType(restType);
								
				tours.add(tour);						
			}			
		} finally {
			closeStatement(callSt);
			con.close();
		}
		return tours;
	}

	@Override
	public Tour getByName(String name) throws SQLException{
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		
		Tour tour = null;
		CallableStatement callSt = null;
		
		try {
			callSt = con.prepareCall(GET_TOUR_BY_NAME);
			callSt.setString(1, name);
			ResultSet rs = callSt.executeQuery();
			if(rs.next()){
				tour = createTourFromResultSet(rs);
				}
		} finally {
			closeStatement(callSt);
			con.close();
		}
		return tour;
	}

	@Override
	public long save(Object o) throws SQLException{
		
		if(o == null || !(o instanceof Tour)  || ((Tour)o).getName() == null){
			return -1;
		}
		
		Tour tour = (Tour)o;
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		
		long result = -1;
		CallableStatement callSt = null;
		
		try {
			callSt = con.prepareCall(INSERT_TOUR);
			
			int k = 0;
			callSt.setString(++k, tour.getName());
			callSt.setString(++k, tour.getDescription());
			callSt.setLong(++k, tour.getRestType().getId());
			callSt.setInt(++k, tour.getPlaces());
			callSt.setLong(++k, tour.getHotel().getId());
			callSt.setDouble(++k, tour.getPrice());
			callSt.setBoolean(++k, tour.isItHot());
			
			if(callSt.executeUpdate() > 0){
				result = getByName(tour.getName()).getId();
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
		
		if(o == null || !(o instanceof Tour) || ((Tour)o).getId() == 0L || 
				((Tour)o).getName() == null || getByID(((Tour)o).getId()) == null){
			return false;
		}
		
		Tour tour = (Tour)o;
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnectionForTransactions();
		CallableStatement callSt = null;
				
		try {
			callSt = con.prepareCall(UPDATE_TOUR);
			
			int k = 0;
			callSt.setString(++k, tour.getName());
			callSt.setString(++k, tour.getDescription());
			callSt.setLong(++k, tour.getRestType().getId());
			callSt.setInt(++k, tour.getPlaces());
			callSt.setLong(++k, tour.getHotel().getId());
			callSt.setDouble(++k, tour.getPrice());
			callSt.setBoolean(++k, tour.isItHot());
			callSt.setLong(++k, tour.getId());
						
			result = callSt.executeUpdate() > 0;
			
			con.commit();
						
		} catch (SQLException e) {
			result = false;
			con.rollback();
		}  finally {
			closeStatement(callSt);
			con.close();
		}	
		return result;			
	}

	@Override
	public boolean remove(Object o) throws SQLException{
		
		if(o == null || !(o instanceof Tour) || ((Tour)o).getId() == 0L || 
				((Tour)o).getName() == null || getByID(((Tour)o).getId()) == null){
			return false;
		}
		
		Tour tour = (Tour)o;
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
		
		try {
			callSt = con.prepareCall(DELETE_TOUR_BY_ID);
			callSt.setLong(1, tour.getId());
			int rs = callSt.executeUpdate();
			if(rs > 0){
				return true;
				}
		}   finally {
			closeStatement(callSt);
			con.close();
		}	
		return false;
	}

}
