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

import ua.nure.sidorovich.entety.RestType;

public class RestTypeDaoTest {
	
	static RestTypeDao restTypeDao = DAOFactory.getInstance().getRestTypeDAO();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		RestType forDelete = new RestType();
		forDelete.setName("forDelete");
		forDelete.setDescription("forDelete");
		
		RestType forUpdate = new RestType();
		forUpdate.setName("forUpdate");
		forUpdate.setDescription("forUpdate");
		
		restTypeDao.save(forDelete);
		restTypeDao.save(forUpdate);

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		restTypeDao.remove(restTypeDao.getByName("forDelete"));
		restTypeDao.remove(restTypeDao.getByName("forUpdate"));
	}
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		restTypeDao.remove(restTypeDao.getByName("saved"));
	}

	@Test
	public void testGetByIDLong() {
		RestType expected = new RestType();
		expected.setId(1);
		expected.setName("Rest");
		expected.setDescription("Rest");
		
		RestType actual = null;
		
		try {
			actual = restTypeDao.getByID(1);
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testGetList() {
		
		List<RestType> expected = new ArrayList<RestType>();
		
		RestType expected1 = new RestType();
		expected1.setId(1);
		expected1.setName("Rest");
		expected1.setDescription("Rest");
		
		expected.add(expected1);
		
		RestType expected2 = new RestType();
		expected2.setId(2);
		expected2.setName("Excortions");
		expected2.setDescription("Excortions");
		
		expected.add(expected2);
		
		RestType expected3 = new RestType();
		expected3.setId(3);
		expected3.setName("Shoping");
		expected3.setDescription("Shoping");
		
		expected.add(expected3);
		
		try {
			expected.add(restTypeDao.getByName("forDelete"));
			expected.add(restTypeDao.getByName("forUpdate"));
		} catch (SQLException e1) {
			fail("Fail with exception" + e1.getMessage());
		}
		
		List<RestType> actual = null;
		
		try {
			actual = restTypeDao.getList();
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testGetByNameString() {
		RestType expected = new RestType();
		expected.setId(1);
		expected.setName("Rest");
		expected.setDescription("Rest");
		
		RestType actual = null;
		
		try {
			actual = restTypeDao.getByName("Rest");
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testSave() {
		RestType expected = new RestType();
		expected.setName("saved");
		expected.setDescription("saved");
		
		RestType actual = null;
		
		try {
			restTypeDao.save(expected);
			actual = restTypeDao.getByName("saved");
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getDescription(), actual.getDescription());
	}

	@Test
	public void testUpdate() {
		RestType expected = null;
		try {
			expected = restTypeDao.getByName("forUpdate");
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		expected.setDescription("updated");
		
		RestType actual = null;
		try {
			restTypeDao.update(expected);
			actual = restTypeDao.getByName("forUpdate");
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testRemove() {
		List<RestType> expected = new ArrayList<RestType>();
		
		RestType expected1 = new RestType();
		expected1.setId(1);
		expected1.setName("Rest");
		expected1.setDescription("Rest");
		
		expected.add(expected1);
		
		RestType expected2 = new RestType();
		expected2.setId(2);
		expected2.setName("Excortions");
		expected2.setDescription("Excortions");
		
		expected.add(expected2);
		
		RestType expected3 = new RestType();
		expected3.setId(3);
		expected3.setName("Shoping");
		expected3.setDescription("Shoping");
		
		expected.add(expected3);
		
		try {
			expected.add(restTypeDao.getByName("forUpdate"));
		} catch (SQLException e1) {
			fail("Fail with exception" + e1.getMessage());
		}
		
		List<RestType> actual = null;
		
		try {
			restTypeDao.remove(restTypeDao.getByName("forDelete"));
			actual = restTypeDao.getList();
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}




}
