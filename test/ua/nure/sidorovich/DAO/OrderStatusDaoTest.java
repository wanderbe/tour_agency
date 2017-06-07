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

import ua.nure.sidorovich.entety.OrderStatus;

public class OrderStatusDaoTest {

	
	static OrderStatusDao orderStatusDao = DAOFactory.getInstance().getOrderStatusDAO();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OrderStatus forDelete = new OrderStatus();
		forDelete.setName("forDelete");
		forDelete.setDescription("forDelete");
		
		OrderStatus forUpdate = new OrderStatus();
		forUpdate.setName("forUpdate");
		forUpdate.setDescription("forUpdate");
		
		orderStatusDao.save(forDelete);
		orderStatusDao.save(forUpdate);

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
			orderStatusDao.remove(orderStatusDao.getByName("forDelete"));
			orderStatusDao.remove(orderStatusDao.getByName("forUpdate"));
	}
	
	@Before
	public void before() throws Exception {
	}
	
	@After
	public void after() throws Exception {
			orderStatusDao.remove(orderStatusDao.getByName("saved"));
	}

	@Test
	public void testGetByIDLong() {
		OrderStatus expected = new OrderStatus();
		expected.setId(1);
		expected.setName("registered");
		expected.setDescription("registered");
		
		OrderStatus actual = null;
		
		try {
			actual = orderStatusDao.getByID(1);
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testGetList() {
		
		List<OrderStatus> expected = new ArrayList<OrderStatus>();
		
		OrderStatus expected1 = new OrderStatus();
		expected1.setId(1);
		expected1.setName("registered");
		expected1.setDescription("registered");
		
		expected.add(expected1);
		
		OrderStatus expected2 = new OrderStatus();
		expected2.setId(2);
		expected2.setName("paid");
		expected2.setDescription("paid");
		
		expected.add(expected2);
		
		OrderStatus expected3 = new OrderStatus();
		expected3.setId(3);
		expected3.setName("canceled");
		expected3.setDescription("canceled");
		
		expected.add(expected3);
		
		try {
			expected.add(orderStatusDao.getByName("forDelete"));
			expected.add(orderStatusDao.getByName("forUpdate"));
		} catch (SQLException e1) {
			fail("Fail with exception" + e1.getMessage());
		}
		
		List<OrderStatus> actual = null;
		
		try {
			actual = orderStatusDao.getList();
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testGetByNameString() {
		OrderStatus expected = new OrderStatus();
		expected.setId(1);
		expected.setName("registered");
		expected.setDescription("registered");
		
		OrderStatus actual = null;
		
		try {
			actual = orderStatusDao.getByName("registered");
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testSave() {
		OrderStatus expected = new OrderStatus();
		expected.setName("saved");
		expected.setDescription("saved");
		
		OrderStatus actual = null;
		
		try {
			orderStatusDao.save(expected);
			actual = orderStatusDao.getByName("saved");
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getDescription(), actual.getDescription());
	}

	@Test
	public void testUpdate() {
		OrderStatus expected = null;
		try {
			expected = orderStatusDao.getByName("forUpdate");
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		expected.setDescription("updated");
		
		OrderStatus actual = null;
		try {
			orderStatusDao.update(expected);
			actual = orderStatusDao.getByName("forUpdate");
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testRemove() {
		List<OrderStatus> expected = new ArrayList<OrderStatus>();
		
		OrderStatus expected1 = new OrderStatus();
		expected1.setId(1);
		expected1.setName("registered");
		expected1.setDescription("registered");
		
		expected.add(expected1);
		
		OrderStatus expected2 = new OrderStatus();
		expected2.setId(2);
		expected2.setName("paid");
		expected2.setDescription("paid");
		
		expected.add(expected2);
		
		OrderStatus expected3 = new OrderStatus();
		expected3.setId(3);
		expected3.setName("canceled");
		expected3.setDescription("canceled");
		
		expected.add(expected3);
		
		try {
			expected.add(orderStatusDao.getByName("forUpdate"));
		} catch (SQLException e1) {
			fail("Fail with exception" + e1.getMessage());
		}
		
		List<OrderStatus> actual = null;
		
		try {
			orderStatusDao.remove(orderStatusDao.getByName("forDelete"));
			actual = orderStatusDao.getList();
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}
}
