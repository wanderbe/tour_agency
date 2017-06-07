package ua.nure.sidorovich.controlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.sidorovich.entety.User;
import ua.nure.sidorovich.security.LoginSecurity;
import ua.nure.sidorovich.util.DataBaseException;


@WebServlet("/login")
public class UserLoginServlet extends HttpServlet {

	private static final long serialVersionUID = 8124141131832201185L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		if(session.getAttribute("user") == null){
			request.getRequestDispatcher("WEB-INF/Login.jsp").forward(request, response);
			} else{
				response.sendRedirect("tours");
			}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		
		User user = null;
		try {
			user = new LoginSecurity().chekPassword(login, password);
		
			if(user == null){
				request.setAttribute("messsage", "incorrect login or password please try again");
				request.setAttribute("login", login);
				request.getRequestDispatcher("WEB-INF/Login.jsp").forward(request, response);
			} else {
				if(session.getAttribute("user") == null){
					session.setAttribute("user", user);
					request.setAttribute("message", "Hello ");
					
					if(session.getAttribute("lastLocation") != null){
						response.sendRedirect((String)session.getAttribute("lastLocation"));
					} else {
						response.sendRedirect("tours");
					}
					
				} else {
					response.sendRedirect("logout");
				}
			}	
		} catch (DataBaseException e) {
			response.sendError(404, e.getMessage());
		}
	}
}
