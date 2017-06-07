package ua.nure.sidorovich.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.sidorovich.DAO.DAOFactory;
import ua.nure.sidorovich.DAO.UserDao;
import ua.nure.sidorovich.entety.User;
import ua.nure.sidorovich.security.PasswordCoder;
import ua.nure.sidorovich.util.DataBaseException;
import ua.nure.sidorovich.validators.ValidatorByExist;
import ua.nure.sidorovich.validators.ValidatorByExistImpl;
import ua.nure.sidorovich.validators.ValidatorByWrites;
import ua.nure.sidorovich.validators.ValidatorByWritesImpl;

public class UserServiceImpl implements UserService{
	
	private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
	
	private UserDao userDao = DAOFactory.getInstance().getUserDAO();
	
	private ValidatorByWrites validatorByWrites = new ValidatorByWritesImpl();
	
	private ValidatorByExist validatorByExist = new ValidatorByExistImpl();
	
	private BlockedUserService blockedUserService = new BlockedUserServiceImpl();
	
	
	
	@Override
	public User saveAndReturnSavedUser(User user) throws DataBaseException {
		long id = save(user);
		if(id > 0){
			return getByID(id);
		} else {
			return null;
		}
	}
	
	@Override
	public boolean isItBlocked(Long idUser){
		boolean result = true;
			User resultUser;
			try {
				resultUser = getByID(idUser);
				if(blockedUserService.getByUser(resultUser) == null){
					result = false;
				}
			} catch (DataBaseException e) {
				LOGGER.error("In promt to check User with id= " + idUser + "; was returned exception", e);
			}
		return result;
	}
	
	@Override
	public boolean isItBlocked(User user){
		if(user == null){
			return false;
		} else {
			return isItBlocked(user.getId());
		}
	}
	

	@Override
	public boolean updateUser(User newUser) throws DataBaseException{
		try {
			LOGGER.info("User tried to update User= " + newUser);
		
			if(!validatorByWrites.isUserEntetyValidIgnorePassword(newUser)){
				return false;
			} else if(!validatorByExist.isUserValid(newUser)){
				return false;
			} else {
				return userDao.update(newUser);
			}
		
		} catch (SQLException e) {
			LOGGER.error("In promt to update User= " + newUser + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}	
	}

	@Override
	public User getByLogin(String login) throws DataBaseException{
		try {
			LOGGER.info("User tried to get User by login= " + login);
			return userDao.getByLogin(login);
		} catch (SQLException e) {
			LOGGER.error("In promt to get User by login= " + login + "; was returned exception ", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}


	@Override
	public User getByID(long id) throws DataBaseException {
		try {
			LOGGER.info("User tried to get User by id= " + id);
			return userDao.getByID(id);
		} catch (SQLException e) {
			LOGGER.error("In promt to get User by id= " + id + "; was returned exception ", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}


	@Override
	public List<User> getList() throws DataBaseException {
		try {
			LOGGER.info("User tried to get list of all User");
			return userDao.getList();
		} catch (SQLException e) {
			LOGGER.error("In promt to get list of all User was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}


	@Override
	public long save(User user) throws DataBaseException {
		
		try {
			LOGGER.info("User tried to save User= " + user);
			
			if(validatorByWrites.isUserEntetyValid(user)){
				user.setPassword(PasswordCoder.hash(user.getPassword(), PasswordCoder.SHA_256));
				return userDao.save(user);
			} else {
				return -1;
			}
		
		} catch (SQLException e) {
			LOGGER.error("In promt to save User= " + user + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
		
	}


	@Override
	public User getByName(String name) throws DataBaseException {
		try {
			LOGGER.info("User tried to get User by name= " + name);
			return userDao.getByName(name);
		} catch (SQLException e) {
			LOGGER.error("In promt to get User by name= " + name + "; was returned exception ", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}


	@Override
	public boolean update(User user) throws DataBaseException {
		try {
			LOGGER.info("User tried to update User= " + user);
			return userDao.update(user);
		} catch (SQLException e) {
			LOGGER.error("In promt to update User= " + user + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}


	@Override
	public boolean remove(User user) throws DataBaseException {
		try {
			LOGGER.info("User tried to remove User= " + user);
			return userDao.remove(user);
		} catch (SQLException e) {
			LOGGER.error("In promt to remove User= " + user + "; was returned exception", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}


	@Override
	public User getByEmail(String email) throws DataBaseException {
		try {
			LOGGER.info("User tried to get User by email= " + email);
			return userDao.getByEmail(email);
		} catch (SQLException e) {
			LOGGER.error("In promt to get User by email= " + email + "; was returned exception ", e);
			throw new DataBaseException("Some problem with Data Base", e);
		}
	}

	

	
	
	
}
