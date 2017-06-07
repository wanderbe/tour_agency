package ua.nure.sidorovich.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.sidorovich.DAO.DAOFactory;
import ua.nure.sidorovich.DAO.RestTypeDao;
import ua.nure.sidorovich.entety.RestType;
import ua.nure.sidorovich.util.DataBaseException;

public class RestTypeServiceImpl implements RestTypeService {
	
	private static final Logger LOGGER = Logger.getLogger(RestTypeServiceImpl.class);
	
	private RestTypeDao restTypeDao = DAOFactory.getInstance().getRestTypeDAO();
	
	

	public RestTypeServiceImpl(){
		super();
	}

	
	@Override
	public RestType getByID(long id) throws DataBaseException {
		try {
			LOGGER.info("User tried to get RestType by id= " + id);
			return restTypeDao.getByID(id);
		} catch (SQLException e) {
			LOGGER.error("In promt to get RestType by id= " + id + "; was returned exception ", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	@Override
	public List<RestType> getList() throws DataBaseException {
		try {
			LOGGER.info("User tried to get list of all RestType");
			return restTypeDao.getList();
		} catch (SQLException e) {
			LOGGER.error("In promt to get list of all RestType was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	
	@Override
	public long save(RestType restType) throws DataBaseException {
		try {
			LOGGER.info("User tried to save RestType= " + restType);
			return restTypeDao.save(restType);
		} catch (SQLException e) {
			LOGGER.error("In promt to save RestType= " + restType + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	
	@Override
	public RestType getByName(String name) throws DataBaseException {
		try {
			LOGGER.info("User tried to get Order by name= " + name);
			return restTypeDao.getByName(name);
		} catch (SQLException e) {
			LOGGER.error("In promt to get Order by name= " + name + "; was returned exception ", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	
	@Override
	public boolean update(RestType restType) throws DataBaseException {
		try {
			LOGGER.info("User tried to update RestType= " + restType);
			return restTypeDao.update(restType);
		} catch (SQLException e) {
			LOGGER.error("In promt to update RestType= " + restType + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	
	@Override
	public boolean remove(RestType restType) throws DataBaseException {
		try {
			LOGGER.info("User tried to remove RestType= " + restType);
			return restTypeDao.remove(restType);
		} catch (SQLException e) {
			LOGGER.error("In promt to remove RestType= " + restType + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}
	
}
