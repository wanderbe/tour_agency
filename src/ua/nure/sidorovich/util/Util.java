package ua.nure.sidorovich.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Util implements AutoCloseable{
	
	private FileInputStream fis;
	private Properties property = new Properties();
	
	private static final String URL = "URL";
    private static final String LOGIN = "LOGIN";
    private static final String PASSWORD = "PASSWORD";
	
	
	
	public Util(String property_file) throws DataBaseException {
		super();
		try {
			fis = new FileInputStream(property_file);
			property.load(fis);
		} catch (IOException e) {
			throw new DataBaseException("Some problem with properties", e); // todo
		}
        
	}

	public String getURL(){	 		
	    return property.getProperty(URL);
	}
	
	public String getLOGIN(){			
	    return property.getProperty(LOGIN);
	}


	public String getPASSWORD(){
		return property.getProperty(PASSWORD);
    }

	@Override
	public void close() {
			try {
				fis.close();
			} catch (IOException e) {
				try {
					fis.close();
				} catch (IOException e1) {
				}
			}
	}
	
}
