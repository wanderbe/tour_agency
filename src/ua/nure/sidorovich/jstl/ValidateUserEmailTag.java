package ua.nure.sidorovich.jstl;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import ua.nure.sidorovich.validators.ValidatorByWrites;
import ua.nure.sidorovich.validators.ValidatorByWritesImpl;

public class ValidateUserEmailTag extends TagSupport{
	
	private static final long serialVersionUID = 4247372604535503565L;
	
	private String email;
    
    public void setEmail(String email) {
    	this.email = email;
    }
	
	public int doStartTag() throws JspException {
        try {
        	JspWriter out = pageContext.getOut();
        	ValidatorByWrites validatorByWrites = new ValidatorByWritesImpl();
            
            if(validatorByWrites.isEmailUserValide(email)){
            	out.print("Ok");
            } else if(email.length() == 0){
            	out.print("");
            } else{
            	out.print("Not correct email! Try again!");
            }
            
        } catch(IOException e) {
            throw new JspException("Error: " + e.getMessage());
        }       
        return SKIP_BODY;
    }

}
