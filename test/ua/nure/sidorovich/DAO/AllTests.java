package ua.nure.sidorovich.DAO;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BlockedUserDaoTest.class, DiscountDaoTest.class, HotelDaoTest.class, HotelTypeDaoTest.class,
		OrderStatusDaoTest.class, RestTypeDaoTest.class, TourDaoTest.class, UserDaoTest.class, UserRoleDaoTest.class })
public class AllTests {

}
