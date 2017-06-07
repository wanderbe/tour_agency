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

import ua.nure.sidorovich.entety.HotelType;

public class HotelTypeDaoTest {
	
	static HotelTypeDao hotelTypeDao = DAOFactory.getInstance().getHotelTypeDAO();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		HotelType forDelete = new HotelType();
		forDelete.setHotelClass(100);
		forDelete.setDescription("100");
		hotelTypeDao.save(forDelete);
		
		HotelType forUpdate = new HotelType();
		forUpdate.setHotelClass(101);
		forUpdate.setDescription("forUpdate");
		hotelTypeDao.save(forUpdate);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		hotelTypeDao.remove(hotelTypeDao.getByHotelClass(101));
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		hotelTypeDao.remove(hotelTypeDao.getByHotelClass(6));
	}

	@Test
	public void testGetByIDLong() {
		HotelType expected = new HotelType();
		expected.setId(1);
		expected.setHotelClass(1);
		expected.setDescription("1");
		
		HotelType actual = null;
		
		try {
			actual = hotelTypeDao.getByID(1);
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testGetList() {
		List<HotelType> expected = new ArrayList<HotelType>();
		
		for(int i = 1; i < 6; i++){
			HotelType temp = new HotelType();
			temp.setId(i);
			temp.setHotelClass(i);
			temp.setDescription(i + "");
			
			expected.add(temp);
		}
		
		try {
			expected.add(hotelTypeDao.getByHotelClass(100));
			expected.add(hotelTypeDao.getByHotelClass(101));
		} catch (SQLException e1) {
			fail("Fail with exception" + e1.getMessage());
		}
		
		List<HotelType> actual = null;
		
		try {
			actual = hotelTypeDao.getList();
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testGetByHotelClass() {
		HotelType expected = new HotelType();
		expected.setId(1);
		expected.setHotelClass(1);
		expected.setDescription("1");
		
		HotelType actual = null;
		
		try {
			actual = hotelTypeDao.getByHotelClass(1);
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetByName() {
		try {
			hotelTypeDao.getByName("1");
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
	}

	@Test
	public void testSave() {
		HotelType expected = new HotelType();
		expected.setHotelClass(6);
		expected.setDescription("6");
		
		HotelType actual = null;
		try {
			hotelTypeDao.save(expected);
			actual = hotelTypeDao.getByHotelClass(6);
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected.getDescription(), actual.getDescription());
		assertEquals(expected.getHotelClass(), actual.getHotelClass());
	}

	@Test
	public void testUpdate() {
		HotelType expected = null;
		try {
			expected = hotelTypeDao.getByHotelClass(101);
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		expected.setDescription("updated");
		
		HotelType actual = null;
		try {
			hotelTypeDao.update(expected);
			actual = hotelTypeDao.getByHotelClass(101);
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testRemove() {List<HotelType> expected = new ArrayList<HotelType>();
	
		for(int i = 1; i < 6; i++){
			HotelType temp = new HotelType();
			temp.setId(i);
			temp.setHotelClass(i);
			temp.setDescription(i + "");
			
			expected.add(temp);
		}
		
		try {
			expected.add(hotelTypeDao.getByHotelClass(101));
		} catch (SQLException e1) {
			fail("Fail with exception" + e1.getMessage());
		}
		
			List<HotelType> actual = null;
		try {
			hotelTypeDao.remove(hotelTypeDao.getByHotelClass(100));
			actual = hotelTypeDao.getList();
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
			
	}

}
