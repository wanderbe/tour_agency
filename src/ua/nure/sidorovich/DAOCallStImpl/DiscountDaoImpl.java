package ua.nure.sidorovich.DAOCallStImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ua.nure.sidorovich.DAO.DiscountDao;
import ua.nure.sidorovich.entety.Discount;
import ua.nure.sidorovich.util.ConnectionManagerImpl;

public class DiscountDaoImpl extends DAOSceleton implements DiscountDao{
	
	public DiscountDaoImpl(){
		super();
	}

	private static final String GET_ALL_DISCONTS = "{call GET_ALL_DISCONTS}";
	
	private static final String GET_DISCONT_BY_REGISTER_TIME = "{call GET_DISCONT_BY_REGISTER_TIME(?)}";
	
	private static final String GET_DISCONT_BY_ID = "{call GET_DISCONT_BY_ID(?)}";
	
	private static final String GET_LATEST_DISCONTS = "{call GET_LATEST_DISCONTS}";
		
	private static final String INSERT_DISCONT = "{call INSERT_DISCONT(?, ?, ?)}";
	
	private Discount createDiscountFromResultSet(ResultSet rs) throws SQLException {
		Discount discont = new Discount();
		int k = 0;
		discont.setId(rs.getLong(++k));
		discont.setRegisterTime(rs.getTimestamp(++k));
		discont.setStep(rs.getInt(++k));
		discont.setMaxPercent(rs.getInt(++k));
		
		return discont;
	}
	
	
	@Override
	public Discount getByID(long id) throws SQLException{
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
		
		Discount discont = null;
		
		try {
			callSt = con.prepareCall(GET_DISCONT_BY_ID);
			callSt.setLong(1, id);
			ResultSet rs = callSt.executeQuery();
			
			if(rs.next()){
				discont = createDiscountFromResultSet(rs);
			}
		} finally {
			closeStatement(callSt);
			con.close();
		}
		return discont;
	}

	
	
	@Override
	public List<Discount> getList() throws SQLException {
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
				
		List<Discount> disconts = new ArrayList<>();
		
		try {
			callSt = con.prepareCall(GET_ALL_DISCONTS);
			ResultSet rs = callSt.executeQuery();
			
			while(rs.next()){
				Discount discont = createDiscountFromResultSet(rs);				
				disconts.add(discont);
			}
		} finally {
			closeStatement(callSt);
			con.close();
		}
		return disconts;
	}
	
	
	@Override
	public List<Discount> getByRegisterTime(Timestamp registerTime) throws SQLException {

		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
		
		List<Discount> disconts = new ArrayList<>();
		
		try {
			callSt = con.prepareCall(GET_DISCONT_BY_REGISTER_TIME);
			
			int i = 0;
			callSt.setTimestamp(++i, registerTime);
			ResultSet rs = callSt.executeQuery();
			
			while(rs.next()){
				Discount discont = createDiscountFromResultSet(rs);				
				disconts.add(discont);
			}
		} finally {
			closeStatement(callSt);
			con.close();
		}
		return disconts;
	}

	
	
	@Override
	public long save(Object o) throws SQLException {
		
		if(o == null || !(o instanceof Discount)  || ((Discount)o).getRegisterTime() == null){
			return -1;
		}
		
		Discount discont = (Discount)o;
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
				
		long result = -1;
		
		try {
			callSt = con.prepareCall(INSERT_DISCONT);
			
			int k = 0;
			callSt.setTimestamp(++k, discont.getRegisterTime());
			callSt.setInt(++k, discont.getStep());
			callSt.setInt(++k, discont.getMaxPercent());
			
			result = callSt.executeUpdate();
		} finally {
			closeStatement(callSt);
			con.close();
		}
		return result;
	}

	
	@Override
	public Discount getLatestDiscont() throws SQLException {

		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		CallableStatement callSt = null;
				
		Discount discont = null;
		
		try {
			callSt = con.prepareCall(GET_LATEST_DISCONTS);
			ResultSet rs = callSt.executeQuery();
			
			while(rs.next()){
				discont = createDiscountFromResultSet(rs);
			}
		} finally {
			closeStatement(callSt);
			con.close();
		}
		return discont;
	}


	@Override
	public Object getByName(String name) throws SQLException {
		throw new UnsupportedOperationException();
	}


	@Override
	public boolean update(Object o) throws SQLException {
		throw new UnsupportedOperationException();
	}


	@Override
	public boolean remove(Object o) throws SQLException {
		throw new UnsupportedOperationException();
	}

	
}
