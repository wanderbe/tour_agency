package ua.nure.sidorovich.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.sidorovich.DAO.DAOFactory;
import ua.nure.sidorovich.DAO.UserRoleDao;
import ua.nure.sidorovich.entety.UserRole;
import ua.nure.sidorovich.util.DataBaseException;

public class UserRoleServiceImpl implements UserRoleService {
	
	private static final Logger LOGGER = Logger.getLogger(UserRoleServiceImpl.class);
	
	private UserRoleDao userRoleDao = DAOFactory.getInstance().getUserRoleDAO();

	
	@Override
	public UserRole getByID(long id) throws DataBaseException {
		try {
			LOGGER.info("User tried to get UserRole by id= " + id);
			return userRoleDao.getByID(id);
		} catch (SQLException e) {
			LOGGER.error("In promt to get UserRole by id= " + id + "; was returned exception ", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	
	@Override
	public List<UserRole> getList() throws DataBaseException {
		try {
			LOGGER.info("User tried to get list of all UserRole");
			return userRoleDao.getList();
		} catch (SQLException e) {
			LOGGER.error("In promt to get list of all UserRole was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	
	@Override
	public long save(UserRole userRole) throws DataBaseException {
		try {
			LOGGER.info("User tried to save UserRole= " + userRole);
			return userRoleDao.save(userRole);
		} catch (SQLException e) {
			LOGGER.error("In promt to save UserRole= " + userRole + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	
	@Override
	public UserRole getByName(String name) throws DataBaseException {
		try {
			LOGGER.info("User tried to get UserRole by name= " + name);
			return userRoleDao.getByName(name);
		} catch (SQLException e) {
			LOGGER.error("In promt to get UserRole by name= " + name + "; was returned exception ", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	
	@Override
	public boolean update(UserRole userRole) throws DataBaseException {
		try {
			LOGGER.info("User tried to update UserRole= " + userRole);
			return userRoleDao.update(userRole);
		} catch (SQLException e) {
			LOGGER.error("In promt to update UserRole= " + userRole + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	
	@Override
	public boolean remove(UserRole userRole) throws DataBaseException {
		try {
			LOGGER.info("User tried to remove UserRole= " + userRole);
			return userRoleDao.remove(userRole);
		} catch (SQLException e) {
			LOGGER.error("In promt to remove UserRole= " + userRole + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	
}
