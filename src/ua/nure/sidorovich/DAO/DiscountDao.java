package ua.nure.sidorovich.DAO;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import ua.nure.sidorovich.entety.Discount;

public interface DiscountDao extends DAO{
		
	@Override
	Discount getByID(long id) throws SQLException;
	
	@Override
    List<Discount> getList() throws SQLException;
	
	List<Discount> getByRegisterTime(Timestamp registerTime) throws SQLException;
	
	Discount getLatestDiscont() throws SQLException;
	
}
