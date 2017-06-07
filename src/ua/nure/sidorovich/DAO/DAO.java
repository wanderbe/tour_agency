package ua.nure.sidorovich.DAO;

import java.sql.SQLException;
import java.util.List;

public interface DAO {
	Object getByID(long id) throws SQLException;
    List getList() throws SQLException;
    Object getByName(String name) throws SQLException;
    long save(Object o) throws SQLException;
    boolean update(Object o) throws SQLException;
    boolean remove(Object o) throws SQLException;
}
