package ua.nure.sidorovich.DAOPrStImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ua.nure.sidorovich.DAO.DiscountDao;
import ua.nure.sidorovich.DAOCallStImpl.DAOSceleton;
import ua.nure.sidorovich.entety.Discount;
import ua.nure.sidorovich.util.ConnectionManagerImpl;

public class DiscountDaoImpl extends DAOSceleton implements DiscountDao{
	
	public DiscountDaoImpl(){
		super();
	}

	private static final String GET_ALL_DISCONTS = "SELECT * FROM disconts";
	
	private static final String GET_DISCONT_BY_REGISTER_TIME 
		= "SELECT * FROM disconts WHERE time_discont_registration=?";
	
	private static final String GET_DISCONT_BY_ID = "SELECT * FROM disconts WHERE id=?";
	
	private static final String GET_LATEST_DISCONTS 
	= "SELECT * FROM disconts "
			+ "WHERE time_discont_registration=(SELECT MAX(time_discont_registration) FROM disconts)";
		
	private static final String INSERT_DISCONT 
		= "INSERT INTO disconts (time_discont_registration, percent_step, max_percent) VALUES (?, ?, ?)";
	
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
		PreparedStatement prepSt = null;
		
		Discount discont = null;
		
		try {
			prepSt = con.prepareStatement(GET_DISCONT_BY_ID);
			prepSt.setLong(1, id);
			ResultSet rs = prepSt.executeQuery();
			
			if(rs.next()){
				discont = createDiscountFromResultSet(rs);
			}
		} finally {
			closeStatement(prepSt);
			con.close();
		}
		return discont;
	}

	
	
	@Override
	public List<Discount> getList() throws SQLException {
		
		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		Statement st = null;
				
		List<Discount> disconts = new ArrayList<>();
		
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(GET_ALL_DISCONTS);
			
			while(rs.next()){
				Discount discont = createDiscountFromResultSet(rs);				
				disconts.add(discont);
			}
		} finally {
			closeStatement(st);
			con.close();
		}
		return disconts;
	}
	
	
	@Override
	public List<Discount> getByRegisterTime(Timestamp registerTime) throws SQLException {

		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		PreparedStatement prepSt = null;
		
		List<Discount> disconts = new ArrayList<>();
		
		try {
			prepSt = con.prepareStatement(GET_DISCONT_BY_REGISTER_TIME);
			
			int i = 0;
			prepSt.setTimestamp(++i, registerTime);
			ResultSet rs = prepSt.executeQuery();
			
			while(rs.next()){
				Discount discont = createDiscountFromResultSet(rs);				
				disconts.add(discont);
			}
		} finally {
			closeStatement(prepSt);
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
		PreparedStatement prepSt = null;
				
		long result = -1;
		
		try {
			prepSt = con.prepareStatement(INSERT_DISCONT);
			
			int k = 0;
			prepSt.setTimestamp(++k, discont.getRegisterTime());
			prepSt.setInt(++k, discont.getStep());
			prepSt.setInt(++k, discont.getMaxPercent());
			
			result = prepSt.executeUpdate();
		} finally {
			closeStatement(prepSt);
			con.close();
		}
		return result;
	}

	
	@Override
	public Discount getLatestDiscont() throws SQLException {

		Connection con = ConnectionManagerImpl.getConnectionManager().getConnection();
		Statement st = null;
				
		Discount discont = null;
		
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(GET_LATEST_DISCONTS);
			
			while(rs.next()){
				discont = createDiscountFromResultSet(rs);
			}
		} finally {
			closeStatement(st);
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
