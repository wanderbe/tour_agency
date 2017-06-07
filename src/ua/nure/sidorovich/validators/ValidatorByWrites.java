package ua.nure.sidorovich.validators;

import java.sql.Date;

import ua.nure.sidorovich.entety.Hotel;
import ua.nure.sidorovich.entety.RestType;
import ua.nure.sidorovich.entety.Tour;
import ua.nure.sidorovich.entety.User;
import ua.nure.sidorovich.entety.UserRole;

public interface ValidatorByWrites {

	boolean isUserEntetyValid(User user);

	boolean isUserEntetyValidIgnorePassword(User user);

	boolean isRestTypeValid(RestType restType);

	boolean isNameTourValid(String name);

	boolean isHotelValid(Hotel hotel);

	boolean isTourEntetyValid(Tour tour);

	boolean isEmailUserValide(String email);

	boolean isLoginUserValide(String login);

	boolean isNameUserValide(String name);

	boolean isPasswordValide(String password);

	boolean isRoleValide(UserRole role);

	boolean isBirthdayValide(Date birthday);

}
