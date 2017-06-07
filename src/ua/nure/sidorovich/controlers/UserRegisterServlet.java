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
import ua.nure.sidorovich.service.UserRoleService;
import ua.nure.sidorovich.service.UserRoleServiceImpl;
import ua.nure.sidorovich.service.UserService;
import ua.nure.sidorovich.service.UserServiceImpl;
import ua.nure.sidorovich.util.DataBaseException;
import ua.nure.sidorovich.validators.ValidatorByExist;
import ua.nure.sidorovich.validators.ValidatorByExistImpl;


@WebServlet("/register")
public class UserRegisterServlet extends HttpServlet {

	private static final long serialVersionUID = -5880495474176218407L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		request.getRequestDispatcher("WEB-INF/UserRegister.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		UserService userService = new UserServiceImpl();
		UserRoleService userRoleService = new UserRoleServiceImpl();
		ValidatorByExist validatorByExist = new ValidatorByExistImpl();
		
		User user = new User();	
		
		try {
						
			if(validatorByExist.isThisLoginEcxist(request.getParameter("login")) 
					|| validatorByExist.isThisEmailEcxist(request.getParameter("email"))){
				
				request.setAttribute("message", "User with this login or email olready ecxist, try another");
				
				request.setAttribute("name", request.getParameter("name"));
				request.setAttribute("birthday", request.getParameter("birthday"));
				request.setAttribute("email", request.getParameter("email"));
				request.setAttribute("phone", request.getParameter("phone"));
				request.setAttribute("login", request.getParameter("login"));
				
				request.getRequestDispatcher("WEB-INF/UserRegister.jsp").forward(request, response);
				
				
			} else {
				user.setName(request.getParameter("name"));
				user.setBirthday(Date.valueOf(request.getParameter("birthday")));
				user.setEmail(request.getParameter("email"));
				user.setPhone(request.getParameter("phone"));
				user.setLogin(request.getParameter("login"));
				user.setPassword(request.getParameter("password"));
				user.setRole(userRoleService.getByName("user"));
				
				user = userService.saveAndReturnSavedUser(user);
				
				if(user == null){
					
					request.setAttribute("message", "User incorrect");
					
					request.setAttribute("name", request.getParameter("name"));
					request.setAttribute("birthday", Date.valueOf(request.getParameter("birthday")));
					request.setAttribute("email", request.getParameter("email"));
					request.setAttribute("phone", request.getParameter("phone"));
					request.setAttribute("login", request.getParameter("login"));
					
					request.getRequestDispatcher("WEB-INF/UserRegister.jsp").forward(request, response);
				} else {
					session.setAttribute("user", user);
					
					if(session.getAttribute("lastLocation") != null){
						response.sendRedirect((String)session.getAttribute("lastLocation"));
					} else {
						response.sendRedirect("tour");
					}
				}				
			}
			
		} catch (IllegalArgumentException e) {
			request.setAttribute("message", "User incorrect");
			
			request.setAttribute("name", request.getParameter("name"));
			request.setAttribute("email", request.getParameter("email"));
			request.setAttribute("phone", request.getParameter("phone"));
			request.setAttribute("login", request.getParameter("login"));
			
			request.getRequestDispatcher("WEB-INF/UserRegister.jsp").forward(request, response);
		} catch (DataBaseException e) {
			response.sendError(404, e.getMessage());
		}
				
	}

}
