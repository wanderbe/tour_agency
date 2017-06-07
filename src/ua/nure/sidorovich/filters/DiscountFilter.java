package ua.nure.sidorovich.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import ua.nure.sidorovich.entety.Discount;
import ua.nure.sidorovich.service.DiscountService;
import ua.nure.sidorovich.service.DiscountServiceImpl;
import ua.nure.sidorovich.util.DataBaseException;


@WebFilter("/*")
public class DiscountFilter implements Filter {
	
    public DiscountFilter() {
        // TODO Auto-generated constructor stub
    }

	
	public void destroy() {
		// TODO Auto-generated method stub
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		DiscountService discountService = new DiscountServiceImpl();
		
		Discount currentDiscount = null;
		
		try {
			currentDiscount = discountService.getLatestDiscont();
		} catch (DataBaseException e) {
		}
		
		request.setAttribute("currentDiscount", currentDiscount);
	
		
		chain.doFilter(request, response);
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
