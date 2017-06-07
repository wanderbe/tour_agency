package ua.nure.sidorovich.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.sidorovich.DAO.DAOFactory;
import ua.nure.sidorovich.DAO.DiscountDao;
import ua.nure.sidorovich.entety.Discount;
import ua.nure.sidorovich.util.DataBaseException;

public class DiscountServiceImpl implements DiscountService{
	
	private static final Logger LOGGER = Logger.getLogger(DiscountServiceImpl.class);
	
	private DiscountDao discountDao = DAOFactory.getInstance().getDiscountDAO();

	
	@Override
	public Discount getByID(long id) throws DataBaseException {
		try {
			LOGGER.info("User tried to get Discount by id= " + id);
			return discountDao.getByID(id);
		} catch (SQLException e) {
			LOGGER.error("In promt to get BlockedUser by id= " + id + "; was returned exception ", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	@Override
	public long save(Discount discount) throws DataBaseException {
		try {
			LOGGER.info("User tried to save Discount= " + discount);
			return discountDao.save(discount);
		} catch (SQLException e) {
			LOGGER.error("In promt to save Discount= " + discount + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	@Override
	public List<Discount> getList() throws DataBaseException {
		try {
			LOGGER.info("User tried to get list of all Discount");
			return discountDao.getList();
		} catch (SQLException e) {
			LOGGER.error("In promt to get list of all Discount was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	@Override
	public List<Discount> getByRegisterTime(Timestamp registerTime) throws DataBaseException {
		try {
			LOGGER.info("User tried to get list of Discounts where RegisterTime= " + registerTime);
			return discountDao.getByRegisterTime(registerTime);
		} catch (SQLException e) {
			LOGGER.error("In promt to get list of Discounts where RegisterTime= " + registerTime 
					+ "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	@Override
	public Discount getLatestDiscont() throws DataBaseException {
		try {
			LOGGER.info("User tried to get last Discount");
			return discountDao.getLatestDiscont();
		} catch (SQLException e) {
			LOGGER.error("In promt to get last Discount was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}
}
