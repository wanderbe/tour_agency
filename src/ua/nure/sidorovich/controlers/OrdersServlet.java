package ua.nure.sidorovich.controlers;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.sidorovich.entety.Order;
import ua.nure.sidorovich.entety.OrderStatus;
import ua.nure.sidorovich.service.OrderService;
import ua.nure.sidorovich.service.OrderServiceImpl;
import ua.nure.sidorovich.service.OrderStatusService;
import ua.nure.sidorovich.service.OrderStatusServiceImpl;
import ua.nure.sidorovich.service.UserService;
import ua.nure.sidorovich.service.UserServiceImpl;
import ua.nure.sidorovich.util.DataBaseException;
import ua.nure.sidorovich.util.MailSender;


@WebServlet("/orders")
public class OrdersServlet extends HttpServlet {
	private static final long serialVersionUID = -189259530520380693L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		request.getSession();
		
		OrderService orderService = new OrderServiceImpl();
		OrderStatusService orderStatusService = new OrderStatusServiceImpl();
		
				
		List<Order> orderList;
		List<OrderStatus> orderStatusList;
			
			try {				
				if(request.getParameter("idOrderStatusForFilter") == null 
						|| request.getParameter("idOrderStatusForFilter").length() == 0
						|| Long.parseLong(request.getParameter("idOrderStatusForFilter")) == 0
						|| Long.parseLong(request.getParameter("idOrderStatusForFilter")) == (-1)){
					orderList = orderService.getList();
				} else {
					request.setAttribute("idOrderStatusForFilter", request.getParameter("idOrderStatusForFilter"));
					
					orderList = orderService
							.getListByOrderStatus(orderStatusService
									.getByID(Long.parseLong(request.getParameter("idOrderStatusForFilter"))));
				}
				
				orderStatusList = orderStatusService.getList();
				
				request.setAttribute("orderList", orderList);
				request.setAttribute("orderStatusList", orderStatusList);
				
				request.getRequestDispatcher("WEB-INF/MenegerOrderedTours.jsp").forward(request, response);
			} catch (NumberFormatException e) {
				response.sendRedirect("orders");
			} catch (DataBaseException e) {
				response.sendError(404, e.getMessage());
			}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();

		OrderService orderService = new OrderServiceImpl();
		OrderStatusService orderStatusService = new OrderStatusServiceImpl();
		UserService userService = new UserServiceImpl();
		
		Order order;
		OrderStatus orderStatus;
		
		try {			
			order = orderService.getByID(Long.parseLong(request.getParameter("idOrder")));
			orderStatus = orderStatusService.getByID(Long.parseLong(request.getParameter("newOrderStatusId")));
						
			if(orderService.changeOrderStatus(order, orderStatus)){
				
		            MailSender.sendMail(order.getUser().getEmail()
		            		, "Message from tour agency"
		            		, "Your order status was changed to " + orderStatus.getName());
		            
				if(session.getAttribute("lastLocation") != null){
					response.sendRedirect((String)session.getAttribute("lastLocation"));
				} else {
					response.sendRedirect("orders");
				}
			} else {				
				throw new DataBaseException("Status order" + order.getId() + " " + "haven't changed to " 
			+ orderStatus.getId() + " " + orderStatus.getName());
			}
			
		} catch (NumberFormatException e) {
			if(session.getAttribute("lastLocation") != null){
				response.sendRedirect((String)session.getAttribute("lastLocation"));
			} else {
				response.sendRedirect("orders");
			}			
		} catch (DataBaseException e) {
			response.sendError(404, e.getMessage());
		} catch (AddressException e) {
			if(session.getAttribute("lastLocation") != null){
				response.sendRedirect((String)session.getAttribute("lastLocation"));
			} else {
				response.sendRedirect("orders");
			}
		} catch (MessagingException e) {
			if(session.getAttribute("lastLocation") != null){
				response.sendRedirect((String)session.getAttribute("lastLocation"));
			} else {
				response.sendRedirect("orders");
			}
		}
		
		
	}
}
