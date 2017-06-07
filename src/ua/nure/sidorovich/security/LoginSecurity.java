package ua.nure.sidorovich.security;

import ua.nure.sidorovich.entety.User;
import ua.nure.sidorovich.service.UserService;
import ua.nure.sidorovich.service.UserServiceImpl;
import ua.nure.sidorovich.util.DataBaseException;

public class LoginSecurity {
	
	private UserService userService = new UserServiceImpl();

	public User chekPassword(String login, String password) throws DataBaseException{
		
		String hPassword = PasswordCoder.hash(password, PasswordCoder.SHA_256);
		
		try {
			User user = userService.getByLogin(login);
			if(user !=null && login != null && password != null && login.equals(user.getLogin()) 
					&& hPassword.equals(user.getPassword())){
				return user;
			}
		} catch (DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return null;
	}
	
	public boolean isUserPasswordConfirm(User user, String password){
		
		String hPassword = PasswordCoder.hash(password, PasswordCoder.SHA_256);
		
		try {
			User resultUser = userService.getByID(user.getId());
			
			if(resultUser != null && password != null && hPassword.equals(resultUser.getPassword())){
				return true;
			}
			
		} catch (DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
