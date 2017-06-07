package ua.nure.sidorovich.validators;

import ua.nure.sidorovich.entety.User;
import ua.nure.sidorovich.service.UserService;
import ua.nure.sidorovich.service.UserServiceImpl;
import ua.nure.sidorovich.util.DataBaseException;

public class ValidatorByExistImpl implements ValidatorByExist{

	@Override
	public boolean isUserValid(User user) throws DataBaseException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isThisLoginEcxist(String login) throws DataBaseException {
		UserService userService = new UserServiceImpl();
		User user = null;
		user = userService.getByLogin(login);
		if(user != null){
			return true;
		}
		return false;
	}

	@Override
	public boolean isThisEmailEcxist(String email) throws DataBaseException {
		UserService userService = new UserServiceImpl();
		User user = null;
		user = userService.getByEmail(email);
		if(user != null){
			return true;
		}
		return false;
	}

}
