package ua.nure.sidorovich.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.sidorovich.DAO.DAOFactory;
import ua.nure.sidorovich.DAO.OrderDao;
import ua.nure.sidorovich.entety.Discount;
import ua.nure.sidorovich.entety.Order;
import ua.nure.sidorovich.entety.OrderStatus;
import ua.nure.sidorovich.entety.Tour;
import ua.nure.sidorovich.entety.User;
import ua.nure.sidorovich.util.DataBaseException;

public class OrderServiceImpl implements OrderService {
	
	private static final Logger LOGGER = Logger.getLogger(OrderServiceImpl.class);
	
	private OrderDao orderDao = DAOFactory.getInstance().getOrderDAO();
	
	private OrderStatusService orderStatusService = new OrderStatusServiceImpl();
	
	private static final String REGISTERED = "registered";
	
	private static final String PAID = "paid";
	
	private static final String CANCELED = "canceled";
	
	@Override
	public boolean changeOrderStatus(Order order, OrderStatus orderStatus) throws DataBaseException {
		if(REGISTERED.equals(orderStatus.getName())){
			return changeToRegistered(order);
		} else if (PAID.equals(orderStatus.getName())){
			return changeToPaid(order);
		} else if (CANCELED.equals(orderStatus.getName())){
			return changeToCanceled(order);
		} else {
			return false;
		}
		
	}
	
	@Override
	public boolean changeToPaid(Order order) throws DataBaseException {
				
		OrderStatusService orderStatusService = new OrderStatusServiceImpl();
		TourService tourService = new TourServiceImpl();
		DiscountService discountService = new DiscountServiceImpl();
		
		double price = tourService.getByID(order.getTour().getId()).getPrice();
		Discount currentDiscount = discountService.getLatestDiscont();
		int paidOrders = getListByOrderStatusAndUser(orderStatusService.getByName(PAID), order.getUser()).size();
		
		double cost;
		if((paidOrders * currentDiscount.getStep()) > currentDiscount.getMaxPercent()){
			cost = price * (1. - (double)currentDiscount.getMaxPercent() / 100.);
		} else {
			cost = price * (1. - (double)paidOrders * currentDiscount.getStep() / 100.);
		}
		
		order.setCost(cost);
		order.setOrderStatus(orderStatusService.getByName(PAID));
				
		return update(order);
	}
	
	@Override
	public boolean changeToRegistered(Order order) throws DataBaseException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean changeToCanceled(Order order) throws DataBaseException {
		OrderStatusService orderStatusService = new OrderStatusServiceImpl();
		
		try {
			order.setOrderStatus(orderStatusService.getByName(CANCELED));
			
			return update(order);
		} catch (DataBaseException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
		return false;
	}
	
	@Override
	public List<Order> getListOrdersByUserAndStatus(String paidStatus, User user) throws DataBaseException {
			return getListByOrderStatusAndUser(orderStatusService.getByName(paidStatus), user);
	}	

	
	@Override
	public List<Order> getListByOrderStatusAndUser(OrderStatus orderStatus, User user) throws DataBaseException {
		try {
			LOGGER.info("User tried to get Order by OrderStatus= " + orderStatus + "; and User= " + user);
			return orderDao.getListByOrderStatusAndUser(orderStatus, user);
		} catch (SQLException e) {
			LOGGER.error("In promt to get Order by OrderStatus= " + orderStatus + "; and User= " + user 
					+ "; was returned exception ", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}
	
	
	@Override
	public List<Order> getListByOrderStatus(OrderStatus orderStatus) throws DataBaseException {
		try {
			LOGGER.info("User tried to get list of Order by OrderStatus= " + orderStatus);
			return orderDao.getListByOrderStatus(orderStatus);
		} catch (SQLException e) {
			LOGGER.error("In promt to get Order by OrderStatus= " + orderStatus + "; was returned exception ", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	@Override
	public long save(Order order) throws DataBaseException {
		try {
			LOGGER.info("User tried to save Order= " + order);
			return orderDao.save(order);
		} catch (SQLException e) {
			LOGGER.error("In promt to save Order= " + order + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	@Override
	public Order getByID(long id) throws DataBaseException {
		try {
			LOGGER.info("User tried to get Order by id= " + id);
			return orderDao.getByID(id);
		} catch (SQLException e) {
			LOGGER.error("In promt to get Order by id= " + id + "; was returned exception ", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}


	@Override
	public boolean update(Order order) throws DataBaseException {
		try {
			LOGGER.info("User tried to update Order= " + order);
			return orderDao.update(order);
		} catch (SQLException e) {
			LOGGER.error("In promt to update Order= " + order + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}


	@Override
	public List<Order> getList() throws DataBaseException {
		try {
			LOGGER.info("User tried to get list of all Order");
			return orderDao.getList();
		} catch (SQLException e) {
			LOGGER.error("In promt to get list of all Order was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}


	@Override
	public boolean remove(Order order) throws DataBaseException {
		try {
			LOGGER.info("User tried to remove Order= " + order);
			return orderDao.remove(order);
		} catch (SQLException e) {
			LOGGER.error("In promt to remove Order= " + order + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}


	@Override
	public List<Order> getByUser(User user) throws DataBaseException {
		try {
			LOGGER.info("User tried to get list of Order where User= " + user);
			return orderDao.getByUser(user);
		} catch (SQLException e) {
			LOGGER.error("In promt to get list of Order where User= " + user + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}


	@Override
	public List<Order> getByTour(Tour tour) throws DataBaseException {
		try {
			LOGGER.info("User tried to get list of Order where Tour= " + tour);
			return orderDao.getByTour(tour);
		} catch (SQLException e) {
			LOGGER.error("In promt to get list of Order where Tour= " + tour + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}


	@Override
	public boolean saveList(List<Order> orderList) throws DataBaseException {
		try {
			LOGGER.info("User tried to save list of Order= " + orderList);
			return orderDao.saveList(orderList);
		} catch (SQLException e) {
			LOGGER.error("In promt to save list of Order= " + orderList + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	@Override
	public List<Order> getListByOrderStatusAndHoteState(Long orderStatusId, boolean b) throws DataBaseException {
		try {
			LOGGER.info("User tried to get list of Order where Tour= " + orderStatusId + orderStatusId);
			return orderDao.getListByOrderStatusAndHoteState(orderStatusId, b);
		} catch (SQLException e) {
			LOGGER.error("In promt to get list of Order where Tour= " + + orderStatusId + orderStatusId + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

}
