package ua.nure.sidorovich.jstl;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import ua.nure.sidorovich.entety.Order;
import ua.nure.sidorovich.entety.User;
import ua.nure.sidorovich.service.OrderService;
import ua.nure.sidorovich.service.OrderServiceImpl;
import ua.nure.sidorovich.util.DataBaseException;

public class AmountUsersHaveHotTourTag extends TagSupport{
	
	private static final long serialVersionUID = 6936078252965168387L;
	
	private Long orderStatusId;
    
    public void setOrderStatusId(long orderStatusId) {
    	this.orderStatusId = orderStatusId;
    }
	
	public int doStartTag() throws JspException {
		
        try {
        	JspWriter out = pageContext.getOut();
        	
        	OrderService orderService = new OrderServiceImpl();
        	
        	List<Order> orders = orderService.getListByOrderStatusAndHoteState(orderStatusId, true);
        	
        	Set<User> users = new HashSet<User>();
        	
        	for(Order order : orders){
        		users.add(order.getUser());
        	}      	
        	  	
            out.print(users.size());
            
        } catch(IOException | DataBaseException e) {
            throw new JspException("Error: " + e.getMessage());
        }       
        return SKIP_BODY;
    }

}
