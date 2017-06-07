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

import ua.nure.sidorovich.entety.Hotel;
import ua.nure.sidorovich.entety.HotelType;

public class HotelDaoTest {

	
	static HotelDao hotelDao = DAOFactory.getInstance().getHotelDAO();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Hotel forDelete = new Hotel();
		forDelete.setName("forDelete");
		forDelete.setDescription("forDelete");
		
		HotelType hotelType = new HotelType();
		hotelType.setId(1);
		hotelType.setDescription("1");
		hotelType.setHotelClass(1);
		
		forDelete.setHotelType(hotelType);
		
		Hotel forUpdate = new Hotel();
		forUpdate.setName("forUpdate");
		forUpdate.setDescription("forUpdate");
		forUpdate.setHotelType(hotelType);
		
		hotelDao.save(forDelete);
		hotelDao.save(forUpdate);

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
			hotelDao.remove(hotelDao.getByName("forDelete"));
			hotelDao.remove(hotelDao.getByName("forUpdate"));
	}
	
	@Before
	public void before() throws Exception {
	}
	
	@After
	public void after() throws Exception {
			hotelDao.remove(hotelDao.getByName("saved"));
	}

	@Test
	public void testGetByIDLong() {
		Hotel expected = new Hotel();
		expected.setId(1);
		expected.setName("Hilton");
		expected.setDescription("Hilton");
		
		HotelType hotelType = new HotelType();
		hotelType.setId(4);
		hotelType.setDescription("4");
		hotelType.setHotelClass(4);
		
		expected.setHotelType(hotelType);
		
		Hotel actual = null;
		
		try {
			actual = hotelDao.getByID(1);
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testGetList() {
		
		List<Hotel> expected = new ArrayList<Hotel>();
		
		Hotel expected1 = new Hotel();
		expected1.setId(1);
		expected1.setName("Hilton");
		expected1.setDescription("Hilton");
		
		HotelType hotelType1 = new HotelType();
		hotelType1.setId(4);
		hotelType1.setDescription("4");
		hotelType1.setHotelClass(4);
		
		expected1.setHotelType(hotelType1);
		
		expected.add(expected1);
		
		Hotel expected2 = new Hotel();
		expected2.setId(2);
		expected2.setName("Wilson");
		expected2.setDescription("Wilson");
		
		HotelType hotelType2 = new HotelType();
		hotelType2.setId(2);
		hotelType2.setDescription("2");
		hotelType2.setHotelClass(2);
		
		expected2.setHotelType(hotelType2);
		
		expected.add(expected2);
		
		
		try {
			expected.add(hotelDao.getByName("forDelete"));
			expected.add(hotelDao.getByName("forUpdate"));
		} catch (SQLException e1) {
			fail("Fail with exception" + e1.getMessage());
		}
		
		List<Hotel> actual = null;
		
		try {
			actual = hotelDao.getList();
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testGetByNameString() {
		Hotel expected = new Hotel();
		expected.setId(1);
		expected.setName("Hilton");
		expected.setDescription("Hilton");
		
		HotelType hotelType = new HotelType();
		hotelType.setId(4);
		hotelType.setDescription("4");
		hotelType.setHotelClass(4);
		
		expected.setHotelType(hotelType);
		
		Hotel actual = null;
		
		try {
			actual = hotelDao.getByName("Hilton");
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testSave() {
		Hotel expected = new Hotel();
		expected.setName("saved");
		expected.setDescription("saved");
		
		HotelType hotelType = new HotelType();
		hotelType.setId(4);
		hotelType.setDescription("4");
		hotelType.setHotelClass(4);
		
		expected.setHotelType(hotelType);
		
		Hotel actual = null;
		
		try {
			hotelDao.save(expected);
			actual = hotelDao.getByName("saved");
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getDescription(), actual.getDescription());
	}

	@Test
	public void testUpdate() {
		Hotel expected = null;
		try {
			expected = hotelDao.getByName("forUpdate");
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		expected.setDescription("updated");
		
		Hotel actual = null;
		try {
			hotelDao.update(expected);
			actual = hotelDao.getByName("forUpdate");
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testRemove() {
		List<Hotel> expected = new ArrayList<Hotel>();
		
		Hotel expected1 = new Hotel();
		expected1.setId(1);
		expected1.setName("Hilton");
		expected1.setDescription("Hilton");
		
		HotelType hotelType1 = new HotelType();
		hotelType1.setId(4);
		hotelType1.setDescription("4");
		hotelType1.setHotelClass(4);
		
		expected1.setHotelType(hotelType1);
		
		expected.add(expected1);
		
		Hotel expected2 = new Hotel();
		expected2.setId(2);
		expected2.setName("Wilson");
		expected2.setDescription("Wilson");
		
		HotelType hotelType2 = new HotelType();
		hotelType2.setId(2);
		hotelType2.setDescription("2");
		hotelType2.setHotelClass(2);
		
		expected2.setHotelType(hotelType2);
		
		expected.add(expected2);
		
		try {
			expected.add(hotelDao.getByName("forUpdate"));
		} catch (SQLException e1) {
			fail("Fail with exception" + e1.getMessage());
		}
		
		List<Hotel> actual = null;
		
		try {
			hotelDao.remove(hotelDao.getByName("forDelete"));
			actual = hotelDao.getList();
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}
}
