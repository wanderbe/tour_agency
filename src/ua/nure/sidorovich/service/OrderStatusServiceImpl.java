package ua.nure.sidorovich.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.sidorovich.DAO.DAOFactory;
import ua.nure.sidorovich.DAO.OrderStatusDao;
import ua.nure.sidorovich.entety.OrderStatus;
import ua.nure.sidorovich.util.DataBaseException;

public class OrderStatusServiceImpl implements OrderStatusService{
	
	private static final Logger LOGGER = Logger.getLogger(OrderStatusServiceImpl.class);
		
	private OrderStatusDao orderStatusDao = DAOFactory.getInstance().getOrderStatusDAO();
	

	@Override
	public OrderStatus getByID(long id) throws DataBaseException {
		try {
			LOGGER.info("User tried to get OrderStatus by id= " + id);
			return orderStatusDao.getByID(id);
		} catch (SQLException e) {
			LOGGER.error("In promt to get OrderStatus by id= " + id + "; was returned exception ", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	@Override
	public List<OrderStatus> getList() throws DataBaseException {
		try {
			LOGGER.info("User tried to get list of all OrderStatus");
			return orderStatusDao.getList();
		} catch (SQLException e) {
			LOGGER.error("In promt to get list of all OrderStatus was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	@Override
	public long save(OrderStatus orderStatus) throws DataBaseException {
		try {
			LOGGER.info("User tried to save OrderStatus= " + orderStatus);
			return orderStatusDao.save(orderStatus);
		} catch (SQLException e) {
			LOGGER.error("In promt to save OrderStatus= " + orderStatus + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	@Override
	public boolean update(OrderStatus orderStatus) throws DataBaseException {
		try {
			LOGGER.info("User tried to update OrderStatus= " + orderStatus);
			return orderStatusDao.update(orderStatus);
		} catch (SQLException e) {
			LOGGER.error("In promt to update OrderStatus= " + orderStatus + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	@Override
	public OrderStatus getByName(String name) throws DataBaseException {
		try {
			LOGGER.info("User tried to get OrderStatus by name= " + name);
			return orderStatusDao.getByName(name);
		} catch (SQLException e) {
			LOGGER.error("In promt to get OrderStatus by name= " + name + "; was returned exception ", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	@Override
	public boolean remove(OrderStatus orderStatus) throws DataBaseException {
		try {
			LOGGER.info("User tried to remove OrderStatus= " + orderStatus);
			return orderStatusDao.remove(orderStatus);
		} catch (SQLException e) {
			LOGGER.error("In promt to remove OrderStatus= " + orderStatus + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	

}
