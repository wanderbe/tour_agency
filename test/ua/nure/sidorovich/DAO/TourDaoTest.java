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
import ua.nure.sidorovich.entety.RestType;
import ua.nure.sidorovich.entety.Tour;

public class TourDaoTest {

	static TourDao tourDao = DAOFactory.getInstance().getTourDAO();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Tour forDelete = new Tour();
		forDelete.setName("forDelete");
		forDelete.setDescription("forDelete");
		
		RestType restType = new RestType();
		
		restType.setId(1);
		restType.setName("Rest");
		restType.setDescription("Rest");
		
		forDelete.setRestType(restType);
		forDelete.setPlaces(1);
		
		Hotel hotel = new Hotel();
		hotel.setId(1);
		hotel.setName("Hilton");
		hotel.setDescription("Hilton");
		
		HotelType hotelType = new HotelType();
		hotelType.setId(4);
		hotelType.setDescription("4");
		hotelType.setHotelClass(4);
		
		hotel.setHotelType(hotelType);
		forDelete.setHotel(hotel);
		forDelete.setPrice(100);
		forDelete.setItHot(false);
				
		Tour forUpdate = new Tour();
		forUpdate.setName("forUpdate");
		forUpdate.setDescription("forUpdate");
		forUpdate.setRestType(restType);
		forUpdate.setPlaces(1);
		forUpdate.setHotel(hotel);
		forUpdate.setPrice(100);
		forUpdate.setItHot(false);
		
		tourDao.save(forDelete);
		tourDao.save(forUpdate);

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
			tourDao.remove(tourDao.getByName("forDelete"));
			tourDao.remove(tourDao.getByName("forUpdate"));
	}
	
	@Before
	public void before() throws Exception {
	}
	
	@After
	public void after() throws Exception {
			tourDao.remove(tourDao.getByName("saved"));
	}

	@Test
	public void testGetByIDLong() {
		Tour expected = new Tour();
		expected.setId(1);
		expected.setName("Turke");
		expected.setDescription("Turke");
		
		RestType restType = new RestType();
		
		restType.setId(1);
		restType.setName("Rest");
		restType.setDescription("Rest");
		
		expected.setRestType(restType);
		expected.setPlaces(1);
		
		Hotel hotel = new Hotel();
		hotel.setId(1);
		hotel.setName("Hilton");
		hotel.setDescription("Hilton");
		
		HotelType hotelType = new HotelType();
		hotelType.setId(4);
		hotelType.setDescription("4");
		hotelType.setHotelClass(4);
		
		hotel.setHotelType(hotelType);
		expected.setHotel(hotel);
		expected.setPrice(100);
		expected.setItHot(true);
		
		Tour actual = null;
		
		try {
			actual = tourDao.getByID(1);
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testGetList() {
		
		List<Tour> expected = new ArrayList<Tour>();
		
		Tour expected1 = new Tour();
		expected1.setId(1);
		expected1.setName("Turke");
		expected1.setDescription("Turke");
		
		RestType restType = new RestType();
		
		restType.setId(1);
		restType.setName("Rest");
		restType.setDescription("Rest");
		
		expected1.setRestType(restType);
		expected1.setPlaces(1);
		
		Hotel hotel = new Hotel();
		hotel.setId(1);
		hotel.setName("Hilton");
		hotel.setDescription("Hilton");
		
		HotelType hotelType = new HotelType();
		hotelType.setId(4);
		hotelType.setDescription("4");
		hotelType.setHotelClass(4);
		
		hotel.setHotelType(hotelType);
		expected1.setHotel(hotel);
		expected1.setPrice(100);
		expected1.setItHot(true);
		
		expected.add(expected1);
		
		Tour expected2 = new Tour();
		expected2.setId(2);
		expected2.setName("Egiped");
		expected2.setDescription("Egiped");
		
		RestType restType2 = new RestType();
		
		restType2.setId(1);
		restType2.setName("Rest");
		restType2.setDescription("Rest");
		
		expected2.setRestType(restType2);
		expected2.setPlaces(1);
		
		Hotel hotel2 = new Hotel();
		hotel2.setId(2);
		hotel2.setName("Wilson");
		hotel2.setDescription("Wilson");
		
		HotelType hotelType2 = new HotelType();
		hotelType2.setId(2);
		hotelType2.setDescription("2");
		hotelType2.setHotelClass(2);
		
		hotel2.setHotelType(hotelType2);
		expected2.setHotel(hotel2);
		expected2.setPrice(234);
		expected2.setItHot(false);
		
		expected.add(expected2);
		
		try {
			expected.add(tourDao.getByName("forDelete"));
			expected.add(tourDao.getByName("forUpdate"));
		} catch (SQLException e1) {
			fail("Fail with exception" + e1.getMessage());
		}
		
		List<Tour> actual = null;
		
		try {
			actual = tourDao.getList();
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testGetByNameString() {
		Tour expected = new Tour();
		expected.setId(1);
		expected.setName("Turke");
		expected.setDescription("Turke");
		
		RestType restType = new RestType();
		
		restType.setId(1);
		restType.setName("Rest");
		restType.setDescription("Rest");
		
		expected.setRestType(restType);
		expected.setPlaces(1);
		
		Hotel hotel = new Hotel();
		hotel.setId(1);
		hotel.setName("Hilton");
		hotel.setDescription("Hilton");
		
		HotelType hotelType = new HotelType();
		hotelType.setId(4);
		hotelType.setDescription("4");
		hotelType.setHotelClass(4);
		
		hotel.setHotelType(hotelType);
		expected.setHotel(hotel);
		expected.setPrice(100);
		expected.setItHot(true);
		
		Tour actual = null;
		
		try {
			actual = tourDao.getByName("Turke");
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testSave() {
		Tour expected = new Tour();
		expected.setName("saved");
		expected.setDescription("saved");
		
		RestType restType = new RestType();
		
		restType.setId(1);
		restType.setName("Rest");
		restType.setDescription("Rest");
		
		expected.setRestType(restType);
		expected.setPlaces(1);
		
		Hotel hotel = new Hotel();
		hotel.setId(1);
		hotel.setName("Hilton");
		hotel.setDescription("Hilton");
		
		HotelType hotelType = new HotelType();
		hotelType.setId(4);
		hotelType.setDescription("4");
		hotelType.setHotelClass(4);
		
		hotel.setHotelType(hotelType);
		expected.setHotel(hotel);
		expected.setPrice(100);
		expected.setItHot(true);
		
		Tour actual = null;
		
		try {
			tourDao.save(expected);
			actual = tourDao.getByName("saved");
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getDescription(), actual.getDescription());
	}

	@Test
	public void testUpdate() {
		Tour expected = null;
		try {
			expected = tourDao.getByName("forUpdate");
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		expected.setDescription("updated");
		
		Tour actual = null;
		try {
			tourDao.update(expected);
			actual = tourDao.getByName("forUpdate");
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testRemove() {
		List<Tour> expected = new ArrayList<Tour>();
		
		Tour expected1 = new Tour();
		expected1.setId(1);
		expected1.setName("Turke");
		expected1.setDescription("Turke");
		
		RestType restType = new RestType();
		
		restType.setId(1);
		restType.setName("Rest");
		restType.setDescription("Rest");
		
		expected1.setRestType(restType);
		expected1.setPlaces(1);
		
		Hotel hotel = new Hotel();
		hotel.setId(1);
		hotel.setName("Hilton");
		hotel.setDescription("Hilton");
		
		HotelType hotelType = new HotelType();
		hotelType.setId(4);
		hotelType.setDescription("4");
		hotelType.setHotelClass(4);
		
		hotel.setHotelType(hotelType);
		expected1.setHotel(hotel);
		expected1.setPrice(100);
		expected1.setItHot(true);
		
		expected.add(expected1);
		
		Tour expected2 = new Tour();
		expected2.setId(2);
		expected2.setName("Egiped");
		expected2.setDescription("Egiped");
		
		RestType restType2 = new RestType();
		
		restType2.setId(1);
		restType2.setName("Rest");
		restType2.setDescription("Rest");
		
		expected2.setRestType(restType2);
		expected2.setPlaces(1);
		
		Hotel hotel2 = new Hotel();
		hotel2.setId(2);
		hotel2.setName("Wilson");
		hotel2.setDescription("Wilson");
		
		HotelType hotelType2 = new HotelType();
		hotelType2.setId(2);
		hotelType2.setDescription("2");
		hotelType2.setHotelClass(2);
		
		hotel2.setHotelType(hotelType2);
		expected2.setHotel(hotel2);
		expected2.setPrice(234);
		expected2.setItHot(false);
		
		expected.add(expected2);
		
		try {
			expected.add(tourDao.getByName("forUpdate"));
		} catch (SQLException e1) {
			fail("Fail with exception" + e1.getMessage());
		}
		
		List<Tour> actual = null;
		
		try {
			tourDao.remove(tourDao.getByName("forDelete"));
			actual = tourDao.getList();
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testFindByParametrs() {
		Tour expected = new Tour();
		expected.setId(1);
		expected.setName("Turke");
		expected.setDescription("Turke");
		
		RestType restType = new RestType();
		
		restType.setId(1);
		restType.setName("Rest");
		restType.setDescription("Rest");
		
		expected.setRestType(restType);
		expected.setPlaces(1);
		
		Hotel hotel = new Hotel();
		hotel.setId(1);
		hotel.setName("Hilton");
		hotel.setDescription("Hilton");
		
		HotelType hotelType = new HotelType();
		hotelType.setId(4);
		hotelType.setDescription("4");
		hotelType.setHotelClass(4);
		
		hotel.setHotelType(hotelType);
		expected.setHotel(hotel);
		expected.setPrice(100);
		expected.setItHot(true);
		
		Tour actual = null;
		
		try {
			actual = tourDao.findByParametrs(1, 99, 101, 1, 4).remove(0);
		} catch (SQLException e) {
			fail("Fail with exception" + e.getMessage());
		}
		
		assertEquals(expected, actual);
	}

}
