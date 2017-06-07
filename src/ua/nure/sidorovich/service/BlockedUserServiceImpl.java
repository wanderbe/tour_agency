package ua.nure.sidorovich.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.sidorovich.DAO.BlockedUserDao;
import ua.nure.sidorovich.DAO.DAOFactory;
import ua.nure.sidorovich.entety.BlockedUser;
import ua.nure.sidorovich.entety.User;
import ua.nure.sidorovich.util.DataBaseException;

public class BlockedUserServiceImpl implements BlockedUserService{
	
	private static final Logger LOGGER = Logger.getLogger(BlockedUserServiceImpl.class);
	
	private BlockedUserDao blockedUserDao = DAOFactory.getInstance().getBlockedUserDAO();
	
	@Override
	public BlockedUser getByUserID(long userId) throws DataBaseException {
		try {
			LOGGER.info("User tried to get BlockedUser by user id= " + userId);
			return blockedUserDao.getByUserID(userId);
		} catch (SQLException e) {
			LOGGER.error("In promt to get BlockedUser by user id= " + userId + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	@Override
	public long save(BlockedUser blockedUser) throws DataBaseException {
		try {
			LOGGER.info("User tried to save BlockedUser= " + blockedUser);
			return blockedUserDao.save(blockedUser);
		} catch (SQLException e) {
			LOGGER.error("In promt to save BlockedUser= " + blockedUser + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	@Override
	public BlockedUser getByUser(User user) throws DataBaseException {
		try {
			LOGGER.info("User tried to get BlockedUser by user= " + user);
			return blockedUserDao.getByUser(user);
		} catch (SQLException e) {
			LOGGER.error("In promt to get BlockedUser by user= " + user + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	@Override
	public List<BlockedUser> getList() throws DataBaseException {
		try {
			LOGGER.info("User tried to get list of all BlockedUser");
			return blockedUserDao.getList();
		} catch (SQLException e) {
			LOGGER.error("In promt to get list of all BlockedUser was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	@Override
	public boolean remove(BlockedUser blockedUser) throws DataBaseException {
		try {
			LOGGER.info("User tried to remove BlockedUser= " + blockedUser);
			return blockedUserDao.remove(blockedUser);
		} catch (SQLException e) {
			LOGGER.error("In promt to remove BlockedUser= " + blockedUser + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}
	
	

}
