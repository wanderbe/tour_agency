package ua.nure.sidorovich.service;

import java.util.List;

import ua.nure.sidorovich.entety.Hotel;
import ua.nure.sidorovich.util.DataBaseException;

public interface HotelService {

	Hotel getByID(long id) throws DataBaseException;

	List<Hotel> getList() throws DataBaseException;

	long save(Hotel hotel) throws DataBaseException;

	Hotel getByName(String name) throws DataBaseException;

	boolean update(Hotel hotel) throws DataBaseException;

	boolean remove(Hotel hotel) throws DataBaseException;

}