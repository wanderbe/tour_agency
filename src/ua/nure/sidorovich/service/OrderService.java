package ua.nure.sidorovich.service;

import java.util.List;

import ua.nure.sidorovich.entety.Order;
import ua.nure.sidorovich.entety.OrderStatus;
import ua.nure.sidorovich.entety.Tour;
import ua.nure.sidorovich.entety.User;
import ua.nure.sidorovich.util.DataBaseException;

public interface OrderService {

	String PAID_STATUS = "paid";

	long save(Order order) throws DataBaseException;

	Order getByID(long id) throws DataBaseException;

	boolean update(Order order) throws DataBaseException;

	List<Order> getList() throws DataBaseException;

	boolean remove(Order order) throws DataBaseException;

	List<Order> getByUser(User user) throws DataBaseException;

	List<Order> getByTour(Tour tour) throws DataBaseException;

	List<Order> getListByOrderStatus(OrderStatus orderStatus) throws DataBaseException;

	boolean saveList(List<Order> orderList) throws DataBaseException;

	List<Order> getListByOrderStatusAndUser(OrderStatus orderStatus, User user) throws DataBaseException;

	List<Order> getListOrdersByUserAndStatus(String paidStatus, User user) throws DataBaseException;

	boolean changeOrderStatus(Order order, OrderStatus orderStatus) throws DataBaseException;

	boolean changeToPaid(Order order) throws DataBaseException;

	boolean changeToRegistered(Order order) throws DataBaseException;

	boolean changeToCanceled(Order order) throws DataBaseException;

	List<Order> getListByOrderStatusAndHoteState(Long orderStatusId, boolean b) throws DataBaseException;

}