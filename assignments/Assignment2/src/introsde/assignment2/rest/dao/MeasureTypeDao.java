package introsde.assignment2.rest.dao;

import introsde.assignment2.rest.model.MeasureType;
import introsde.db.common.ConnectionHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MeasureTypeDao {

	private static MeasureTypeDao instance;
	
	/** A private Constructor prevents any other class from instantiating. */
	private MeasureTypeDao() {
		super();	    
	}
	
	public static synchronized MeasureTypeDao getInstance() {
		if (instance == null) {
			instance = new MeasureTypeDao();
		}
		return instance;
	}
	
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	public List<MeasureType> getAll() {
		List<MeasureType> list = new ArrayList<MeasureType>();
		Connection conn = null;
		String query = "SELECT * FROM measure_type ORDER BY mt_id";
		try {
			ConnectionHelper.getInstance().open();
			conn = ConnectionHelper.getInstance().getConnection();
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				list.add(processRow(rs));
			}
		} catch (SQLException e) {
			System.err.println(e.toString());
		} finally {
			try {
				ConnectionHelper.getInstance().close();
			} catch (SQLException e) {
				System.err.println(e.toString());
			}
		}
		return list;
	}
	
	public MeasureType get(Long measureTypeId) {
		MeasureType measureType = null;
		Connection conn = null;
		String query = "SELECT * FROM measure_type WHERE mt_id = ?";
		try {
			ConnectionHelper.getInstance().open();
			conn = ConnectionHelper.getInstance().getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setLong(1, measureTypeId);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				measureType = processRow(rs);
			}
		} catch (SQLException e) {
			System.err.println(e.toString());
		} finally {
			try {
				ConnectionHelper.getInstance().close();
			} catch (SQLException e) {
				System.err.println(e.toString());
			}
		}
		return measureType;
	}
	
	public MeasureType findByName(String name) {
		MeasureType measureType = null;
		Connection conn = null;
		String query = "SELECT * FROM measure_type WHERE name = ?";
		try {
			ConnectionHelper.getInstance().open();
			conn = ConnectionHelper.getInstance().getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, name);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				measureType = processRow(rs);
			}
		} catch (SQLException e) {
			System.err.println(e.toString());
		} finally {
			try {
				ConnectionHelper.getInstance().close();
			} catch (SQLException e) {
				System.err.println(e.toString());
			}
		}
		return measureType;
	}
	
	private MeasureType processRow(ResultSet rs) throws SQLException {
		Long measureTypeId = rs.getLong("mt_id");
		String name = rs.getString("name");
		String type = rs.getString("type");
    	return new MeasureType(measureTypeId, name, type);
    }
}
