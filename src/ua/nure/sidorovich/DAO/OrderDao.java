package ua.nure.sidorovich.DAO;

import java.sql.SQLException;
import java.util.List;

import ua.nure.sidorovich.entety.Order;
import ua.nure.sidorovich.entety.OrderStatus;
import ua.nure.sidorovich.entety.Tour;
import ua.nure.sidorovich.entety.User;

public interface OrderDao extends DAO{
		
	@Override
	Order getByID(long id) throws SQLException;
	
	@Override
    List<Order> getList() throws SQLException;
	
	List<Order> getByUser(User user) throws SQLException;
	
	List<Order> getByTour(Tour tour) throws SQLException;
	
	List<Order> getListByOrderStatus(OrderStatus orderStatus) throws SQLException;

	boolean saveList(List<Order> orderList) throws SQLException;

	List<Order> getListByOrderStatusAndUser(OrderStatus orderStatus, User user) throws SQLException;

	List<Order> getListByOrderStatusAndHoteState(long orderStatusId, boolean hotState) throws SQLException;
}
