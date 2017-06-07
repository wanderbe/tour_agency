package ua.nure.sidorovich.controlers;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.sidorovich.entety.Discount;
import ua.nure.sidorovich.service.DiscountService;
import ua.nure.sidorovich.service.DiscountServiceImpl;
import ua.nure.sidorovich.util.DataBaseException;

@WebServlet("/newDiscountSystem")
public class NewDiscountSystemServlet extends HttpServlet {

	private static final long serialVersionUID = 6546659413638123475L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		request.getRequestDispatcher("WEB-INF/NewDiscountSystemForm.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		DiscountService discountService = new DiscountServiceImpl();
				
		if(request.getParameter("discountStep") == null || request.getParameter("discountStep").length() == 0
				|| request.getParameter("maxDiscount") == null 
				|| request.getParameter("maxDiscount").length() == 0){
			
			request.setAttribute("message", "Something wrong! Try again!");
			request.getRequestDispatcher("WEB-INF/NewDiscountSystemForm.jsp").forward(request, response);
		} else {
			Discount discount = new Discount();
			
			discount.setRegisterTime(new Timestamp(System.currentTimeMillis()));
			
			try{
				discount.setStep(Integer.parseInt(request.getParameter("discountStep")));
				discount.setMaxPercent(Integer.parseInt(request.getParameter("maxDiscount")));
				discountService.save(discount);
				
				if(session.getAttribute("lastLocation") != null){
					response.sendRedirect((String)session.getAttribute("lastLocation"));
				} else {
					response.sendRedirect("tour");
				}
				
			} catch (NumberFormatException e) {
				request.setAttribute("message", "Something wrong! Try again!");
				request.getRequestDispatcher("WEB-INF/NewDiscountSystemForm.jsp").forward(request, response);
			} catch (DataBaseException e) {
				response.sendError(404, e.getMessage());
			}			
		}
	}
}
