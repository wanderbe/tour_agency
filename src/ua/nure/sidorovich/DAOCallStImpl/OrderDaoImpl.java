package ua.nure.sidorovich.DAOCallStImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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

	private static final String GET_ALL_ORDERS = "{call GET_ALL_ORDERS}";
	
	private static final String GET_ORDERS_BY_USER_ID = "{call GET_ORDERS_BY_USER_ID(?)}";

	private static final String GET_ORDERS_BY_TOUR_ID = "{call GET_ORDERS_BY_TOUR_ID(?)}";

	private static final String GET_ORDER_BY_ORDER_STATUS_ID = "{call GET_ORDER_BY_ORDER_STATUS_ID(?)}";
	
	private static final String GET_ORDERS_BY_ORDER_STATUS_ID_AND_USER_ID 
		= "{call GET_ORDERS_BY_ORDER_STATUS_ID_AND_USER_ID(?, ?)}";
	
	private static final String GET_ORDER_BY_ID = "{call GET_ORDER_BY_ID(?)}";
	
	private static final String UPDATE_ORDER = "{call UPDATE_ORDER(?, ?, ?, ?, ?, ?)}";
	
	private static final String DELETE_ORDER_BY_ID = "{call DELETE_ORDER_BY_ID(?)}";
	
	private static final String INSERT_ORDER = "{call INSERT_ORDER(?, ?, ?, ?, ?)}";
	
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
	public List<Order> getList() throws SQLException{
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();		
		CallableStatement callSt = null;
		
		List<Order> orders = new ArrayList<>();
		
		try {
			callSt = con.prepareCall(GET_ALL_ORDERS);
			ResultSet rs = callSt.executeQuery();
			
			while(rs.next()){
				Order order = createOrderFromResultSet(rs);
				orders.add(order);
			}
		} finally {
			closeStatement(callSt);
			con.close();
		}	
		return orders;
	}
	
	@Override
	public List<Order> getByUser(User user) throws SQLException {

		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();	
		CallableStatement callSt = null;
		
		List<Order> orders = new ArrayList<>();
		
		try {
			callSt = con.prepareCall(GET_ORDERS_BY_USER_ID);
			
			int k = 0;
			callSt.setLong(++k, user.getId());
			ResultSet rs = callSt.executeQuery();
			
			while(rs.next()){
				Order order = createOrderFromResultSet(rs);
				orders.add(order);
			}
		} finally {
			closeStatement(callSt);
			con.close();
		}	
		return orders;
	}

	@Override
	public List<Order> getByTour(Tour tour) throws SQLException {

		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();	
		CallableStatement callSt = null;
		
		List<Order> orders = new ArrayList<>();
		
		try {
			callSt = con.prepareCall(GET_ORDERS_BY_TOUR_ID);
			
			int k = 0;
			callSt.setLong(++k, tour.getId());
			ResultSet rs = callSt.executeQuery();
			
			while(rs.next()){
				Order order = createOrderFromResultSet(rs);
				orders.add(order);
			}
		} finally {
			closeStatement(callSt);
			con.close();
		}	
		return orders;
	}

	@Override
	public List<Order> getListByOrderStatus(OrderStatus orderStatus) throws SQLException {

		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();	
		CallableStatement callSt = null;
		
		List<Order> orders = new ArrayList<>();
		
		try {
			callSt = con.prepareCall(GET_ORDER_BY_ORDER_STATUS_ID);
			
			int k = 0;
			callSt.setLong(++k, orderStatus.getId());
			ResultSet rs = callSt.executeQuery();
			
			while(rs.next()){
				Order order = createOrderFromResultSet(rs);
				orders.add(order);
			}
		} finally {
			closeStatement(callSt);
			con.close();
		}	
		return orders;
	}
	
	@Override
	public List<Order> getListByOrderStatusAndUser(OrderStatus orderStatus, User user) throws SQLException {

		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
		
		List<Order> orders = new ArrayList<>();
		
		try {
			callSt = con.prepareCall(GET_ORDERS_BY_ORDER_STATUS_ID_AND_USER_ID);
			
			int k = 0;
			callSt.setLong(++k, orderStatus.getId());
			callSt.setLong(++k, user.getId());
			ResultSet rs = callSt.executeQuery();
			
			while(rs.next()){
				Order order = createOrderFromResultSet(rs);
				orders.add(order);
			}
		} finally {
			closeStatement(callSt);
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
		CallableStatement callSt = null;
		
		
		long result = -1;
		
		try {
			callSt = con.prepareCall(INSERT_ORDER);
			
			int k = 0;
			callSt.setDate(++k, order.getRegisterTime());
			callSt.setLong(++k, order.getUser().getId());
			callSt.setLong(++k, order.getTour().getId());
			callSt.setDouble(++k, order.getCost());
			callSt.setLong(++k, order.getOrderStatus().getId());
			
			result = callSt.executeUpdate();
		} finally {
			closeStatement(callSt);
			con.close();
		}	
		return result;
	}

	@Override
	public boolean saveList(List<Order> orderList) throws SQLException {
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnectionForTransactions();
		CallableStatement callSt = null;
				
		try {
			for(Order order : orderList){
			
				callSt = con.prepareCall(INSERT_ORDER);
				
				int k = 0;
				callSt.setDate(++k, order.getRegisterTime());
				callSt.setLong(++k, order.getUser().getId());
				callSt.setLong(++k, order.getTour().getId());
				callSt.setDouble(++k, order.getCost());
				callSt.setLong(++k, order.getOrderStatus().getId());
				
				callSt.executeUpdate();		
			}				
			con.commit();
			return true;
		} catch (SQLException e) {
				con.rollback();
				return false;
		} finally {
			closeStatement(callSt);
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
		CallableStatement callSt = null;
		
		try {
			callSt = con.prepareCall(UPDATE_ORDER);
			
			int k = 0;
			callSt.setDate(++k, order.getRegisterTime());
			callSt.setLong(++k, order.getUser().getId());
			callSt.setLong(++k, order.getTour().getId());
			callSt.setDouble(++k, order.getCost());
			callSt.setLong(++k, order.getOrderStatus().getId());
			
			callSt.setLong(++k, order.getId());
			
			result = callSt.executeUpdate() > 0;			
		} finally {
			closeStatement(callSt);
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
		CallableStatement callSt = null;
		
		try {
			callSt = con.prepareCall(DELETE_ORDER_BY_ID);
			callSt.setLong(1, order.getId());
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
	public Order getByID(long id) throws SQLException{
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
		
		Order order = null;
		
		try {
			
			callSt = con.prepareCall(GET_ORDER_BY_ID);
			callSt.setLong(1, id);
			ResultSet rs = callSt.executeQuery();
			
			if(rs.next()){
				order = createOrderFromResultSet(rs);
			}
		} finally {
			closeStatement(callSt);
			con.close();
		}
		return order;
	}

	@Override
	public Object getByName(String name) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Order> getListByOrderStatusAndHoteState(long orderStatus, boolean hotState) throws SQLException {
		throw new UnsupportedOperationException();
	}

}
