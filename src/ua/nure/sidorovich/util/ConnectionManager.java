package ua.nure.sidorovich.util;

import java.sql.Connection;

public interface ConnectionManager {
	
	public Connection getConnection() throws DataBaseException;
	
	public Connection getConnectionForTransactions() throws DataBaseException;

	void closeConnection(WrapperConnection wrapperConnection);

}
