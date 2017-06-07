package ua.nure.sidorovich.DAOCallStImpl;

import ua.nure.sidorovich.DAO.BlockedUserDao;
import ua.nure.sidorovich.DAO.DAOFactory;
import ua.nure.sidorovich.DAO.DiscountDao;
import ua.nure.sidorovich.DAO.HotelDao;
import ua.nure.sidorovich.DAO.HotelTypeDao;
import ua.nure.sidorovich.DAO.OrderDao;
import ua.nure.sidorovich.DAO.OrderStatusDao;
import ua.nure.sidorovich.DAO.RestTypeDao;
import ua.nure.sidorovich.DAO.TourDao;
import ua.nure.sidorovich.DAO.UserDao;
import ua.nure.sidorovich.DAO.UserRoleDao;

public class DAOCallStFactory extends DAOFactory{
	
	public UserDao getUserDAO() {
		return new UserDaoImpl();
	}

	public UserRoleDao getUserRoleDAO() {
		return new UserRoleDaoImpl();
	}
	
	public TourDao getTourDAO() {
		return new TourDaoImpl();
	}
	
	public RestTypeDao getRestTypeDAO() {
		return new RestTypeDaoImpl();
	}
	
	public OrderStatusDao getOrderStatusDAO() {
		return new OrderStatusDaoImpl();
	}
	
	public OrderDao getOrderDAO() {
		return new OrderDaoImpl();
	}
	
	public HotelTypeDao getHotelTypeDAO() {
		return new HotelTypeDaoImpl();
	}
	
	public HotelDao getHotelDAO() {
		return new HotelDaoImpl();
	}
	
	public DiscountDao getDiscountDAO() {
		return new DiscountDaoImpl();
	}
	
	public BlockedUserDao getBlockedUserDAO() {
		return new BlockedUserDaoImpl();
	}
	
}
