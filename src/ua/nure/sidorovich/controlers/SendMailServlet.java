package ua.nure.sidorovich.controlers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.sidorovich.service.UserService;
import ua.nure.sidorovich.service.UserServiceImpl;
import ua.nure.sidorovich.util.MailSender;


@WebServlet("/sendMail")
public class SendMailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("tours");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserService userService = new UserServiceImpl();
		
		HttpSession session = request.getSession();
		
		try {
            MailSender.sendMail(
            		userService.getByID(Long.parseLong(request.getParameter("user_id_for_message"))).getEmail()
            		, request.getParameter("subject")
            		, request.getParameter("message"));
            
            if(session.getAttribute("lastLocation") != null){
				response.sendRedirect((String)session.getAttribute("lastLocation"));
			} else {
				response.sendRedirect("tour");
			}
        } catch (Exception e) {
        	response.sendError(404, e.getMessage());
        }		
	}

}
