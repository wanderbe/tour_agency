package ua.nure.sidorovich.DAO;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ua.nure.sidorovich.entety.User;
import ua.nure.sidorovich.entety.UserRole;

public class UserDaoTest {
	
	static UserDao userDao = DAOFactory.getInstance().getUserDAO();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		User forDelete = new User();
		forDelete.setName("forDelete");
		forDelete.setLogin("forDelete");
		forDelete.setBirthday(new Date(2016, 6, 8));
		forDelete.setEmail("qwdqwdwq11@qdw.com");
		forDelete.setPhone("332515325321");
		forDelete.setPassword("password");
		
		UserRole userRole = new UserRole();
		userRole.setId(1);
		userRole.setName("user");
		userRole.setDescription("userast1");
		
		forDelete.setRole(userRole);
		
		User forUpdate = new User();
		forUpdate.setName("forUpdate");
		forUpdate.setLogin("forUpdate");
		forUpdate.setBirthday(new Date(2016, 6, 8));
		forUpdate.setEmail("qwdqwdwq22@qdw.com");
		forUpdate.setPhone("332515325322");
		forUpdate.setPassword("password");
				
		forUpdate.setRole(userRole);
		
		userDao.save(forDelete);
		userDao.save(forUpdate);

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
			userDao.remove(userDao.getByName("forDelete"));
			userDao.remove(userDao.getByName("forUpdate"));
	}
	
	@Before
	public void before() throws Exception {
	}
	
	@After
	public void after() throws Exception {
			userDao.remove(userDao.getByName("saved"));
	}

	@Test
	public void testGetByIDLong() {
		
		User expected = new User();
		expected.setId(1);
		expected.setName("djec1");
		expected.setBirthday(new Date(116, 5, 8));
		expected.setEmail("tour.agency.user1@gmail.com");
		expected.setPhone("1");
		expected.setLogin("djec1");
		
		UserRole userRole = new UserRole();
		userRole.setId(1);
		userRole.setName("user");
		userRole.setDescription("user");
		
		expected.setRole(userRole);
		
		User actual = null;
		
		try {
			actual = userDao.getByID(1);
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testGetList() {
		
		List<User> expected = new ArrayList<User>();
		
		User expected1 = new User();
		expected1.setId(1);
		expected1.setName("djec1");
		expected1.setBirthday(new Date(116, 5, 8));
		expected1.setEmail("tour.agency.user1@gmail.com");
		expected1.setPhone("1");
		expected1.setLogin("djec1");
		
		UserRole userRole1 = new UserRole();
		userRole1.setId(1);
		userRole1.setName("user");
		userRole1.setDescription("user");
		
		expected1.setRole(userRole1);
		
		expected.add(expected1);
		
		User expected2 = new User();
		expected2.setId(2);
		expected2.setName("djec2");
		expected2.setBirthday(new Date(116, 5, 8));
		expected2.setEmail("tour.agency.user2@gmail.com");
		expected2.setPhone("2");
		expected2.setLogin("djec2");
		
		UserRole userRole2 = new UserRole();
		userRole2.setId(2);
		userRole2.setName("manager");
		userRole2.setDescription("manager");
		
		expected2.setRole(userRole2);
		
		expected.add(expected2);
		
		User expected3 = new User();
		expected3.setId(3);
		expected3.setName("djec3");
		expected3.setBirthday(new Date(116, 5, 8));
		expected3.setEmail("tour.agency.user3@gmail.com");
		expected3.setPhone("3");
		expected3.setLogin("djec3");
		
		UserRole userRole3 = new UserRole();
		userRole3.setId(3);
		userRole3.setName("admin");
		userRole3.setDescription("admin");
		
		expected3.setRole(userRole3);
		
		expected.add(expected3);
		
		User expected4 = new User();
		expected4.setId(4);
		expected4.setName("djec4");
		expected4.setBirthday(new Date(116, 5, 8));
		expected4.setEmail("tour.agency.user4@gmail.com");
		expected4.setPhone("4");
		expected4.setLogin("djec4");
		
		UserRole userRole4 = new UserRole();
		userRole4.setId(1);
		userRole4.setName("user");
		userRole4.setDescription("user");
		
		expected4.setRole(userRole1);
		
		expected.add(expected4);
		
		try {
			expected.add(userDao.getByName("forDelete"));
			expected.add(userDao.getByName("forUpdate"));
		} catch (SQLException e1) {
			fail("Fail with exception" + e1.getMessage());
		}
		
		List<User> actual = null;
		
		try {
			actual = userDao.getList();
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testGetByNameString() {
		User expected = new User();
		expected.setId(1);
		expected.setName("djec1");
		expected.setBirthday(new Date(116, 5, 8));
		expected.setEmail("tour.agency.user1@gmail.com");
		expected.setPhone("1");
		expected.setLogin("djec1");
		
		UserRole userRole = new UserRole();
		userRole.setId(1);
		userRole.setName("user");
		userRole.setDescription("user");
		
		expected.setRole(userRole);
		
		User actual = null;
		
		try {
			actual = userDao.getByName("djec1");
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testSave() {
		User expected = new User();
		expected.setName("saved");
		expected.setBirthday(new Date(116, 5, 8));
		expected.setEmail("tour.agency.user100@gmail.com");
		expected.setPhone("100");
		expected.setLogin("saved");
		expected.setPassword("password");
		
		UserRole userRole = new UserRole();
		userRole.setId(1);
		userRole.setName("user");
		userRole.setDescription("user");
		
		expected.setRole(userRole);
		
		User actual = null;
		
		try {
			userDao.save(expected);
			actual = userDao.getByName("saved");
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected.getName(), actual.getName());
	}

	@Test
	public void testUpdate() {
		User expected = null;
		try {
			expected = userDao.getByName("forUpdate");
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		expected.setLogin("updated");
		
		User actual = null;
		try {
			userDao.update(expected);
			actual = userDao.getByName("forUpdate");
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testRemove() {
List<User> expected = new ArrayList<User>();
		
		User expected1 = new User();
		expected1.setId(1);
		expected1.setName("djec1");
		expected1.setBirthday(new Date(116, 5, 8));
		expected1.setEmail("tour.agency.user1@gmail.com");
		expected1.setPhone("1");
		expected1.setLogin("djec1");
		
		UserRole userRole1 = new UserRole();
		userRole1.setId(1);
		userRole1.setName("user");
		userRole1.setDescription("user");
		
		expected1.setRole(userRole1);
		
		expected.add(expected1);
		
		User expected2 = new User();
		expected2.setId(2);
		expected2.setName("djec2");
		expected2.setBirthday(new Date(116, 5, 8));
		expected2.setEmail("tour.agency.user2@gmail.com");
		expected2.setPhone("2");
		expected2.setLogin("djec2");
		
		UserRole userRole2 = new UserRole();
		userRole2.setId(2);
		userRole2.setName("manager");
		userRole2.setDescription("manager");
		
		expected2.setRole(userRole2);
		
		expected.add(expected2);
		
		User expected3 = new User();
		expected3.setId(3);
		expected3.setName("djec3");
		expected3.setBirthday(new Date(116, 5, 8));
		expected3.setEmail("tour.agency.user3@gmail.com");
		expected3.setPhone("3");
		expected3.setLogin("djec3");
		
		UserRole userRole3 = new UserRole();
		userRole3.setId(3);
		userRole3.setName("admin");
		userRole3.setDescription("admin");
		
		expected3.setRole(userRole3);
		
		expected.add(expected3);
		
		User expected4 = new User();
		expected4.setId(4);
		expected4.setName("djec4");
		expected4.setBirthday(new Date(116, 5, 8));
		expected4.setEmail("tour.agency.user4@gmail.com");
		expected4.setPhone("4");
		expected4.setLogin("djec4");
		
		UserRole userRole4 = new UserRole();
		userRole4.setId(1);
		userRole4.setName("user");
		userRole4.setDescription("user");
		
		expected4.setRole(userRole1);
		
		expected.add(expected4);
		
		try {
			expected.add(userDao.getByName("forUpdate"));
		} catch (SQLException e1) {
			fail("Fail with exception" + e1.getMessage());
		}
		
		List<User> actual = null;
		
		try {
			userDao.remove(userDao.getByName("forDelete"));
			actual = userDao.getList();
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testGetByLogin() {
		User expected = new User();
		expected.setId(1);
		expected.setName("djec1");
		expected.setBirthday(new Date(116, 5, 8));
		expected.setEmail("tour.agency.user1@gmail.com");
		expected.setPhone("1");
		expected.setLogin("djec1");
		
		UserRole userRole = new UserRole();
		userRole.setId(1);
		userRole.setName("user");
		userRole.setDescription("user");
		
		expected.setRole(userRole);
		
		User actual = null;
		
		try {
			actual = userDao.getByLogin("djec1");
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testGetByEmail() {
		User expected = new User();
		expected.setId(1);
		expected.setName("djec1");
		expected.setBirthday(new Date(116, 5, 8));
		expected.setEmail("tour.agency.user1@gmail.com");
		expected.setPhone("1");
		expected.setLogin("djec1");
		
		UserRole userRole = new UserRole();
		userRole.setId(1);
		userRole.setName("user");
		userRole.setDescription("user");
		
		expected.setRole(userRole);
		
		User actual = null;
		
		try {
			actual = userDao.getByEmail("tour.agency.user1@gmail.com");
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}



}
