package ua.nure.sidorovich.DAO;

import java.sql.SQLException;
import java.util.List;

import ua.nure.sidorovich.entety.Tour;

public interface TourDao extends DAO{
	
	@Override
	Tour getByID(long id) throws SQLException;
	
	@Override
    List<Tour> getList() throws SQLException;
	
	@Override
	Tour getByName(String name) throws SQLException;

	List<Tour> findByParametrs(long lookingRestTypeId, int lookingStartPrice, int lookingFinishPrice,
			int lookingPersonsAmount, long lookingHotelTypeId) throws SQLException;
}
