package ua.nure.sidorovich.controlers;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.sidorovich.entety.Tour;


@WebServlet("/deleteFromBascet")
public class UserBascetDeleteItemServlet extends HttpServlet {

	private static final long serialVersionUID = -8710796965619367593L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.sendRedirect("bascet");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		if(request.getParameter("idTour") != null 
				&& request.getParameter("idTour").length() != 0 
				&& session.getAttribute("bascet") != null 
				&& (session.getAttribute("bascet") instanceof Map)){
			
			Map<Tour, Integer> bascetMap = (Map<Tour, Integer>) session.getAttribute("bascet");
			
			Tour tour = null;
			
			try {
				tour = new Tour();
				tour.setId(Long.parseLong(request.getParameter("idTour")));
				if(bascetMap.containsKey(tour)){
					if(bascetMap.get(tour) > 1){
						bascetMap.put(tour, bascetMap.get(tour) -1);
					} else {
						bascetMap.remove(tour);
					}
				}
				
				if(session.getAttribute("bascetSize") != null 
						|| (session.getAttribute("bascetSize") instanceof Integer)){
					session.setAttribute("bascetSize", (Integer)session.getAttribute("bascetSize") - 1);
				}
				
				if(bascetMap.isEmpty()){
					session.setAttribute("bascet", null);
					session.setAttribute("bascetSize", null);
				}
				
				if(session.getAttribute("lastLocation") != null){
					response.sendRedirect((String)session.getAttribute("lastLocation"));
				} else {
					response.sendRedirect("bascet");
				}
				
			} catch (NumberFormatException e) {
				request.setAttribute("message", "Something wrong! Try again!");
				request.getRequestDispatcher("WEB-INF/UserBascet.jsp").forward(request, response);
			}
		} else {
			request.setAttribute("message", "Something wrong! Try again!");
			request.getRequestDispatcher("WEB-INF/UserBascet.jsp").forward(request, response);
		}
	}

}
