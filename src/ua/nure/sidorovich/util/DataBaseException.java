package ua.nure.sidorovich.util;

import java.sql.SQLException;

public class DataBaseException extends SQLException{
	
	private static final long serialVersionUID = 5213803177004427068L;
	
	String message;
	Exception exception;
	
	public DataBaseException(String message, Exception exception) {
		super();
		this.message = message;
		this.exception = exception;
		exception.printStackTrace();
	}
	
	public DataBaseException(String message) {
		super();
		this.message = message;
	}

	public DataBaseException() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public Exception getException() {
		return exception;
	}
}
