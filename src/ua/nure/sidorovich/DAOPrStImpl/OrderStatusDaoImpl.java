package ua.nure.sidorovich.DAOPrStImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.nure.sidorovich.DAO.OrderStatusDao;
import ua.nure.sidorovich.entety.OrderStatus;
import ua.nure.sidorovich.util.ConnectionManagerImpl;

public class OrderStatusDaoImpl extends DAOSceleton implements OrderStatusDao{
	
	public OrderStatusDaoImpl(){
		super();
	}

	private static final String GET_ALL_ORDER_STATUSES = "SELECT * FROM order_status";
	
	private static final String GET_ORDER_STATUS_BY_ID 
		= "SELECT * FROM order_status WHERE id=?";

	private static final String GET_ORDER_STATUS_BY_NAME 
		= "SELECT * FROM order_status WHERE name=?";
	
	private static final String UPDATE_ORDER_STATUS 
		= "UPDATE order_status SET name =?, description =? WHERE id =?";
	
	private static final String DELETE_ORDER_STATUS_BY_ID 
		= "DELETE FROM order_status WHERE id=?";
	
	private static final String INSERT_ORDER_STATUS 
		= "INSERT INTO order_status (name, description) VALUES (?, ?)";

	private OrderStatus createOrderStatusFromResultSet(ResultSet rs) throws SQLException{
		OrderStatus orderStatus = new OrderStatus();
		int k = 0;
		orderStatus.setId(rs.getInt(++k));
		orderStatus.setName(rs.getString(++k));
		orderStatus.setDescription(rs.getString(++k));
		
		return orderStatus;
	}
	
	@Override
	public OrderStatus getByID(long id) throws SQLException{
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		PreparedStatement prepSt = null;
		
		OrderStatus orderStatus = null;
		
		try {
			prepSt = con.prepareStatement(GET_ORDER_STATUS_BY_ID);
			prepSt.setLong(1, id);
			ResultSet rs = prepSt.executeQuery();
			if(rs.next()){
				orderStatus = createOrderStatusFromResultSet(rs);
				}
		} finally {
			closeStatement(prepSt);
			con.close();
		}
		return orderStatus;
	}

	@Override
	public List<OrderStatus> getList() throws SQLException{
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		Statement st = null;
		
		List<OrderStatus> orderStatusList = new ArrayList<>();
		
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(GET_ALL_ORDER_STATUSES);
			while(rs.next()){
				OrderStatus orderStatus = createOrderStatusFromResultSet(rs);
				orderStatusList.add(orderStatus);
			}
		} finally {
			closeStatement(st);
			con.close();
		}
		return orderStatusList;
	}

	@Override
	public OrderStatus getByName(String name) throws SQLException {
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		PreparedStatement prepSt = null;
		
		OrderStatus orderStatus = null;
		
		try {
			prepSt = con.prepareStatement(GET_ORDER_STATUS_BY_NAME);
			prepSt.setString(1, name);
			ResultSet rs = prepSt.executeQuery();
			if(rs.next()){
				orderStatus = createOrderStatusFromResultSet(rs);
				}
		} finally {
			closeStatement(prepSt);
			con.close();
		}
		return orderStatus;
	}

	@Override
	public long save(Object o) throws SQLException {
		
		if(o == null || !(o instanceof OrderStatus || ((OrderStatus)o).getName() == null)){
			return -1;
		}
		
		OrderStatus orderStatus = (OrderStatus)o;
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		PreparedStatement prepSt = null;
		
		long result = -1;
		
		try {
			prepSt = con.prepareStatement(INSERT_ORDER_STATUS);
			
			int k = 0;
			prepSt.setString(++k, orderStatus.getName());
			prepSt.setString(++k, orderStatus.getDescription()); //todo
						
			if(prepSt.executeUpdate() > 0){
				result = getByName(orderStatus.getName()).getId();
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
		
		if(o == null || !(o instanceof OrderStatus) || ((OrderStatus)o).getId() == 0L 
				|| ((OrderStatus)o).getName() == null || getByID(((OrderStatus)o).getId()) == null){
			return false;
		}
		
		OrderStatus orderStatus = (OrderStatus)o;
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnectionForTransactions();
		PreparedStatement prepSt = null;
				
		try {
			prepSt = con.prepareStatement(UPDATE_ORDER_STATUS);
			
			int k = 0;
			prepSt.setString(++k, orderStatus.getName());
			prepSt.setString(++k, orderStatus.getDescription());
			prepSt.setLong(++k, orderStatus.getId());
						
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
		
		if(o == null || !(o instanceof OrderStatus) || ((OrderStatus)o).getId() == 0L 
				|| ((OrderStatus)o).getName() == null || getByID(((OrderStatus)o).getId()) == null){
			return false;
		}
		
		OrderStatus orderStatus = (OrderStatus)o;
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		PreparedStatement prepSt = null;
		
		try {
			prepSt = con.prepareStatement(DELETE_ORDER_STATUS_BY_ID);
			prepSt.setLong(1, orderStatus.getId());
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
