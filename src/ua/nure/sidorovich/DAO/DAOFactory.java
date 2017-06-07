package ua.nure.sidorovich.DAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public abstract class DAOFactory {
	
	private static final String FQN_DAOFACTORY = (DAOFactory.class.getResource("").toString() 
			+ "../../../../../../ua.nure.sidorovich.DAO.DAOFactory").substring(6);
	
	private static DAOFactory instance;
	
	public static DAOFactory getInstance() {
		if (instance == null) {
			String className = null;
			try (BufferedReader in = new BufferedReader(
				new FileReader(FQN_DAOFACTORY))) {
				className = in.readLine();
			} catch (IOException ex) {
				ex.printStackTrace();
			}

			try {
				instance = (DAOFactory)Class
					.forName(className).newInstance();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
				ex.printStackTrace();
			}
			
		}
		return instance;
	}
		
	public abstract UserDao getUserDAO();
	
	public abstract UserRoleDao getUserRoleDAO();
	
	public abstract TourDao getTourDAO();
	
	public abstract RestTypeDao getRestTypeDAO();
	
	public abstract OrderStatusDao getOrderStatusDAO();
	
	public abstract OrderDao getOrderDAO();
	
	public abstract HotelTypeDao getHotelTypeDAO();
	
	public abstract HotelDao getHotelDAO();
	
	public abstract DiscountDao getDiscountDAO();
	
	public abstract BlockedUserDao getBlockedUserDAO();
	
}
