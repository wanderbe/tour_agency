package ua.nure.sidorovich.controlers;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.sidorovich.entety.Order;
import ua.nure.sidorovich.entety.Tour;
import ua.nure.sidorovich.entety.User;
import ua.nure.sidorovich.service.OrderService;
import ua.nure.sidorovich.service.OrderServiceImpl;
import ua.nure.sidorovich.service.OrderStatusService;
import ua.nure.sidorovich.service.OrderStatusServiceImpl;
import ua.nure.sidorovich.util.DataBaseException;


@WebServlet("/order")
public class OrderServlet extends HttpServlet {
	
	private static final long serialVersionUID = 7901876402233842906L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.sendRedirect("cabinet");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
			
		HttpSession session = request.getSession();
		
		OrderService orderService = new OrderServiceImpl();
		OrderStatusService orderStatusService = new OrderStatusServiceImpl();
		
		User user;
		Map<Tour, Integer> bascetMap = null;
		List<Order> orderList = null;
		
		if(session.getAttribute("bascet") == null || !(session.getAttribute("bascet") instanceof Map)){
			response.sendRedirect("login");
		} else {
			
			bascetMap = (Map<Tour, Integer>)session.getAttribute("bascet");
			user = (User)session.getAttribute("user");
			orderList = new ArrayList<Order>();
			
			try {			
				for(Map.Entry<Tour, Integer> set : bascetMap.entrySet()){
					for(int i = 0; i < set.getValue(); i++){
						Order order = new Order();
						order.setRegisterTime(new Date(System.currentTimeMillis()));
						order.setUser(user);
						order.setTour(set.getKey());
						order.setCost(0);
						order.setOrderStatus(orderStatusService.getByName(OrderStatusService.NAME_OF_ORDER_STATUS_REGISTERED));
						
						orderList.add(order);
					}	
				}
				
				if(orderService.saveList(orderList)){
					session.setAttribute("bascet", null);
					response.sendRedirect("order");
				} else {
					request.setAttribute("message", "Something wrong! Try again!"); // todo
					response.sendRedirect("bascet");
				}
			
			} catch (DataBaseException e) {
				response.sendError(404, e.getMessage());
			}
		}
	}
}
