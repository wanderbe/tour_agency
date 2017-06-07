package ua.nure.sidorovich.service;

import java.sql.Timestamp;
import java.util.List;

import ua.nure.sidorovich.entety.Discount;
import ua.nure.sidorovich.util.DataBaseException;

public interface DiscountService {

	Discount getByID(long id) throws DataBaseException;

	long save(Discount discount) throws DataBaseException;

	List<Discount> getList() throws DataBaseException;

	List<Discount> getByRegisterTime(Timestamp registerTime) throws DataBaseException;

	Discount getLatestDiscont() throws DataBaseException;

}