package ua.nure.sidorovich.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.sidorovich.DAO.DAOFactory;
import ua.nure.sidorovich.DAO.HotelTypeDao;
import ua.nure.sidorovich.entety.HotelType;
import ua.nure.sidorovich.util.DataBaseException;

public class HotelTypeServiceImpl implements HotelTypeService {
	
	private static final Logger LOGGER = Logger.getLogger(HotelTypeServiceImpl.class);
	
	private HotelTypeDao hotelTypeDao = DAOFactory.getInstance().getHotelTypeDAO();

	@Override
	public HotelType getByID(long id) throws DataBaseException {
		try {
			LOGGER.info("User tried to get Hotel by id= " + id);
			return hotelTypeDao.getByID(id);
		} catch (SQLException e) {
			LOGGER.error("In promt to get Hotel by id= " + id + "; was returned exception ", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	@Override
	public List<HotelType> getList() throws DataBaseException {
		try {
			LOGGER.info("User tried to get list of all HotelType");
			return hotelTypeDao.getList();
		} catch (SQLException e) {
			LOGGER.error("In promt to get list of all HotelType was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}


	@Override
	public long save(HotelType hotelType) throws DataBaseException {
		try {
			LOGGER.info("User tried to save HotelType= " + hotelType);
			return hotelTypeDao.save(hotelType);
		} catch (SQLException e) {
			LOGGER.error("In promt to save HotelType= " + hotelType + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}


	@Override
	public HotelType getByHotelClass(int hotelClass) throws DataBaseException {
		try {
			LOGGER.info("User tried to get HotelType by hotelClass= " + hotelClass);
			return hotelTypeDao.getByHotelClass(hotelClass);
		} catch (SQLException e) {
			LOGGER.error("In promt to get HotelType by hotelClass= " + hotelClass + "; was returned exception ", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}


	@Override
	public boolean update(HotelType hotelType) throws DataBaseException {
		try {
			LOGGER.info("User tried to update HotelType= " + hotelType);
			return hotelTypeDao.update(hotelType);
		} catch (SQLException e) {
			LOGGER.error("In promt to update HotelType= " + hotelType + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}


	@Override
	public boolean remove(HotelType hotelType) throws DataBaseException {
		try {
			LOGGER.info("User tried to remove HotelType= " + hotelType);
			return hotelTypeDao.remove(hotelType);
		} catch (SQLException e) {
			LOGGER.error("In promt to remove HotelType= " + hotelType + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}
	
	
}
