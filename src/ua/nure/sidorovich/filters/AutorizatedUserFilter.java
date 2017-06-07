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


@WebFilter(value = {"/order", "/bascet", "/cabinet", "/updateUser"})
public class AutorizatedUserFilter implements Filter {

   
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		
		if(session.getAttribute("user") == null || !(session.getAttribute("user") instanceof User) 
				|| ((User)session.getAttribute("user")).getId() == 0 ){
			((HttpServletResponse)response).sendRedirect("login");
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
