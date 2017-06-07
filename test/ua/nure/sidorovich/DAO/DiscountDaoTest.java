package ua.nure.sidorovich.DAO;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ua.nure.sidorovich.entety.Discount;

public class DiscountDaoTest {
	
	
	static DiscountDao discountDao = DAOFactory.getInstance().getDiscountDAO();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Before
	public void before() throws Exception {
	}
	
	@After
	public void after() throws Exception {
	}

	@Test
	public void testGetByIDLong() {
		Discount expected = new Discount();
		expected.setStep(2);
		expected.setMaxPercent(5);
		
		Discount actual = null;
		
		try {
			actual = discountDao.getByID(1);
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected.getMaxPercent(), actual.getMaxPercent());
		assertEquals(expected.getStep(), actual.getStep());
	}

	@Test
	public void testGetList() {
		
		List<Discount> actual = null;
		
		try {
			actual = discountDao.getList();
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		
		assertTrue(actual.size() > 0);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetByNameString() {
		try {
			discountDao.getByName("");
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
	}

	@Test
	public void testSave() {
		Discount expected = new Discount();
		expected.setRegisterTime(new Timestamp(System.currentTimeMillis()));
		expected.setStep(2);
		expected.setMaxPercent(10);
		
		Discount actual = null;
		
		try {
			discountDao.save(expected);
			actual = discountDao.getLatestDiscont();
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected.getMaxPercent(), actual.getMaxPercent());
		assertEquals(expected.getStep(), actual.getStep());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testUpdate() {
		try {
			discountDao.update(null);
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemove() {
		try {
			discountDao.remove(null);
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
	}

	@Test
	public void testGetByRegisterTime() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		Discount expected = new Discount();
		expected.setRegisterTime(timestamp);
		expected.setStep(2);
		expected.setMaxPercent(10);
		
		Discount actual = null;
		
		try {
			discountDao.save(expected);
			actual = discountDao.getByRegisterTime(timestamp).remove(0);
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected.getMaxPercent(), actual.getMaxPercent());
		assertEquals(expected.getStep(), actual.getStep());
	}

	@Test
	public void testGetLatestDiscont() {
		Discount expected = new Discount();
		expected.setRegisterTime(new Timestamp(System.currentTimeMillis()));
		expected.setStep(2);
		expected.setMaxPercent(10);
		
		Discount actual = null;
		
		try {
			discountDao.save(expected);
			actual = discountDao.getLatestDiscont();
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected.getMaxPercent(), actual.getMaxPercent());
		assertEquals(expected.getStep(), actual.getStep());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetByName() {
		try {
			discountDao.getByName(null);
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
	}

}
