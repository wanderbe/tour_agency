package ua.nure.sidorovich.controlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.sidorovich.service.TourService;
import ua.nure.sidorovich.service.TourServiceImpl;
import ua.nure.sidorovich.util.DataBaseException;


@WebServlet("/deleteTour")
public class TourDeleteServlet extends HttpServlet {

	private static final long serialVersionUID = -8357741065626617709L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.sendRedirect("tours");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		TourService tourService = new TourServiceImpl();
		
		if(request.getParameter("idTour") != null 
				&& request.getParameter("idTour").length() != 0){
			try {
				
				if(tourService.remove(tourService.getByID(Long.parseLong(request.getParameter("idTour"))))){
					if(session.getAttribute("lastLocation") != null){
						response.sendRedirect((String)session.getAttribute("lastLocation"));
					} else {
						response.sendRedirect("tours");
					}
				} else {
					response.sendRedirect("tours");
				}
			} catch (NumberFormatException e) {
				response.sendRedirect("tours");
			} catch (DataBaseException e) {
				response.sendError(404, e.getMessage());
			}
		} else {
			response.sendRedirect("tours");
		}
	}

}
