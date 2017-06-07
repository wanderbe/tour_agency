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


@WebFilter(value = {"/orders", "/newDiscountSystem"})
public class ManagerFilter implements Filter {
	
	private static final String MANAGER = "manager";
	
	private static final String ADMIN = "admin";

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		
		if(session.getAttribute("user") == null || !(session.getAttribute("user") instanceof User)){
			((HttpServletResponse)response).sendRedirect("login");
		} else if(MANAGER.equals(((User)session.getAttribute("user")).getRole().getName()) 
				|| ADMIN.equals(((User)session.getAttribute("user")).getRole().getName())){
			chain.doFilter(request, response);
		} else {
			((HttpServletResponse)response).sendRedirect("tours");
		}
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

	@Override
	public void destroy() {
	}

}
