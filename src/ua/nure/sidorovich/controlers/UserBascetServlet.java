package ua.nure.sidorovich.controlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.sidorovich.entety.Tour;
import ua.nure.sidorovich.service.TourService;
import ua.nure.sidorovich.service.TourServiceImpl;
import ua.nure.sidorovich.util.DataBaseException;

@WebServlet("/bascet")
public class UserBascetServlet extends HttpServlet {

	private static final long serialVersionUID = -6252590475577489790L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		request.getSession();
		
		request.getRequestDispatcher("WEB-INF/UserBascet.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		TourService tourService = new TourServiceImpl();
		
		HttpSession session = request.getSession();
		
		
		if(request.getParameter("idTour") != null && request.getParameter("idTour").length() != 0){
			Map<Tour, Integer> bascetMap = null;
			
			if(session.getAttribute("bascet") == null 
					|| !(session.getAttribute("bascet") instanceof Map)){
				bascetMap = new HashMap<Tour, Integer>();
				session.setAttribute("bascet", bascetMap);
			} else{
				bascetMap = (Map<Tour, Integer>) session.getAttribute("bascet");  // todo classcastexception
			}
									
			Tour tour = null;
			
			try {
				tour = tourService.getByID(Long.parseLong(request.getParameter("idTour")));
				
				if(bascetMap.containsKey(tour)){
					bascetMap.put(tour, bascetMap.get(tour) + 1);
				} else {
					bascetMap.put(tour, 1);
				}
				
				if(session.getAttribute("bascetSize") == null 
						|| !(session.getAttribute("bascetSize") instanceof Integer)){
					session.setAttribute("bascetSize", 1);
				} else {
					session.setAttribute("bascetSize", (Integer)session.getAttribute("bascetSize") + 1);
				}
				
				if(session.getAttribute("lastLocation") != null){
					response.sendRedirect((String)session.getAttribute("lastLocation"));
				} else {
					response.sendRedirect("tour");
				}
				
			} catch (NumberFormatException ne) {
				response.sendRedirect("tours");
			} catch (DataBaseException e) {
				response.sendError(404, e.getMessage());
			}
			
		} else{
			if(session.getAttribute("lastLocation") != null){
				response.sendRedirect((String)session.getAttribute("lastLocation"));
			} else {
				response.sendRedirect("tours");
			}
		}			
	}		
		
		
	

}
