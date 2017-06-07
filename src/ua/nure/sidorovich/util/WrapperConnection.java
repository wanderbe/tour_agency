package ua.nure.sidorovich.util;

import java.sql.Connection;

public class WrapperConnection extends WrapperConnectionSceleton implements Connection{
	
	private ConnectionManager connectionManager;
	
	public WrapperConnection(Connection connection, ConnectionManager connectionManager) {
		this.innerConnection = connection;
		this.connectionManager = connectionManager;
	}

	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}

	public void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}
	
	
	@Override
	public void close(){
		connectionManager.closeConnection(this);
		
	}

}
