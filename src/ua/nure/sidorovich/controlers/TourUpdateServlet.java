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

@WebServlet("/updateTour")
public class TourUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = -85823902877989651L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		request.getSession();
		
		TourService tourService = new TourServiceImpl();		
		HotelService hotelService = new HotelServiceImpl();
		RestTypeService restTypeService = new RestTypeServiceImpl();
		
		if(request.getParameter("idTour") != null && request.getParameter("idTour").length() != 0){
			try {
				Tour newTour = tourService.getByID(Long.parseLong(request.getParameter("idTour")));
				
				List<Hotel> hotelsList = hotelService.getList();
				List<RestType> restTypeLst =  restTypeService.getList();
				
				
				request.setAttribute("hotelList", hotelsList);
				request.setAttribute("restTypeList", restTypeLst);
				request.setAttribute("newTour", newTour);
				
				request.setAttribute("message", "Enter new data for tour:");
				
				request.setAttribute("action", "updateTour");
				request.getRequestDispatcher("WEB-INF/TourForm.jsp").forward(request, response);
			} catch (NumberFormatException e) {
				response.sendRedirect("tours");
			} catch (DataBaseException e) {
				response.sendError(404, e.getMessage());
			}
			
		} else {
			response.sendRedirect("tours");
		}		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		request.getSession();
		
		TourService tourService = new TourServiceImpl();		
		HotelService hotelService = new HotelServiceImpl();
		RestTypeService restTypeService = new RestTypeServiceImpl();
		
		try {	
			Tour tour = new Tour();
			
			tour.setId(Long.parseLong(request.getParameter("newTourId")));
			tour.setDescription(request.getParameter("newTourDescription"));
			
			Hotel hotel = hotelService.getByID(Long.parseLong(request.getParameter("newTourHotelId")));
			
			tour.setHotel(hotel);
			tour.setItHot(Boolean.parseBoolean(request.getParameter("newTourHot"))); //todo
			tour.setName(request.getParameter("newTourName"));
			tour.setPlaces(Integer.parseInt(request.getParameter("newTourPlaces")));
			tour.setPrice(Double.parseDouble(request.getParameter("newTourPrice")));
			
			RestType restType = restTypeService.getByID(Long.parseLong(request.getParameter("newTourRestTypeId")));
			
			tour.setRestType(restType);
			
			if(tourService.update(tour)){
				response.sendRedirect("tours");
			} else {
				
				List<Hotel> hotelsList = hotelService.getList();
				List<RestType> restTypeLst = restTypeService.getList();
				
				request.setAttribute("hotelList", hotelsList);
				request.setAttribute("restTypeList", restTypeLst);
				
				request.setAttribute("newTour", tour);
				request.setAttribute("message", "Something wrong! Try again!1");
				
				request.setAttribute("action", "updateTour");
				request.getRequestDispatcher("WEB-INF/TourForm.jsp").forward(request, response);
			}
			
		} catch (NumberFormatException e) {			
			try {			
				List<Hotel> hotelsList = hotelService.getList();
				List<RestType> restTypeLst = restTypeService.getList();
				
				request.setAttribute("hotelList", hotelsList);
				request.setAttribute("restTypeList", restTypeLst);
				
				request.setAttribute("message", "Something wrong! Try again!2");
				
				request.setAttribute("action", "updateTour");
				request.getRequestDispatcher("WEB-INF/TourForm.jsp").forward(request, response);
			} catch (DataBaseException de) {
				response.sendError(404, e.getMessage());
			}
			
		} catch (DataBaseException e) {
			response.sendError(404, e.getMessage());
		}
		
	}

}
