package ua.nure.sidorovich.DAO;

import java.sql.SQLException;
import java.util.List;

import ua.nure.sidorovich.entety.OrderStatus;

public interface OrderStatusDao extends DAO{
	
	@Override
	OrderStatus getByID(long id) throws SQLException;
	
	@Override
    List<OrderStatus> getList() throws SQLException;
	
	@Override
	OrderStatus getByName(String name) throws SQLException;
}
