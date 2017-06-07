package ua.nure.sidorovich.DAOPrStImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.nure.sidorovich.DAO.OrderDao;
import ua.nure.sidorovich.entety.Hotel;
import ua.nure.sidorovich.entety.HotelType;
import ua.nure.sidorovich.entety.Order;
import ua.nure.sidorovich.entety.OrderStatus;
import ua.nure.sidorovich.entety.RestType;
import ua.nure.sidorovich.entety.Tour;
import ua.nure.sidorovich.entety.User;
import ua.nure.sidorovich.entety.UserRole;
import ua.nure.sidorovich.util.ConnectionManagerImpl;

public class OrderDaoImpl extends DAOSceleton implements OrderDao{
	
	public OrderDaoImpl(){
		super();
	}

	private static final String GET_ALL_ORDERS 
		= "SELECT orders.id, orders.register_time, orders.cost, users.id, users.name, users.birthday, "
				+ "users.email, users.phone, users.login, users.password, user_role.id, user_role.name, "
				+ "user_role.description, tours.id, tours.name, tours.description, tours.places, tours.price, "
				+ "tours.is_hot, rest_type.id, rest_type.name, rest_type.description, hotels.id, hotels.name, "
				+ "hotels.description, hotel_types.id, hotel_types.class, hotel_types.description, "
				+ "order_status.id, order_status.name, order_status.description "
				+ "FROM orders LEFT JOIN users ON orders.id_user=users.id "
				+ "LEFT JOIN user_role ON users.id_role=user_role.id "
				+ "LEFT JOIN tours ON orders.id_tour=tours.id "
				+ "LEFT JOIN rest_type ON tours.id_rest_type=rest_type.id "
				+ "LEFT JOIN hotels ON tours.id_hotel=hotels.id "
				+ "LEFT JOIN hotel_types ON hotels.hotel_type_id=hotel_types.id "
				+ "LEFT JOIN order_status ON orders.id_status=order_status.id";
	
	private static final String GET_BY_ORDER_STATUS_ID_AND_HOT_STATE = GET_ALL_ORDERS + " WHERE (orders.id_status=?) AND (tours.is_hot=?)";
	
	private static final String GET_ORDERS_BY_USER = GET_ALL_ORDERS + " WHERE orders.id_user=?";

	private static final String GET_BY_TOUR = GET_ALL_ORDERS + " WHERE orders.id_tour=?";;

	private static final String GET_BY_ORDER_STATUS = GET_ALL_ORDERS + " WHERE orders.id_status=?";
	
	private static final String GET_BY_ORDER_STATUS_AND_USER = GET_ALL_ORDERS + " WHERE (orders.id_status=?) AND (orders.id_user=?)";
	
	private static final String GET_ORDER_BY_ID = GET_ALL_ORDERS + " WHERE orders.id=?";
	
	private static final String UPDATE_ORDER
		= "UPDATE orders SET register_time =?, id_user =?, id_tour =?, cost =?, id_status =? WHERE id =?";
	
	private static final String DELETE_ORDER_BY_ID = "DELETE FROM orders WHERE id=?";
	
	private static final String INSERT_ORDER 
		= "INSERT INTO orders (register_time, id_user, id_tour, cost, id_status) VALUES (?, ?, ?, ?, ?)";
	
	private Order createOrderFromResultSet(ResultSet rs) throws SQLException {
		Order order = new Order();
		User user = new User();
		UserRole userRole = new UserRole();
		Tour tour = new Tour();
		RestType restType = new RestType();
		Hotel hotel = new Hotel();
		HotelType hotelType = new HotelType();
		OrderStatus orderStatus = new OrderStatus();
		
		int k = 0;
		
		order.setId(rs.getLong(++k));
		order.setRegisterTime(rs.getDate(++k));
		order.setCost(rs.getDouble(++k));
		
		user.setId(rs.getLong(++k));
		user.setName(rs.getString(++k));
		user.setBirthday(rs.getDate(++k));
		user.setEmail(rs.getString(++k));
		user.setPhone(rs.getString(++k));
		user.setLogin(rs.getString(++k));
		user.setPassword(rs.getString(++k));
		
		userRole.setId(rs.getLong(++k));
		userRole.setName(rs.getString(++k));
		userRole.setDescription(rs.getString(++k));
		
		user.setRole(userRole);
		order.setUser(user);
		
		tour.setId(rs.getLong(++k));
		tour.setName(rs.getString(++k));
		tour.setDescription(rs.getString(++k));
		tour.setPlaces(rs.getInt(++k));
		tour.setPrice(rs.getDouble(++k));
		tour.setItHot(rs.getBoolean(++k));
		
		restType.setId(rs.getLong(++k));
		restType.setName(rs.getString(++k));
		restType.setDescription(rs.getString(++k));
		
		tour.setRestType(restType);
		
		hotel.setId(rs.getLong(++k));
		hotel.setName(rs.getString(++k));
		hotel.setDescription(rs.getString(++k));
		
		hotelType.setId(rs.getLong(++k));
		hotelType.setHotelClass(rs.getInt(++k));
		hotelType.setDescription(rs.getString(++k));
		
		hotel.setHotelType(hotelType);
		tour.setHotel(hotel);
		order.setTour(tour);
		
		orderStatus.setId(rs.getLong(++k));
		orderStatus.setName(rs.getString(++k));
		orderStatus.setDescription(rs.getString(++k));
		
		order.setOrderStatus(orderStatus);
		
		return order;
	}
	
	@Override
	public List<Order> getListByOrderStatusAndHoteState(long orderStatusId, boolean hotState) throws SQLException {

		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		PreparedStatement prepSt = null;
		
		List<Order> orders = new ArrayList<>();
		
		try {
			prepSt = con.prepareStatement(GET_BY_ORDER_STATUS_ID_AND_HOT_STATE);
			
			int k = 0;
			prepSt.setLong(++k, orderStatusId);
			prepSt.setBoolean(++k, hotState);
			ResultSet rs = prepSt.executeQuery();
			
			while(rs.next()){
				Order order = createOrderFromResultSet(rs);
				orders.add(order);
			}
		} finally {
			closeStatement(prepSt);
			con.close();
		}	
		return orders;
	}
	
	@Override
	public List<Order> getList() throws SQLException{
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();		
		Statement st = null;
		
		List<Order> orders = new ArrayList<>();
		
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(GET_ALL_ORDERS);
			
			while(rs.next()){
				Order order = createOrderFromResultSet(rs);
				orders.add(order);
			}
		} finally {
			closeStatement(st);
			con.close();
		}	
		return orders;
	}
	
	@Override
	public List<Order> getByUser(User user) throws SQLException {

		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();	
		PreparedStatement prepSt = null;
		
		List<Order> orders = new ArrayList<>();
		
		try {
			prepSt = con.prepareStatement(GET_ORDERS_BY_USER);
			
			int k = 0;
			prepSt.setLong(++k, user.getId());
			ResultSet rs = prepSt.executeQuery();
			
			while(rs.next()){
				Order order = createOrderFromResultSet(rs);
				orders.add(order);
			}
		} finally {
			closeStatement(prepSt);
			con.close();
		}	
		return orders;
	}

	@Override
	public List<Order> getByTour(Tour tour) throws SQLException {

		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();	
		PreparedStatement prepSt = null;
		
		List<Order> orders = new ArrayList<>();
		
		try {
			prepSt = con.prepareStatement(GET_BY_TOUR);
			
			int k = 0;
			prepSt.setLong(++k, tour.getId());
			ResultSet rs = prepSt.executeQuery();
			
			while(rs.next()){
				Order order = createOrderFromResultSet(rs);
				orders.add(order);
			}
		} finally {
			closeStatement(prepSt);
			con.close();
		}	
		return orders;
	}

	@Override
	public List<Order> getListByOrderStatus(OrderStatus orderStatus) throws SQLException {

		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();	
		PreparedStatement prepSt = null;
		
		List<Order> orders = new ArrayList<>();
		
		try {
			prepSt = con.prepareStatement(GET_BY_ORDER_STATUS);
			
			int k = 0;
			prepSt.setLong(++k, orderStatus.getId());
			ResultSet rs = prepSt.executeQuery();
			
			while(rs.next()){
				Order order = createOrderFromResultSet(rs);
				orders.add(order);
			}
		} finally {
			closeStatement(prepSt);
			con.close();
		}	
		return orders;
	}
	
	@Override
	public List<Order> getListByOrderStatusAndUser(OrderStatus orderStatus, User user) throws SQLException {

		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		PreparedStatement prepSt = null;
		
		List<Order> orders = new ArrayList<>();
		
		try {
			prepSt = con.prepareStatement(GET_BY_ORDER_STATUS_AND_USER);
			
			int k = 0;
			prepSt.setLong(++k, orderStatus.getId());
			prepSt.setLong(++k, user.getId());
			ResultSet rs = prepSt.executeQuery();
			
			while(rs.next()){
				Order order = createOrderFromResultSet(rs);
				orders.add(order);
			}
		} finally {
			closeStatement(prepSt);
			con.close();
		}	
		return orders;
	}

	
	@Override
	public long save(Object o) throws SQLException {
		
		if(o == null || !(o instanceof Order)  || ((Order)o).getUser() == null){
			return -1;
		}
		
		Order order = (Order)o;
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		PreparedStatement prepSt = null;
		
		
		long result = -1;
		
		try {
			prepSt = con.prepareStatement(INSERT_ORDER);
			
			int k = 0;
			prepSt.setDate(++k, order.getRegisterTime());
			prepSt.setLong(++k, order.getUser().getId());
			prepSt.setLong(++k, order.getTour().getId());
			prepSt.setDouble(++k, order.getCost());
			prepSt.setLong(++k, order.getOrderStatus().getId());
			
			result = prepSt.executeUpdate();
		} finally {
			closeStatement(prepSt);
			con.close();
		}	
		return result;
	}

	@Override
	public boolean saveList(List<Order> orderList) throws SQLException {
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnectionForTransactions();
		PreparedStatement prepSt = null;
				
		try {
			for(Order order : orderList){
			
				prepSt = con.prepareStatement(INSERT_ORDER);
				
				int k = 0;
				prepSt.setDate(++k, order.getRegisterTime());
				prepSt.setLong(++k, order.getUser().getId());
				prepSt.setLong(++k, order.getTour().getId());
				prepSt.setDouble(++k, order.getCost());
				prepSt.setLong(++k, order.getOrderStatus().getId());
				
				prepSt.executeUpdate();		
			}				
			con.commit();
			return true;
		} catch (SQLException e) {
				con.rollback();
				return false;
		} finally {
			closeStatement(prepSt);
			con.close();
		}
	}
	
	@Override
	public boolean update(Object o) throws SQLException{
		
		boolean result = false;
		
		if(o == null || !(o instanceof Order) || ((Order)o).getTour() == null || 
				((Order)o).getUser() == null || ((Order)o).getOrderStatus() == null){
			return false;
		}
		
		Order order = (Order)o;
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		PreparedStatement prepSt = null;
		
		try {
			prepSt = con.prepareStatement(UPDATE_ORDER);
			
			int k = 0;
			prepSt.setDate(++k, order.getRegisterTime());
			prepSt.setLong(++k, order.getUser().getId());
			prepSt.setLong(++k, order.getTour().getId());
			prepSt.setDouble(++k, order.getCost());
			prepSt.setLong(++k, order.getOrderStatus().getId());
			
			prepSt.setLong(++k, order.getId());
			
			result = prepSt.executeUpdate() > 0;			
		} finally {
			closeStatement(prepSt);
			con.close();
		}
		return result;
	}

	@Override
	public boolean remove(Object o) throws SQLException{
		
		if(o == null || !(o instanceof Order) || ((Order)o).getId() == 0L || 
				((Order)o).getUser() == null || getByID(((Order)o).getId()) == null){
			return false;
		}
		
		Order order = (Order)o;
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		PreparedStatement prepSt = null;
		
		try {
			prepSt = con.prepareStatement(DELETE_ORDER_BY_ID);
			prepSt.setLong(1, order.getId());
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
	public Order getByID(long id) throws SQLException{
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		PreparedStatement prepSt = null;
		
		Order order = null;
		
		try {
			
			prepSt = con.prepareStatement(GET_ORDER_BY_ID);
			prepSt.setLong(1, id);
			ResultSet rs = prepSt.executeQuery();
			
			if(rs.next()){
				order = createOrderFromResultSet(rs);
			}
		} finally {
			closeStatement(prepSt);
			con.close();
		}
		return order;
	}

	@Override
	public Object getByName(String name) throws SQLException {
		throw new UnsupportedOperationException();
	}

}
