package ua.nure.sidorovich.service;

import java.util.List;

import ua.nure.sidorovich.entety.OrderStatus;
import ua.nure.sidorovich.util.DataBaseException;

public interface OrderStatusService {

	String NAME_OF_ORDER_STATUS_REGISTERED = "registered";

	OrderStatus getByID(long id) throws DataBaseException;

	List<OrderStatus> getList() throws DataBaseException;

	long save(OrderStatus orderStatus) throws DataBaseException;

	boolean update(OrderStatus orderStatus) throws DataBaseException;

	OrderStatus getByName(String name) throws DataBaseException;

	boolean remove(OrderStatus orderStatus) throws DataBaseException;

}