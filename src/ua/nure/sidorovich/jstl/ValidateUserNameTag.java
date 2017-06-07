package ua.nure.sidorovich.jstl;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import ua.nure.sidorovich.validators.ValidatorByWrites;
import ua.nure.sidorovich.validators.ValidatorByWritesImpl;

public class ValidateUserNameTag extends TagSupport{
	
	private static final long serialVersionUID = -6361497513800894774L;
	
	private String name;
    
    public void setName(String name) {
    	this.name = name;
    }
	
	public int doStartTag() throws JspException {
        try {
        	JspWriter out = pageContext.getOut();
        	ValidatorByWrites validatorByWrites = new ValidatorByWritesImpl();
            
            if(validatorByWrites.isNameUserValide(name)){
            	out.print("Ok");
            } else if(name.length() == 0){
            	out.print("");
            } else{
            	out.print("Not correct name! Try letters (a-zA-Zà-ÿÀ-ß) digits (0-9) and simvols (_-) not less 3, not more 15");
            }
            
        } catch(IOException e) {
            throw new JspException("Error: " + e.getMessage());
        }       
        return SKIP_BODY;
    }

}
