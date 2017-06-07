package ua.nure.sidorovich.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.sidorovich.DAO.DAOFactory;
import ua.nure.sidorovich.DAO.TourDao;
import ua.nure.sidorovich.entety.Tour;
import ua.nure.sidorovich.util.DataBaseException;
import ua.nure.sidorovich.validators.ValidatorByWritesImpl;

public class TourServiceImpl implements TourService{
	
	private static final Logger LOGGER = Logger.getLogger(TourServiceImpl.class);
	
    private TourDao tourDao = DAOFactory.getInstance().getTourDAO();
    
	public TourServiceImpl(){
		super();
	}

	@Override
	public Tour getByID(long id) throws DataBaseException {
		try {
			LOGGER.info("User tried to get Tour by id= " + id);
			return tourDao.getByID(id);
		} catch (SQLException e) {
			LOGGER.error("In promt to get Tour by id= " + id + "; was returned exception ", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	@Override
	public List<Tour> getList() throws DataBaseException {
		try {
			LOGGER.info("User tried to get list of all Tour");
			return tourDao.getList();
		} catch (SQLException e) {
			LOGGER.error("In promt to get list of all Tour was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	@Override
	public long save(Tour tour) throws DataBaseException {
		
		try {
			LOGGER.info("User tried to save Tour= " + tour);
			
			if(new ValidatorByWritesImpl().isTourEntetyValid(tour)){
					return tourDao.save(tour);
			} else {
				return -1;
			}
			
		} catch (SQLException e) {
			LOGGER.error("In promt to save Tour= " + tour + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	@Override
	public Tour getByName(String name) throws DataBaseException {
		try {
			LOGGER.info("User tried to get Tour by name= " + name);
			return tourDao.getByName(name);
		} catch (SQLException e) {
			LOGGER.error("In promt to get Tour by name= " + name + "; was returned exception ", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	@Override
	public boolean update(Tour tour) throws DataBaseException {
		try {
			LOGGER.info("User tried to update Tour= " + tour);
		
			if(new ValidatorByWritesImpl().isTourEntetyValid(tour)){
				return tourDao.update(tour);
			} else {
				return false;
			}
		
		} catch (SQLException e) {
			LOGGER.error("In promt to update Tour= " + tour + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	@Override
	public List<Tour> findByParametrs(long lookingRestTypeId, int lookingStartPrice, int lookingFinishPrice,
			int lookingPersonsAmount, long lookingHotelTypeId) throws DataBaseException {
		try {
			LOGGER.info("User tried to get Tour by lookingRestTypeId= " + lookingRestTypeId 
					+ "; and lookingStartPrice= " + lookingStartPrice
					+ "; and lookingFinishPrice= " + lookingFinishPrice
					+ "; and lookingPersonsAmount= " + lookingPersonsAmount
					+ "; and lookingHotelTypeId= " + lookingHotelTypeId);
			return tourDao.findByParametrs(lookingRestTypeId, lookingStartPrice, lookingFinishPrice, lookingPersonsAmount,
					lookingHotelTypeId);
		} catch (SQLException e) {
			LOGGER.error("In promt to get Tour by lookingRestTypeId= " + lookingRestTypeId 
					+ "; and lookingStartPrice= " + lookingStartPrice
					+ "; and lookingFinishPrice= " + lookingFinishPrice
					+ "; and lookingPersonsAmount= " + lookingPersonsAmount
					+ "; and lookingHotelTypeId= " + lookingHotelTypeId
					+ "; was returned exception ", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	@Override
	public boolean remove(Tour tour) throws DataBaseException {
		try {
			LOGGER.info("User tried to remove Tour= " + tour);
			return tourDao.remove(tour);
		} catch (SQLException e) {
			LOGGER.error("In promt to remove Tour= " + tour + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}
    
    
}
