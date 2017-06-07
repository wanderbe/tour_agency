package ua.nure.sidorovich.DAO;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ua.nure.sidorovich.entety.UserRole;

public class UserRoleDaoTest {
	
	static UserRoleDao userRoleDao = DAOFactory.getInstance().getUserRoleDAO();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		UserRole forDelete = new UserRole();
		forDelete.setName("forDelete");
		forDelete.setDescription("forDelete");
		
		UserRole forUpdate = new UserRole();
		forUpdate.setName("forUpdate");
		forUpdate.setDescription("forUpdate");
		
		userRoleDao.save(forDelete);
		userRoleDao.save(forUpdate);

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
			userRoleDao.remove(userRoleDao.getByName("forDelete"));
			userRoleDao.remove(userRoleDao.getByName("forUpdate"));
	}
	
	@Before
	public void before() throws Exception {
	}
	
	@After
	public void after() throws Exception {
			userRoleDao.remove(userRoleDao.getByName("saved"));
	}

	@Test
	public void testGetByIDLong() {
		UserRole expected = new UserRole();
		expected.setId(1);
		expected.setName("user");
		expected.setDescription("user");
		
		UserRole actual = null;
		
		try {
			actual = userRoleDao.getByID(1);
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testGetList() {
		
		List<UserRole> expected = new ArrayList<UserRole>();
		
		UserRole expected1 = new UserRole();
		expected1.setId(1);
		expected1.setName("user");
		expected1.setDescription("user");
		
		expected.add(expected1);
		
		UserRole expected2 = new UserRole();
		expected2.setId(2);
		expected2.setName("manager");
		expected2.setDescription("manager");
		
		expected.add(expected2);
		
		UserRole expected3 = new UserRole();
		expected3.setId(3);
		expected3.setName("admin");
		expected3.setDescription("admin");
		
		expected.add(expected3);
		
		try {
			expected.add(userRoleDao.getByName("forDelete"));
			expected.add(userRoleDao.getByName("forUpdate"));
		} catch (SQLException e1) {
			fail("Fail with exception" + e1.getMessage());
		}
		
		List<UserRole> actual = null;
		
		try {
			actual = userRoleDao.getList();
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testGetByNameString() {
		UserRole expected = new UserRole();
		expected.setId(1);
		expected.setName("user");
		expected.setDescription("user");
		
		UserRole actual = null;
		
		try {
			actual = userRoleDao.getByName("user");
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testSave() {
		UserRole expected = new UserRole();
		expected.setName("saved");
		expected.setDescription("saved");
		
		UserRole actual = null;
		
		try {
			userRoleDao.save(expected);
			actual = userRoleDao.getByName("saved");
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getDescription(), actual.getDescription());
	}

	@Test
	public void testUpdate() {
		UserRole expected = null;
		try {
			expected = userRoleDao.getByName("forUpdate");
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		expected.setDescription("updated");
		
		UserRole actual = null;
		try {
			userRoleDao.update(expected);
			actual = userRoleDao.getByName("forUpdate");
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testRemove() {
		List<UserRole> expected = new ArrayList<UserRole>();
		
		UserRole expected1 = new UserRole();
		expected1.setId(1);
		expected1.setName("user");
		expected1.setDescription("user");
		
		expected.add(expected1);
		
		UserRole expected2 = new UserRole();
		expected2.setId(2);
		expected2.setName("manager");
		expected2.setDescription("manager");
		
		expected.add(expected2);
		
		UserRole expected3 = new UserRole();
		expected3.setId(3);
		expected3.setName("admin");
		expected3.setDescription("admin");
		
		expected.add(expected3);
		
		try {
			expected.add(userRoleDao.getByName("forUpdate"));
		} catch (SQLException e1) {
			fail("Fail with exception" + e1.getMessage());
		}
		
		List<UserRole> actual = null;
		
		try {
			userRoleDao.remove(userRoleDao.getByName("forDelete"));
			actual = userRoleDao.getList();
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

}
