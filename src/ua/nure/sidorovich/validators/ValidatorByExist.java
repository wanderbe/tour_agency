package ua.nure.sidorovich.validators;

import ua.nure.sidorovich.entety.User;
import ua.nure.sidorovich.util.DataBaseException;

public interface ValidatorByExist {

	boolean isUserValid(User user) throws DataBaseException;

	boolean isThisLoginEcxist(String login) throws DataBaseException;

	boolean isThisEmailEcxist(String email) throws DataBaseException;

}
