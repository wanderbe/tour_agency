package ua.nure.sidorovich.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionManagerImpl implements ConnectionManager{
	
	public static final int MAX_AMOUNT_OF_CONNECTION = 10;
	
	private static int counter;
	
	private static ConnectionManager connectionManager;
	
	private static final String PROPERTIES_FILE = (ConnectionManagerImpl.class.getResource("").toString() 
			+ "../../../../../../db_config.properties").substring(6);
	
	private String url;
    private String login;
    private String password;

	private List<Connection> connectionList;
	
	
	
	private ConnectionManagerImpl() throws DataBaseException {
		super();
		
		connectionList = new ArrayList<Connection>();
		
		counter = 0;
		
		try(Util util = new Util(PROPERTIES_FILE)){
			this.url = util.getURL();
			this.login = util.getLOGIN();
			this.password = util.getPASSWORD();
		}

		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
		} catch (SQLException e) {
			throw new DataBaseException("Not connection to Data Base", e);
		}
	}
	
	public synchronized static ConnectionManager getConnectionManager()  throws DataBaseException {
		
		if(connectionManager == null){
			connectionManager = new ConnectionManagerImpl();
		}		
		return connectionManager;
		
	}

	@Override
	public synchronized Connection getConnection() throws DataBaseException{
		
		Connection result;
		
		while(!(counter < MAX_AMOUNT_OF_CONNECTION)){
			try {
				this.wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		
		if(connectionList == null || connectionList.isEmpty()){
	        try {
	        	result = new WrapperConnection(DriverManager.getConnection(url, login, password), this);
			} catch (SQLException e) {
				throw new DataBaseException("Not connection to Data Base", e);
			}
		} else {
			result = connectionList.remove(0);
			try {
				while(!result.isValid(0)){
					result.close();
					result = connectionList.remove(0);
				}
			} catch (SQLException e) {
				throw new DataBaseException("Not connection to Data Base", e);
			}
		}
		
		counter++;
		
        return result;
	}
	
	@Override
	public synchronized Connection getConnectionForTransactions() throws DataBaseException{
		Connection result;
        
		while(!(counter < MAX_AMOUNT_OF_CONNECTION)){
			try {
				this.wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
				
        if(connectionList == null || connectionList.isEmpty()){
	        
	        try {
	        	result = new WrapperConnection(DriverManager.getConnection(url, login, password), this);
	        	result.setAutoCommit(false);
			} catch (SQLException e) {
				throw new DataBaseException("Not connection to Data Base", e);
			}
		} else {
			result = connectionList.remove(0);
			
			try {
				while(!result.isValid(0)){
					result.close();
					result = connectionList.remove(0);
				}
			} catch (SQLException e) {
				throw new DataBaseException("Not connection to Data Base", e);
			}
		}
        
        try {
			result.setAutoCommit(false);
		} catch (SQLException e) {
			throw new DataBaseException("Not connection to Data Base", e);
		}
        
        counter++;
        
        return result;
	}

	@Override
	public synchronized void closeConnection(WrapperConnection wrapperConnection) {
		try {
			wrapperConnection.clearWarnings();
			if(!wrapperConnection.getAutoCommit()){
				wrapperConnection.setAutoCommit(true);
			}
						
			if(connectionList.size() < MAX_AMOUNT_OF_CONNECTION && wrapperConnection.isValid(0)){
				connectionList
					.add(new WrapperConnection(wrapperConnection.getInnerConnection(), connectionManager));
			} else {
				wrapperConnection.getInnerConnection().close();
			}
			wrapperConnection.setInnerConnection(null);
			wrapperConnection.setConnectionManager(null);
			
		} catch (SQLException e) {
			try {
				wrapperConnection.getInnerConnection().close();
			} catch (SQLException e1) {
				wrapperConnection.setInnerConnection(null);
				wrapperConnection.setConnectionManager(null);
			}
			wrapperConnection.setInnerConnection(null);
			wrapperConnection.setConnectionManager(null);
		} finally {
			this.notifyAll();
			counter--;
		}
		
	}
	
}
