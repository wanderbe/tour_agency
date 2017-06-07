package ua.nure.sidorovich.DAOPrStImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

	private static final String GET_ALL_TOURS
		= 	"SELECT tours.id, tours.name, tours.description, tours.places, tours.price, tours.is_hot, "
			+ "rest_type.id, rest_type.name, rest_type.description, hotels.id, hotels.name, hotels.description, "
			+ "hotel_types.id, hotel_types.class, hotel_types.description FROM "
			+ "tours LEFT JOIN rest_type ON tours.id_rest_type=rest_type.id "
			+ "LEFT JOIN hotels ON tours.id_hotel=hotels.id "
			+ "LEFT JOIN hotel_types ON hotels.hotel_type_id=hotel_types.id ";
	
	private static final String GET_TOUR_BY_ID = GET_ALL_TOURS + " WHERE tours.id=?";

	private static final String GET_TOUR_BY_NAME = GET_ALL_TOURS + " WHERE tours.name=?";
	
	private static final String GET_TOURS_BY_PARAMETRS = GET_ALL_TOURS 
			+ " WHERE (rest_type.id BETWEEN ? AND ?) AND (tours.price BETWEEN ? AND ?) "
			+ "AND (tours.places BETWEEN ? AND ?) AND (hotel_types.id BETWEEN ? AND ?)";
	
	private static final String UPDATE_TOUR
		= "UPDATE tours SET name =?, description =?, id_rest_type=?, "
				+ "places=?, id_hotel=?, price=?, is_hot=? WHERE id =?";
	
	private static final String DELETE_TOUR_BY_ID = "DELETE FROM tours WHERE id=?";
	
	private static final String INSERT_TOUR 
		= "INSERT INTO tours (name, description, id_rest_type, "
				+ "places, id_hotel, price, is_hot) VALUES (?, ?, ?, ?, ?, ?, ?)";


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
		PreparedStatement prepSt = null;
		
		try {
			prepSt = con.prepareStatement(GET_TOUR_BY_ID);
			prepSt.setLong(1, l);
			ResultSet rs = prepSt.executeQuery();
			if(rs.next()){
				tour = createTourFromResultSet(rs);
				}
		} finally {
			closeStatement(prepSt);
			con.close();
		}
		return tour;
	}

	@Override
	public List<Tour> getList() throws SQLException{
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		
		List<Tour> tours = new ArrayList<>();
		Statement st = null;
		
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(GET_ALL_TOURS);
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
			closeStatement(st);
			con.close();
		}
		return tours;
	}

	@Override
	public Tour getByName(String name) throws SQLException{
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		
		Tour tour = null;
		PreparedStatement prepSt = null;
		
		try {
			prepSt = con.prepareStatement(GET_TOUR_BY_NAME);
			prepSt.setString(1, name);
			ResultSet rs = prepSt.executeQuery();
			if(rs.next()){
				tour = createTourFromResultSet(rs);
				}
		} finally {
			closeStatement(prepSt);
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
		PreparedStatement prepSt = null;
		
		try {
			prepSt = con.prepareStatement(INSERT_TOUR);
			
			int k = 0;
			prepSt.setString(++k, tour.getName());
			prepSt.setString(++k, tour.getDescription());
			prepSt.setLong(++k, tour.getRestType().getId());
			prepSt.setInt(++k, tour.getPlaces());
			prepSt.setLong(++k, tour.getHotel().getId());
			prepSt.setDouble(++k, tour.getPrice());
			prepSt.setBoolean(++k, tour.isItHot());
			
			if(prepSt.executeUpdate() > 0){
				result = getByName(tour.getName()).getId();
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
		
		if(o == null || !(o instanceof Tour) || ((Tour)o).getId() == 0L || 
				((Tour)o).getName() == null || getByID(((Tour)o).getId()) == null){
			return false;
		}
		
		Tour tour = (Tour)o;
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnectionForTransactions();
		PreparedStatement prepSt = null;
				
		try {
			prepSt = con.prepareStatement(UPDATE_TOUR);
			
			int k = 0;
			prepSt.setString(++k, tour.getName());
			prepSt.setString(++k, tour.getDescription());
			prepSt.setLong(++k, tour.getRestType().getId());
			prepSt.setInt(++k, tour.getPlaces());
			prepSt.setLong(++k, tour.getHotel().getId());
			prepSt.setDouble(++k, tour.getPrice());
			prepSt.setBoolean(++k, tour.isItHot());
			prepSt.setLong(++k, tour.getId());
						
			result = prepSt.executeUpdate() > 0;
			
			con.commit();
						
		} catch (SQLException e) {
			result = false;
			con.rollback();
		}  finally {
			closeStatement(prepSt);
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
		PreparedStatement prepSt = null;
		
		try {
			prepSt = con.prepareStatement(DELETE_TOUR_BY_ID);
			prepSt.setLong(1, tour.getId());
			int rs = prepSt.executeUpdate();
			if(rs > 0){
				return true;
				}
		}   finally {
			closeStatement(prepSt);
			con.close();
		}	
		return false;
	}

}
