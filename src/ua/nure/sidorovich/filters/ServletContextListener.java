package ua.nure.sidorovich.filters;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.xml.DOMConfigurator;


@WebListener
public class ServletContextListener implements javax.servlet.ServletContextListener {

    
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	
    public void contextInitialized(ServletContextEvent arg0)  { 
    	
    	DOMConfigurator
		.configure((ServletContextListener.class.getResource("").toString()
    			+ "../../../../../../log4j.xml").substring(6));
    }
	
}
