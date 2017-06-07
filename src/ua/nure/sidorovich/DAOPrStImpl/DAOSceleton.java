package ua.nure.sidorovich.DAOPrStImpl;

import java.sql.SQLException;
import java.sql.Statement;

import ua.nure.sidorovich.DAO.DAO;

public abstract class DAOSceleton implements DAO{
	

	public DAOSceleton(){
	}
	
	public void closeStatement(Statement statement) {
		try {
			if(statement != null){
				statement.close();
			}
		} catch (SQLException e) {
			try {
				statement.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
	}
}
