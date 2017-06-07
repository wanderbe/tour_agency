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
import javax.servlet.http.HttpSession;

import ua.nure.sidorovich.entety.User;
import ua.nure.sidorovich.service.UserService;
import ua.nure.sidorovich.service.UserServiceImpl;


@WebFilter("/*")
public class AccessViewFilter implements Filter {

	private static final String USER = "user";
	
	private static final String MANAGER = "manager";
	
	private static final String ADMIN = "admin";

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		
		UserService userService = new UserServiceImpl();
		
		if(session.getAttribute("user") == null || !(session.getAttribute("user") instanceof User)){
			((HttpServletRequest) request).setAttribute("blockedUser", false);
			
			((HttpServletRequest) request).setAttribute("userRights", false);
			((HttpServletRequest) request).setAttribute("managerRights", false);
			((HttpServletRequest) request).setAttribute("adminRights", false);
		} else {
			if(userService.isItBlocked((User)session.getAttribute("user"))){
				((HttpServletRequest) request).setAttribute("blockedUser", true);
				
				((HttpServletRequest) request).setAttribute("userRights", false);
				((HttpServletRequest) request).setAttribute("managerRights", false);
				((HttpServletRequest) request).setAttribute("adminRights", false);
			} else if(USER.equals(((User)session.getAttribute("user")).getRole().getName())){
				((HttpServletRequest) request).setAttribute("blockedUser", false);
				
				((HttpServletRequest) request).setAttribute("userRights", true);
				((HttpServletRequest) request).setAttribute("managerRights", false);
				((HttpServletRequest) request).setAttribute("adminRights", false);
				
			} else if(MANAGER.equals(((User)session.getAttribute("user")).getRole().getName())){
				((HttpServletRequest) request).setAttribute("blockedUser", false);
				
				((HttpServletRequest) request).setAttribute("userRights", true);
				((HttpServletRequest) request).setAttribute("managerRights", true);
				((HttpServletRequest) request).setAttribute("adminRights", false);
				
			} else if(ADMIN.equals(((User)session.getAttribute("user")).getRole().getName())){
				((HttpServletRequest) request).setAttribute("blockedUser", false);
				
				((HttpServletRequest) request).setAttribute("userRights", true);
				((HttpServletRequest) request).setAttribute("managerRights", true);
				((HttpServletRequest) request).setAttribute("adminRights", true);
				
			}else {
				((HttpServletRequest) request).setAttribute("blockedUser", true);
				((HttpServletRequest) request).setAttribute("userRights", false);
				((HttpServletRequest) request).setAttribute("managerRights", false);
				((HttpServletRequest) request).setAttribute("adminRights", false);
				}		
			}  
		chain.doFilter(request, response);
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}
