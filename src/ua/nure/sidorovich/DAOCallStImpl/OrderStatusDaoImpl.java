package ua.nure.sidorovich.DAOCallStImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.nure.sidorovich.DAO.OrderStatusDao;
import ua.nure.sidorovich.entety.OrderStatus;
import ua.nure.sidorovich.util.ConnectionManagerImpl;

public class OrderStatusDaoImpl extends DAOSceleton implements OrderStatusDao{
	
	public OrderStatusDaoImpl(){
		super();
	}

	private static final String GET_ALL_ORDER_STATUSES = "{call GET_ALL_ORDER_STATUSES}";
	
	private static final String GET_ORDER_STATUS_BY_ID = "{call GET_ORDER_STATUS_BY_ID(?)}";

	private static final String GET_ORDER_STATUS_BY_NAME = "{call GET_ORDER_STATUS_BY_NAME(?)}";
	
	private static final String UPDATE_ORDER_STATUS = "{call UPDATE_ORDER_STATUS(?, ?, ?)}";
	
	private static final String DELETE_ORDER_STATUS_BY_ID = "{call DELETE_ORDER_STATUS_BY_ID(?)}";
	
	private static final String INSERT_ORDER_STATUS = "{call INSERT_ORDER_STATUS(?, ?)}";

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
		CallableStatement callSt = null;
		
		OrderStatus orderStatus = null;
		
		try {
			callSt = con.prepareCall(GET_ORDER_STATUS_BY_ID);
			callSt.setLong(1, id);
			ResultSet rs = callSt.executeQuery();
			if(rs.next()){
				orderStatus = createOrderStatusFromResultSet(rs);
				}
		} finally {
			closeStatement(callSt);
			con.close();
		}
		return orderStatus;
	}

	@Override
	public List<OrderStatus> getList() throws SQLException{
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
		
		List<OrderStatus> orderStatusList = new ArrayList<>();
		
		try {
			callSt = con.prepareCall(GET_ALL_ORDER_STATUSES);
			ResultSet rs = callSt.executeQuery();
			while(rs.next()){
				OrderStatus orderStatus = createOrderStatusFromResultSet(rs);
				orderStatusList.add(orderStatus);
			}
		} finally {
			closeStatement(callSt);
			con.close();
		}
		return orderStatusList;
	}

	@Override
	public OrderStatus getByName(String name) throws SQLException {
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
		
		OrderStatus orderStatus = null;
		
		try {
			callSt = con.prepareCall(GET_ORDER_STATUS_BY_NAME);
			callSt.setString(1, name);
			ResultSet rs = callSt.executeQuery();
			if(rs.next()){
				orderStatus = createOrderStatusFromResultSet(rs);
				}
		} finally {
			closeStatement(callSt);
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
		CallableStatement callSt = null;
		
		long result = -1;
		
		try {
			callSt = con.prepareCall(INSERT_ORDER_STATUS);
			
			int k = 0;
			callSt.setString(++k, orderStatus.getName());
			callSt.setString(++k, orderStatus.getDescription()); //todo
						
			if(callSt.executeUpdate() > 0){
				result = getByName(orderStatus.getName()).getId();
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
		
		if(o == null || !(o instanceof OrderStatus) || ((OrderStatus)o).getId() == 0L 
				|| ((OrderStatus)o).getName() == null || getByID(((OrderStatus)o).getId()) == null){
			return false;
		}
		
		OrderStatus orderStatus = (OrderStatus)o;
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnectionForTransactions();
		CallableStatement callSt = null;
				
		try {
			callSt = con.prepareCall(UPDATE_ORDER_STATUS);
			
			int k = 0;
			callSt.setString(++k, orderStatus.getName());
			callSt.setString(++k, orderStatus.getDescription());
			callSt.setLong(++k, orderStatus.getId());
						
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
		
		if(o == null || !(o instanceof OrderStatus) || ((OrderStatus)o).getId() == 0L 
				|| ((OrderStatus)o).getName() == null || getByID(((OrderStatus)o).getId()) == null){
			return false;
		}
		
		OrderStatus orderStatus = (OrderStatus)o;
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
		
		try {
			callSt = con.prepareCall(DELETE_ORDER_STATUS_BY_ID);
			callSt.setLong(1, orderStatus.getId());
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
