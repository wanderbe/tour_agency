package ua.nure.sidorovich.controlers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.sidorovich.entety.Hotel;
import ua.nure.sidorovich.service.HotelService;
import ua.nure.sidorovich.service.HotelServiceImpl;
import ua.nure.sidorovich.util.DataBaseException;


@WebServlet("/hotelcatalog")
public class HotelCatalogServlet extends HttpServlet {


	private static final long serialVersionUID = 6580169524565833356L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		request.getSession();
		
		HotelService hotelService = new HotelServiceImpl();
		
		List<Hotel> resultList = null;
		
		try {
			resultList = hotelService.getList();

			request.setAttribute("hotelList", resultList);
			
			request.getRequestDispatcher("WEB-INF/HotelCatalog.jsp").forward(request, response);
		} catch (DataBaseException e) {
			response.sendError(404, e.getMessage());
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}

}
