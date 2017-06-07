package ua.nure.sidorovich.service;

import java.util.List;

import ua.nure.sidorovich.entety.User;
import ua.nure.sidorovich.util.DataBaseException;

public interface UserService {

	boolean isItBlocked(User user);

	boolean updateUser(User newUser) throws DataBaseException;

	User getByLogin(String login) throws DataBaseException;

	User getByID(long id) throws DataBaseException;

	List<User> getList() throws DataBaseException;

	long save(User user) throws DataBaseException;

	User getByName(String name) throws DataBaseException;

	boolean update(User user) throws DataBaseException;

	boolean remove(User user) throws DataBaseException;

	User getByEmail(String email) throws DataBaseException;

	User saveAndReturnSavedUser(User user) throws DataBaseException;

	boolean isItBlocked(Long idUser) throws DataBaseException;

}