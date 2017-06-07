package ua.nure.sidorovich.controlers;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.sidorovich.entety.HotelType;
import ua.nure.sidorovich.entety.RestType;
import ua.nure.sidorovich.entety.Tour;
import ua.nure.sidorovich.service.HotelTypeService;
import ua.nure.sidorovich.service.HotelTypeServiceImpl;
import ua.nure.sidorovich.service.RestTypeService;
import ua.nure.sidorovich.service.RestTypeServiceImpl;
import ua.nure.sidorovich.service.TourService;
import ua.nure.sidorovich.service.TourServiceImpl;
import ua.nure.sidorovich.util.ByHotTourComparator;
import ua.nure.sidorovich.util.DataBaseException;


@WebServlet("/tours")
public class TourCatalogServlet extends HttpServlet {

	private static final long serialVersionUID = -7343785505579770665L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		TourService tourService = new TourServiceImpl();
		RestTypeService restTypeService = new RestTypeServiceImpl();
		HotelTypeService hotelTypeService = new HotelTypeServiceImpl();		
		
		List<Tour> tourList = null;
		List<RestType> restTypeList = null;
		List<HotelType> hotelTypeList = null;
		
		long lookingRestTypeId = -1;
		if(request.getParameter("lookingRestTypeId") != null 
				&& request.getParameter("lookingRestTypeId").length() != 0){
			try{
				lookingRestTypeId = Long.parseLong(request.getParameter("lookingRestTypeId"));
			} catch(NumberFormatException e){
				lookingRestTypeId = -1;
				}
		} 
		request.setAttribute("lookingRestTypeId", lookingRestTypeId);
			
		int lookingStartPrice = -1;
		if(request.getParameter("lookingStartPrice") != null 
				&& request.getParameter("lookingStartPrice").length() != 0){
			try{
				lookingStartPrice = Integer.parseInt(request.getParameter("lookingStartPrice"));
				request.setAttribute("lookingStartPrice", lookingStartPrice);
			} catch(NumberFormatException e){
				request.setAttribute("lookingStartPrice", "");
				lookingStartPrice = -1;
				}
		}	
		
		int lookingFinishPrice = -1;
		if(request.getParameter("lookingFinishPrice") != null 
				&& request.getParameter("lookingFinishPrice").length() != 0){
			try{
				lookingFinishPrice = Integer.parseInt(request.getParameter("lookingFinishPrice"));
				request.setAttribute("lookingFinishPrice", lookingFinishPrice);
			} catch(NumberFormatException e){
				request.setAttribute("lookingFinishPrice", "");
				lookingFinishPrice = -1;
				}
		}
		
		int lookingPersonsAmount = -1;
		if(request.getParameter("lookingPersonsAmount") != null 
				&& request.getParameter("lookingPersonsAmount").length() != 0){
			try{
				lookingPersonsAmount = Integer.parseInt(request.getParameter("lookingPersonsAmount"));
				request.setAttribute("lookingPersonsAmount", lookingPersonsAmount);
			} catch(NumberFormatException e){
				request.setAttribute("lookingPersonsAmount", "");
				lookingPersonsAmount = -1;
				}
		}
		
		long lookingHotelTypeId = -1;
		if(request.getParameter("lookingHotelTypeId") != null 
				&& request.getParameter("lookingHotelTypeId").length() != 0){
			try{
				lookingHotelTypeId = Long.parseLong(request.getParameter("lookingHotelTypeId"));
			} catch(NumberFormatException e){
				lookingHotelTypeId = -1;
				}
		} 
		request.setAttribute("lookingHotelTypeId", lookingHotelTypeId);
		
		try {						
			restTypeList = restTypeService.getList();
			hotelTypeList = hotelTypeService.getList();
			
			tourList = tourService.findByParametrs(lookingRestTypeId, lookingStartPrice, lookingFinishPrice,
					lookingPersonsAmount, lookingHotelTypeId);
			
			Collections.sort(tourList, new ByHotTourComparator());
			
			request.setAttribute("tourList", tourList);	
			request.setAttribute("restTypeList", restTypeList);
			request.setAttribute("hotelTypeList", hotelTypeList);
			
			request.getRequestDispatcher("WEB-INF/TourCatalog.jsp").forward(request, response);
			
		} catch (DataBaseException e) {
			response.sendError(404, e.getMessage());
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		TourService tourService = new TourServiceImpl();
		Tour tour;
		
		try {			
			tour = tourService.getByID(Long.parseLong(request.getParameter("idTour")));
			tour.setItHot(Boolean.parseBoolean(request.getParameter("setHot")));
						
			if(tourService.update(tour)){
				response.sendRedirect("tours");
			} else {
				throw new DataBaseException("Status order" + tour.getId() + " " + tour.getName() 
				+ " haven't changed HOT status to " + request.getParameter("setHot"));
			}
			
		} catch (NumberFormatException ne) {
			response.sendRedirect("tours");
			
		} catch (DataBaseException e) {
			response.sendError(404, e.getMessage());
		}
	}

}
