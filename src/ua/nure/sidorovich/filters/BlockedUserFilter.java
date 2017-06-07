package ua.nure.sidorovich.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.sidorovich.entety.User;
import ua.nure.sidorovich.service.UserService;
import ua.nure.sidorovich.service.UserServiceImpl;


@WebFilter(value = {"/orders", "/newDiscountSystem", "/users", "/updateTour", "/deleteTour", "/newTour"})
public class BlockedUserFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		
		UserService userService = new UserServiceImpl();
		
			if(userService.isItBlocked((User)session.getAttribute("user"))){
				
				((HttpServletResponse)response).sendRedirect("tours");
				
			} else {
				chain.doFilter(request, response);
			} 
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}
