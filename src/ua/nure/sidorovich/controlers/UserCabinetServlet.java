package ua.nure.sidorovich.controlers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.sidorovich.entety.Order;
import ua.nure.sidorovich.entety.User;
import ua.nure.sidorovich.service.OrderService;
import ua.nure.sidorovich.service.OrderServiceImpl;
import ua.nure.sidorovich.service.OrderStatusService;
import ua.nure.sidorovich.service.OrderStatusServiceImpl;
import ua.nure.sidorovich.service.UserService;
import ua.nure.sidorovich.service.UserServiceImpl;
import ua.nure.sidorovich.util.DataBaseException;

@WebServlet("/cabinet")
public class UserCabinetServlet extends HttpServlet {

	private static final long serialVersionUID = 8124141131832201185L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		UserService userService = new UserServiceImpl();
		OrderService orderService  = new OrderServiceImpl();
		OrderStatusService orderStatusService = new OrderStatusServiceImpl();
		
		try {
			
			User user = null;		
			if(request.getParameter("user_id") != null){
				user = userService.getByID(Long.parseLong(request.getParameter("user_id")));
				request.setAttribute("user", user);
			} else {
				user = (User) session.getAttribute("user");
			}	
							
			List<Order> orderList = orderService.getByUser(user);
			
			request.setAttribute("interested_order_id", request.getParameter("interested_order_id"));
			
			request.setAttribute("orderStatusList", orderStatusService.getList());
			request.setAttribute("paidOrders"
					, orderService.getListOrdersByUserAndStatus(OrderService.PAID_STATUS, user).size());
			request.setAttribute("orderList", orderList);
			
			request.getRequestDispatcher("WEB-INF/UserCabinet.jsp").forward(request, response);
			
		} catch (NumberFormatException e) {
			if(session.getAttribute("lastLocation") != null){
				response.sendRedirect((String)session.getAttribute("lastLocation"));
			} else {
				response.sendRedirect("tours");
			}			
		} catch (DataBaseException e) {
			response.sendError(404, e.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.sendRedirect("cabinet");
		}		
}
