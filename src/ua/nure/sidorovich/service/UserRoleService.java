package ua.nure.sidorovich.service;

import java.util.List;

import ua.nure.sidorovich.entety.UserRole;
import ua.nure.sidorovich.util.DataBaseException;

public interface UserRoleService {

	UserRole getByID(long id) throws DataBaseException;

	List<UserRole> getList() throws DataBaseException;

	long save(UserRole userRole) throws DataBaseException;

	UserRole getByName(String name) throws DataBaseException;

	boolean update(UserRole userRole) throws DataBaseException;

	boolean remove(UserRole userRole) throws DataBaseException;

}