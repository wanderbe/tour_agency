package ua.nure.sidorovich.DAO;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ua.nure.sidorovich.entety.BlockedUser;
import ua.nure.sidorovich.entety.User;
import ua.nure.sidorovich.entety.UserRole;

public class BlockedUserDaoTest {
	
	
	static BlockedUserDao blockedUserDao = DAOFactory.getInstance().getBlockedUserDAO();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BlockedUser forDelete = new BlockedUser();
		
		User user = new User();
		user.setId(4);
		user.setName("djec4");
		user.setBirthday(new Date(116, 5, 8));
		user.setEmail("tour.agency.user4@gmail.com");
		user.setPhone("4");
		user.setLogin("djec4");
		
		UserRole userRole = new UserRole();
		userRole.setId(1);
		userRole.setName("user");
		userRole.setDescription("user");
		
		user.setRole(userRole);
		
		forDelete.setUser(user);
		forDelete.setDescription("forDelete");
		forDelete.setBlockTime(new Timestamp(System.currentTimeMillis()));
		
		blockedUserDao.save(forDelete);
		
		BlockedUser test = new BlockedUser();
		
		User user1 = new User();
		user1.setId(2);
		user1.setName("djec2");
		user1.setBirthday(new Date(116, 5, 8));
		user1.setEmail("tour.agency.user2@gmail.com");
		user1.setPhone("2");
		user1.setLogin("djec2");
		
		UserRole userRole1 = new UserRole();
		userRole1.setId(2);
		userRole1.setName("manager");
		userRole1.setDescription("manager");
		
		user.setRole(userRole1);
		
		test.setUser(user1);
		test.setDescription("test");
		test.setBlockTime(new Timestamp(System.currentTimeMillis()));
		
		blockedUserDao.save(test);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
			blockedUserDao.remove(blockedUserDao.getByUserID(4));
			blockedUserDao.remove(blockedUserDao.getByUserID(2));
	}
	

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		blockedUserDao.remove(blockedUserDao.getByUserID(1));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetByIDLong() {
		try {
			blockedUserDao.getByID(1);
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
	}

	@Test
	public void testGetList() {
				
		List<BlockedUser> actual = null;
		
		try {
			actual = blockedUserDao.getList();
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertTrue(actual.size() == 2);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetByNameString() {
		try {
			blockedUserDao.getByName(null);
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
	}

	@Test
	public void testSave() {
		
		BlockedUser expected = new BlockedUser();
		
		User user = new User();
		user.setId(1);
		user.setName("djec1");
		user.setBirthday(new Date(116, 5, 8));
		user.setEmail("tour.agency.user1@gmail.com");
		user.setPhone("1");
		user.setLogin("djec1");
		
		UserRole userRole = new UserRole();
		userRole.setId(1);
		userRole.setName("user");
		userRole.setDescription("user");
		
		user.setRole(userRole);
		
		expected.setUser(user);
		expected.setDescription("saved");
		expected.setBlockTime(new Timestamp(System.currentTimeMillis()));
		
		BlockedUser actual = null;
		
		try {
			blockedUserDao.save(expected);
			actual = blockedUserDao.getByUserID(1);
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		
		assertEquals(expected.getDescription(), actual.getDescription());
		assertEquals(expected.getUser(), actual.getUser());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testUpdate() {
		try {
			blockedUserDao.update(null);
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
	}

	@Test
	public void testRemove() {
		
		List<BlockedUser> actual = null;
		
		try {
			blockedUserDao.remove(blockedUserDao.getByUserID(2));
			actual = blockedUserDao.getList();
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		
		boolean result = false;
		
		for(BlockedUser b : actual){
			result = b.getUser().getId() != 2;
		}
		
		assertTrue(result);
	}

	@Test
	public void testGetByUserID() {
		BlockedUser expected = new BlockedUser();
		
		User user = new User();
		user.setId(2);
		user.setName("djec2");
		user.setBirthday(new Date(116, 5, 8));
		user.setEmail("tour.agency.user2@gmail.com");
		user.setPhone("2");
		user.setLogin("djec2");
		
		UserRole userRole = new UserRole();
		userRole.setId(2);
		userRole.setName("manager");
		userRole.setDescription("manager");
		
		user.setRole(userRole);
		
		expected.setUser(user);
		expected.setDescription("test");
		
		BlockedUser actual = null;
		
		try {
			actual = blockedUserDao.getByUserID(2);
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		
		assertEquals(expected.getUser(), actual.getUser());
		assertEquals(expected.getDescription(), actual.getDescription());		
	}

	@Test
	public void testGetByUser() {
		
		BlockedUser expected = new BlockedUser();
		
		User user = new User();
		user.setId(2);
		user.setName("djec2");
		user.setBirthday(new Date(116, 5, 8));
		user.setEmail("tour.agency.user2@gmail.com");
		user.setPhone("2");
		user.setLogin("djec2");
		
		UserRole userRole = new UserRole();
		userRole.setId(2);
		userRole.setName("manager");
		userRole.setDescription("manager");
		
		user.setRole(userRole);
		
		expected.setUser(user);
		expected.setDescription("test");
		
		BlockedUser actual = null;
		
		try {
			actual = blockedUserDao.getByUser(user);
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		
		assertEquals(expected.getUser(), actual.getUser());
		assertEquals(expected.getDescription(), actual.getDescription());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetByName() {
		try {
			blockedUserDao.getByName("");
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
	}


}
