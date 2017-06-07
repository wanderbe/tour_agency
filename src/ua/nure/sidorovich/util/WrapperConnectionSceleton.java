package ua.nure.sidorovich.util;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public abstract class WrapperConnectionSceleton implements Connection{

	protected Connection innerConnection;
		
	public Connection getInnerConnection() {
		return innerConnection;
	}

	public void setInnerConnection(Connection innerConnection) {
		this.innerConnection = innerConnection;
	}

	public void abort(Executor executor) throws SQLException {
		innerConnection.abort(executor);
	}

	public void clearWarnings() throws SQLException {
		innerConnection.clearWarnings();
	}

	public void commit() throws SQLException {
		innerConnection.commit();
	}

	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		return innerConnection.createArrayOf(typeName, elements);
	}

	public Blob createBlob() throws SQLException {
		return innerConnection.createBlob();
	}

	public Clob createClob() throws SQLException {
		return innerConnection.createClob();
	}

	public NClob createNClob() throws SQLException {
		return innerConnection.createNClob();
	}

	public SQLXML createSQLXML() throws SQLException {
		return innerConnection.createSQLXML();
	}

	public Statement createStatement() throws SQLException {
		return innerConnection.createStatement();
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return innerConnection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		return innerConnection.createStatement(resultSetType, resultSetConcurrency);
	}

	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		return innerConnection.createStruct(typeName, attributes);
	}

	public boolean getAutoCommit() throws SQLException {
		return innerConnection.getAutoCommit();
	}

	public String getCatalog() throws SQLException {
		return innerConnection.getCatalog();
	}

	public Properties getClientInfo() throws SQLException {
		return innerConnection.getClientInfo();
	}

	public String getClientInfo(String name) throws SQLException {
		return innerConnection.getClientInfo(name);
	}

	public int getHoldability() throws SQLException {
		return innerConnection.getHoldability();
	}

	public DatabaseMetaData getMetaData() throws SQLException {
		return innerConnection.getMetaData();
	}

	public int getNetworkTimeout() throws SQLException {
		return innerConnection.getNetworkTimeout();
	}

	public String getSchema() throws SQLException {
		return innerConnection.getSchema();
	}

	public int getTransactionIsolation() throws SQLException {
		return innerConnection.getTransactionIsolation();
	}

	public Map<String, Class<?>> getTypeMap() throws SQLException {
		return innerConnection.getTypeMap();
	}

	public SQLWarning getWarnings() throws SQLException {
		return innerConnection.getWarnings();
	}

	public boolean isClosed() throws SQLException {
		return innerConnection.isClosed();
	}

	public boolean isReadOnly() throws SQLException {
		return innerConnection.isReadOnly();
	}

	public boolean isValid(int timeout) throws SQLException {
		return innerConnection.isValid(timeout);
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return innerConnection.isWrapperFor(iface);
	}

	public String nativeSQL(String sql) throws SQLException {
		return innerConnection.nativeSQL(sql);
	}

	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		return innerConnection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		return innerConnection.prepareCall(sql, resultSetType, resultSetConcurrency);
	}

	public CallableStatement prepareCall(String sql) throws SQLException {
		return innerConnection.prepareCall(sql);
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		return innerConnection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
			throws SQLException {
		return innerConnection.prepareStatement(sql, resultSetType, resultSetConcurrency);
	}

	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		return innerConnection.prepareStatement(sql, autoGeneratedKeys);
	}

	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		return innerConnection.prepareStatement(sql, columnIndexes);
	}

	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		return innerConnection.prepareStatement(sql, columnNames);
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return innerConnection.prepareStatement(sql);
	}

	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		innerConnection.releaseSavepoint(savepoint);
	}

	public void rollback() throws SQLException {
		innerConnection.rollback();
	}

	public void rollback(Savepoint savepoint) throws SQLException {
		innerConnection.rollback(savepoint);
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException {
		innerConnection.setAutoCommit(autoCommit);
	}

	public void setCatalog(String catalog) throws SQLException {
		innerConnection.setCatalog(catalog);
	}

	public void setClientInfo(Properties properties) throws SQLClientInfoException {
		innerConnection.setClientInfo(properties);
	}

	public void setClientInfo(String name, String value) throws SQLClientInfoException {
		innerConnection.setClientInfo(name, value);
	}

	public void setHoldability(int holdability) throws SQLException {
		innerConnection.setHoldability(holdability);
	}

	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		innerConnection.setNetworkTimeout(executor, milliseconds);
	}

	public void setReadOnly(boolean readOnly) throws SQLException {
		innerConnection.setReadOnly(readOnly);
	}

	public Savepoint setSavepoint() throws SQLException {
		return innerConnection.setSavepoint();
	}

	public Savepoint setSavepoint(String name) throws SQLException {
		return innerConnection.setSavepoint(name);
	}

	public void setSchema(String schema) throws SQLException {
		innerConnection.setSchema(schema);
	}

	public void setTransactionIsolation(int level) throws SQLException {
		innerConnection.setTransactionIsolation(level);
	}

	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		innerConnection.setTypeMap(map);
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		return innerConnection.unwrap(iface);
	}

	@Override
	public abstract void close() throws SQLException;

}
