package ua.nure.sidorovich.jstl;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import ua.nure.sidorovich.service.UserService;
import ua.nure.sidorovich.service.UserServiceImpl;
import ua.nure.sidorovich.util.DataBaseException;

public class CheckBlockedUserTag extends TagSupport{
	
	private Long idUser;
    
    public void setIdUser(long idUser) {
    	this.idUser = idUser;
    }

	private static final long serialVersionUID = -5957952890269746812L;
	
	public int doStartTag() throws JspException {
        try {
        	JspWriter out = pageContext.getOut();
        	UserService userService = new UserServiceImpl();
            
            if(userService.isItBlocked(idUser)){
            	out.print("Blocked");
            } else{
            	out.print("Not locked");
            }
            
        } catch(IOException | DataBaseException e) {
            throw new JspException("Error: " + e.getMessage());
        }       
        return SKIP_BODY;
    }

}
