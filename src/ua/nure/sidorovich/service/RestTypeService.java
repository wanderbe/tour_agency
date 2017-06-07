package ua.nure.sidorovich.service;

import java.util.List;

import ua.nure.sidorovich.entety.RestType;
import ua.nure.sidorovich.util.DataBaseException;

public interface RestTypeService {

	RestType getByID(long id) throws DataBaseException;

	List<RestType> getList() throws DataBaseException;

	long save(RestType restType) throws DataBaseException;

	RestType getByName(String name) throws DataBaseException;

	boolean update(RestType restType) throws DataBaseException;

	boolean remove(RestType restType) throws DataBaseException;

}