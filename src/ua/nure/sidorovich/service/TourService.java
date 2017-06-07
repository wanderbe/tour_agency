package ua.nure.sidorovich.service;

import java.util.List;

import ua.nure.sidorovich.entety.Tour;
import ua.nure.sidorovich.util.DataBaseException;

public interface TourService {

	Tour getByID(long id) throws DataBaseException;

	List<Tour> getList() throws DataBaseException;

	Tour getByName(String name) throws DataBaseException;

	boolean update(Tour tour) throws DataBaseException;

	List<Tour> findByParametrs(long lookingRestTypeId, int lookingStartPrice, int lookingFinishPrice,
			int lookingPersonsAmount, long lookingHotelTypeId) throws DataBaseException;

	boolean remove(Tour tour) throws DataBaseException;

	long save(Tour tour) throws DataBaseException;

}