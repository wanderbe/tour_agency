package ua.nure.sidorovich.service;

import java.util.List;

import ua.nure.sidorovich.entety.HotelType;
import ua.nure.sidorovich.util.DataBaseException;

public interface HotelTypeService {

	HotelType getByID(long id) throws DataBaseException;

	List<HotelType> getList() throws DataBaseException;

	long save(HotelType hotelType) throws DataBaseException;

	HotelType getByHotelClass(int hotelClass) throws DataBaseException;

	boolean update(HotelType hotelType) throws DataBaseException;

	boolean remove(HotelType hotelType) throws DataBaseException;

}