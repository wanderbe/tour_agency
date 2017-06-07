package ua.nure.sidorovich.controlers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.sidorovich.entety.Hotel;
import ua.nure.sidorovich.entety.RestType;
import ua.nure.sidorovich.entety.Tour;
import ua.nure.sidorovich.service.HotelService;
import ua.nure.sidorovich.service.HotelServiceImpl;
import ua.nure.sidorovich.service.RestTypeService;
import ua.nure.sidorovich.service.RestTypeServiceImpl;
import ua.nure.sidorovich.service.TourService;
import ua.nure.sidorovich.service.TourServiceImpl;
import ua.nure.sidorovich.util.DataBaseException;

@WebServlet("/newTour")
public class TourAddingServlet extends HttpServlet {

	private static final long serialVersionUID = -512596010195466407L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		request.getSession();
		
		HotelService hotelService = new HotelServiceImpl();
		RestTypeService restTypeService = new RestTypeServiceImpl();
				
		List<Hotel> hotelsList = null;
		List<RestType> restTypeLst = null;
		
		try {			
			hotelsList = hotelService.getList();
			restTypeLst = restTypeService.getList();
			
			request.setAttribute("hotelList", hotelsList);
			request.setAttribute("restTypeList", restTypeLst);
			
			request.setAttribute("message", "Enter new data for tour:");
			
			request.setAttribute("action", "newTour");
			request.getRequestDispatcher("WEB-INF/TourForm.jsp").forward(request, response);
		} catch (DataBaseException e) {
			response.sendError(404, e.getMessage());
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		request.getSession();
		
		TourService tourService = new TourServiceImpl();		
		HotelService hotelService = new HotelServiceImpl();
		RestTypeService restTypeService = new RestTypeServiceImpl();
		
		try {		
			Tour newTour = new Tour();
			
			newTour.setDescription(request.getParameter("newTourDescription"));
			
			Hotel hotel = hotelService.getByID(Long.parseLong(request.getParameter("newTourHotelId")));
			
			newTour.setHotel(hotel);
			newTour.setItHot(Boolean.parseBoolean(request.getParameter("newTourHot"))); //todo
			newTour.setName(request.getParameter("newTourName"));
			newTour.setPlaces(Integer.parseInt(request.getParameter("newTourPlaces")));
			newTour.setPrice(Double.parseDouble(request.getParameter("newTourPrice")));
			
			RestType restType = restTypeService.getByID(Long.parseLong(request.getParameter("newTourRestTypeId")));
			
			newTour.setRestType(restType);
			
			if(tourService.save(newTour) > 0){
				response.sendRedirect("tours");
			} else {
				List<Hotel> hotelsList = hotelService.getList();
				List<RestType> restTypeLst = restTypeService.getList();
					
				request.setAttribute("hotelList", hotelsList);
				request.setAttribute("restTypeList", restTypeLst);
				
				request.setAttribute("newTour", newTour);
				request.setAttribute("message", "Something wrong! Try again!");
				
				request.setAttribute("action", "newTour");
				request.getRequestDispatcher("WEB-INF/TourForm.jsp").forward(request, response);
			}
			
		} catch (NumberFormatException e) {
			
				try {
					List<Hotel> hotelsList = hotelService.getList();
					List<RestType> restTypeLst = restTypeService.getList();
					
					request.setAttribute("hotelList", hotelsList);
					request.setAttribute("restTypeList", restTypeLst);
					
					request.setAttribute("message", "Something wrong! Try again!");
					
					request.setAttribute("action", "newTour");
					request.getRequestDispatcher("WEB-INF/TourForm.jsp").forward(request, response);
				} catch (DataBaseException e1) {
					response.sendError(404, e.getMessage());
				}
		} catch (DataBaseException e) {
			response.sendError(404, e.getMessage());
		}
		
	}

}
