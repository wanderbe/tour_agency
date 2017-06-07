package ua.nure.sidorovich.validators;

import java.sql.Date;
import java.util.regex.Pattern;

import ua.nure.sidorovich.entety.Hotel;
import ua.nure.sidorovich.entety.RestType;
import ua.nure.sidorovich.entety.Tour;
import ua.nure.sidorovich.entety.User;
import ua.nure.sidorovich.entety.UserRole;
import ua.nure.sidorovich.service.HotelService;
import ua.nure.sidorovich.service.HotelServiceImpl;
import ua.nure.sidorovich.service.RestTypeService;
import ua.nure.sidorovich.service.RestTypeServiceImpl;
import ua.nure.sidorovich.service.UserRoleService;
import ua.nure.sidorovich.service.UserRoleServiceImpl;
import ua.nure.sidorovich.util.DataBaseException;

public class ValidatorByWritesImpl implements ValidatorByWrites{
	
	private static final String REGX_USER_MAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*"
			+ "@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private static final String REGX_USER_LOGIN = "^[a-zA-Zà-ÿÀ-ß0-9_-]{3,15}$";
	
	private static final String REGX_USER_NAME = "^[a-zA-Zà-ÿÀ-ß0-9_-]{3,15}$";
	
	private static final String REGX_USER_PASSWORD = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$";
//	At least 8 chars
//	Contains at least one digit
//	Contains at least one lower alpha char and one upper alpha char
//	Contains at least one char within a set of special chars (@#%$^ etc.)
//	Does not contain space, tab, etc.
	
	private static final String REGX_TOUR_NAME = "^[a-zA-Zà-ÿÀ-ß0-9_-[\\s]]{3,32}$";

	@Override
	public boolean isUserEntetyValid(User user) {
		if(user.getBirthday() == null || !(user.getBirthday() instanceof Date) 
				|| user.getEmail() == null || user.getEmail().length() == 0
				|| user.getLogin() == null || user.getLogin().length() == 0
				|| user.getName() == null || user.getName().length() == 0
				|| user.getPassword() == null || user.getPassword().length() == 0
				|| user.getPhone() == null || user.getPhone().length() == 0
				|| user.getRole() == null || !(user.getRole() instanceof UserRole)){
			return false;
		} else if(!isBirthdayValide(user.getBirthday())){
			return false; //
		} else if(!isEmailUserValide(user.getEmail())){
			return false;
		} else if(!isLoginUserValide(user.getLogin())){
			return false;
		} else if(!isNameUserValide(user.getName())){
			return false;
		} else if(!isPasswordValide(user.getPassword())){
			return false;
		} else if(!isRoleValide(user.getRole())){
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public boolean isUserEntetyValidIgnorePassword(User user) {
		if(user.getBirthday() == null || !(user.getBirthday() instanceof Date) 
				|| user.getEmail() == null || user.getEmail().length() == 0
				|| user.getLogin() == null || user.getLogin().length() == 0
				|| user.getName() == null || user.getName().length() == 0
				|| user.getPassword() == null || user.getPassword().length() == 0
				|| user.getPhone() == null || user.getPhone().length() == 0
				|| user.getRole() == null || !(user.getRole() instanceof UserRole)){
			return false;
		} else if(!isBirthdayValide(user.getBirthday())){
			return false; //
		} else if(!isEmailUserValide(user.getEmail())){
			return false;
		} else if(!isLoginUserValide(user.getLogin())){
			return false;
		} else if(!isNameUserValide(user.getName())){
			return false;
		} else if(!isRoleValide(user.getRole())){
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean isBirthdayValide(Date birthday) {
		return birthday.getTime()>(18*365*24*60*60*1000);
	}

	@Override
	public boolean isRoleValide(UserRole role) {
		UserRoleService userRoleService = new UserRoleServiceImpl();
		try {
			if(userRoleService.getByID(role.getId()) == null){
				return false;
			} else {
				return true;
			}
		} catch (DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean isPasswordValide(String password) {
		return Pattern.matches(REGX_USER_PASSWORD, password);
	}

	@Override
	public boolean isNameUserValide(String name) {
		return Pattern.matches(REGX_USER_NAME, name);
	}

	@Override
	public boolean isLoginUserValide(String login) {
		return Pattern.matches(REGX_USER_LOGIN, login);
	}

	@Override
	public boolean isEmailUserValide(String email) {
		return Pattern.matches(REGX_USER_MAIL, email);
	}
	

	@Override
	public boolean isTourEntetyValid(Tour tour) {
		if(tour.getRestType() == null || !(tour.getRestType() instanceof RestType)
				|| tour.getPrice() == 0 || tour.getPlaces() == 0 
				|| tour.getName() == null || tour.getName().length() == 0
				|| tour.getHotel() == null || !(tour.getHotel() instanceof Hotel)){
			return false;
		} else if(!isRestTypeValid(tour.getRestType())){
			return false;
		} else if(!isNameTourValid(tour.getName())){
			return false;
		} else if(!isHotelValid(tour.getHotel())){
			return false;
		} else {
			return true;
		}
	}
	
	
	@Override
	public boolean isHotelValid(Hotel hotel) {
		HotelService HotelService = new HotelServiceImpl();
		try {
			if(HotelService.getByID(hotel.getId()) == null){
				return false;
			} else {
				return true;
			}
		} catch (DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean isNameTourValid(String name) {
		return Pattern.matches(REGX_TOUR_NAME, name);
	}

	@Override
	public boolean isRestTypeValid(RestType restType) {
		RestTypeService restTypeService = new RestTypeServiceImpl();
		try {
			if(restTypeService.getByID(restType.getId()) == null){
				return false;
			} else {
				return true;
			}
		} catch (DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}
