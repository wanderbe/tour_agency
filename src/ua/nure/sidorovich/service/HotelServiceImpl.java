package ua.nure.sidorovich.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.sidorovich.DAO.DAOFactory;
import ua.nure.sidorovich.DAO.HotelDao;
import ua.nure.sidorovich.entety.Hotel;
import ua.nure.sidorovich.util.DataBaseException;

public class HotelServiceImpl implements HotelService {
	
	private static final Logger LOGGER = Logger.getLogger(HotelServiceImpl.class);
	
	private HotelDao hotelDao = DAOFactory.getInstance().getHotelDAO();

	public HotelServiceImpl(){
		super();
	}

	@Override
	public Hotel getByID(long id) throws DataBaseException {
		try {
			LOGGER.info("User tried to get Hotel by id= " + id);
			return hotelDao.getByID(id);
		} catch (SQLException e) {
			LOGGER.error("In promt to get Hotel by id= " + id + "; was returned exception ", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	@Override
	public List<Hotel> getList() throws DataBaseException {
		try {
			LOGGER.info("User tried to get list of all Hotel");
			return hotelDao.getList();
		} catch (SQLException e) {
			LOGGER.error("In promt to get list of all Hotel was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	@Override
	public long save(Hotel hotel) throws DataBaseException {
		try {
			LOGGER.info("User tried to save Hotel= " + hotel);
			return hotelDao.save(hotel);
		} catch (SQLException e) {
			LOGGER.error("In promt to save Hotel= " + hotel + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	@Override
	public Hotel getByName(String name) throws DataBaseException {
		try {
			LOGGER.info("User tried to get Hotel by name= " + name);
			return hotelDao.getByName(name);
		} catch (SQLException e) {
			LOGGER.error("In promt to get Hotel by name= " + name + "; was returned exception ", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	@Override
	public boolean update(Hotel hotel) throws DataBaseException {
		try {
			LOGGER.info("User tried to update Hotel= " + hotel);
			return hotelDao.update(hotel);
		} catch (SQLException e) {
			LOGGER.error("In promt to update Hotel= " + hotel + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	@Override
	public boolean remove(Hotel hotel) throws DataBaseException {
		try {
			LOGGER.info("User tried to remove Hotel= " + hotel);
			return hotelDao.remove(hotel);
		} catch (SQLException e) {
			LOGGER.error("In promt to remove Hotel= " + hotel + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}
	
	
}
