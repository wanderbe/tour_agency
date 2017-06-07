package ua.nure.sidorovich.controlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class UserLogoutServlet extends HttpServlet {

	private static final long serialVersionUID = 8124141131832201185L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session = request.getSession();

			if(session.getAttribute("user") != null){
				session.removeAttribute("user");
			} 
			
			if(session.getAttribute("lastLocation") != null){
				session.setAttribute("lastLocation", null);
				response.sendRedirect("tours");
			} else {
				response.sendRedirect("tours");
			}
			
			try{
				session.invalidate();
			} catch(IllegalStateException e){
				//ignore
			}
	}

}
