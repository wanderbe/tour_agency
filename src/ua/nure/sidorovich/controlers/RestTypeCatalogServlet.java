package ua.nure.sidorovich.controlers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.sidorovich.entety.RestType;
import ua.nure.sidorovich.service.RestTypeService;
import ua.nure.sidorovich.service.RestTypeServiceImpl;
import ua.nure.sidorovich.util.DataBaseException;


@WebServlet("/resttypecatalog")
public class RestTypeCatalogServlet extends HttpServlet {


	private static final long serialVersionUID = 6580169524565833356L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		RestTypeService restTypeService = new RestTypeServiceImpl();
		
		List<RestType> resultList = null;
			
		try {			
			resultList = restTypeService.getList();
			
			request.setAttribute("restTypeList", resultList);
			
			request.getRequestDispatcher("WEB-INF/RestTypeCatalog.jsp").forward(request, response);
			
			
		} catch (DataBaseException e) {
			response.sendError(404, e.getMessage());
		}
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}

}
