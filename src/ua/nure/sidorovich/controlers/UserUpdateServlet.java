package ua.nure.sidorovich.controlers;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.sidorovich.entety.User;
import ua.nure.sidorovich.security.LoginSecurity;
import ua.nure.sidorovich.security.PasswordCoder;
import ua.nure.sidorovich.service.UserService;
import ua.nure.sidorovich.service.UserServiceImpl;
import ua.nure.sidorovich.util.DataBaseException;
import ua.nure.sidorovich.validators.ValidatorByWrites;
import ua.nure.sidorovich.validators.ValidatorByWritesImpl;

@WebServlet("/updateUser")
public class UserUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = -85823902877989651L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.getSession();
		request.getRequestDispatcher("WEB-INF/UpdateUserForm.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		UserService userService = new UserServiceImpl();
		
		ValidatorByWrites validatorByWrites = new ValidatorByWritesImpl();
		
		User oldUser = (User)(session.getAttribute("user"));
		try {
			
			if(new LoginSecurity().isUserPasswordConfirm(oldUser, request.getParameter("oldPassword"))){
				User newUser = new User();
				
				newUser.setLogin(request.getParameter("updatedLogin"));
				newUser.setBirthday(Date.valueOf(request.getParameter("updatedBirthday")));
				newUser.setEmail(request.getParameter("updatedEmail"));
				newUser.setName(request.getParameter("updatedUserName"));
				newUser.setPhone(request.getParameter("updatedPhone"));
				newUser.setRole(oldUser.getRole());
				newUser.setId(oldUser.getId());
				
				if(request.getParameter("updatedPassword") == null 
						|| request.getParameter("updatedPassword").length() == 0){
					newUser.setPassword(oldUser.getPassword());
					
					if(userService.updateUser(newUser)){
						session.setAttribute("user", userService.getByID(newUser.getId()));
						response.sendRedirect("cabinet");
					} else {
						request.setAttribute("message", "Something wrong! Try again!");
						request.getRequestDispatcher("WEB-INF/UpdateUserForm.jsp").forward(request, response);
					}
					
				} else if(validatorByWrites.isPasswordValide(request.getParameter("updatedPassword"))){
					newUser.setPassword(PasswordCoder.hash(request.getParameter("updatedPassword")
							, PasswordCoder.SHA_256));
					
					if(userService.updateUser(newUser)){
						session.setAttribute("user", userService.getByID(newUser.getId()));
						response.sendRedirect("cabinet");
					} else {
						request.setAttribute("message", "Something wrong! Try again!");
						request.getRequestDispatcher("WEB-INF/UpdateUserForm.jsp").forward(request, response);
					}
					
				} else {
					request.setAttribute("message", "Something wrong! Try again!");
					request.getRequestDispatcher("WEB-INF/UpdateUserForm.jsp").forward(request, response);
				}
				
			} else {
				request.setAttribute("message", "Incorrect password! Try again!");
				request.getRequestDispatcher("WEB-INF/UpdateUserForm.jsp").forward(request, response);
			}
		
		} catch (NumberFormatException e) {
			request.setAttribute("message", "Incorrect password! Try again!");
			request.getRequestDispatcher("WEB-INF/UpdateUserForm.jsp").forward(request, response);			
		} catch (DataBaseException e) {
			response.sendError(404, e.getMessage());
		}
		
	}

}
