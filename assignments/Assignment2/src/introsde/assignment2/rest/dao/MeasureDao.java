package introsde.assignment2.rest.dao;

import introsde.assignment2.rest.model.Measure;
import introsde.db.common.ConnectionHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MeasureDao {

	private static MeasureDao instance;
	
	/** A private Constructor prevents any other class from instantiating. */
	private MeasureDao() {
		super();    
	}
	
	public static synchronized MeasureDao getInstance() {
		if (instance == null) {
			instance = new MeasureDao();
		}
		return instance;
	}
	
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	public List<Measure> getAll(Long personId, String measureName) {
		List<Measure> list = new ArrayList<Measure>();
		Connection conn = null;
		String query = "SELECT m.m_id, m.value, m.created, mt.name, mt.type FROM measure m INNER JOIN measure_type mt ON m.mt_id = mt.mt_id WHERE m.p_id = ? AND mt.name = ? ORDER BY m.m_id";
		try {
			ConnectionHelper.getInstance().open();
			conn = ConnectionHelper.getInstance().getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setLong(1, personId);
			statement.setString(2, measureName);
			ResultSet rs = statement.executeQuery();
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
	
	public List<Measure> getAllAfterDate(Long personId, String measureName, Date after) {
		List<Measure> list = new ArrayList<Measure>();
		Connection conn = null;
		String query = "SELECT m.m_id, m.value, m.created, mt.name, mt.type FROM measure m INNER JOIN measure_type mt ON m.mt_id = mt.mt_id WHERE m.p_id = ? AND mt.name = ? AND m.created > strftime('%Y-%m-%d %H:%M:%f',?/1000,'unixepoch','localtime') ORDER BY m.m_id";
		try {
			ConnectionHelper.getInstance().open();
			conn = ConnectionHelper.getInstance().getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setLong(1, personId);
			statement.setString(2, measureName);
			long timestamp = after.getTime();
			statement.setDate(3, new java.sql.Date(timestamp));			
			ResultSet rs = statement.executeQuery();
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
	
	public List<Measure> getAllBeforeDate(Long personId, String measureName, Date before) {
		List<Measure> list = new ArrayList<Measure>();
		Connection conn = null;
		String query = "SELECT m.m_id, m.value, m.created, mt.name, mt.type FROM measure m INNER JOIN measure_type mt ON m.mt_id = mt.mt_id WHERE m.p_id = ? AND mt.name = ? AND m.created < strftime('%Y-%m-%d %H:%M:%f',?/1000,'unixepoch','localtime') ORDER BY m.m_id";
		try {
			ConnectionHelper.getInstance().open();
			conn = ConnectionHelper.getInstance().getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setLong(1, personId);
			statement.setString(2, measureName);
			long timestamp = before.getTime();
			statement.setDate(3, new java.sql.Date(timestamp));
			ResultSet rs = statement.executeQuery();
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
	
	public List<Measure> getAllInDateRange(Long personId, String measureName, Date before, Date after) {
		List<Measure> list = new ArrayList<Measure>();
		Connection conn = null;
		String query = "SELECT m.m_id, m.value, m.created, mt.name, mt.type FROM measure m INNER JOIN measure_type mt ON m.mt_id = mt.mt_id WHERE m.p_id = ? AND mt.name = ? AND m.created < strftime('%Y-%m-%d %H:%M:%f',?/1000,'unixepoch','localtime') AND m.created > strftime('%Y-%m-%d %H:%M:%f',?/1000,'unixepoch','localtime') ORDER BY m.m_id";
		try {
			ConnectionHelper.getInstance().open();
			conn = ConnectionHelper.getInstance().getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setLong(1, personId);
			statement.setString(2, measureName);
			long timestamp = before.getTime();
			statement.setDate(3, new java.sql.Date(timestamp));				
			timestamp = after.getTime();
			statement.setDate(4, new java.sql.Date(timestamp));	
			ResultSet rs = statement.executeQuery();
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
	
	public Measure get(Long personId, String measureName, Long measureId) {
		Measure object = null;
		Connection conn = null;
		String query = "SELECT m.m_id, m.value, m.created, mt.name, mt.type FROM measure m INNER JOIN measure_type mt ON m.mt_id = mt.mt_id WHERE m.p_id = ? AND mt.name = ? AND m.m_id = ?";
		try {
			ConnectionHelper.getInstance().open();
			conn = ConnectionHelper.getInstance().getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setLong(1, personId);
			statement.setString(2, measureName);
			statement.setLong(3, measureId);		
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				object = processRow(rs);
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
		return object;
    }
	
	public Measure getLatest(Long personId, String measureName) {
		Measure object = null;
		Connection conn = null;
		String query = "SELECT * FROM measure m INNER JOIN measure_type mt ON m.mt_id = mt.mt_id WHERE m.m_id IN (SELECT m.m_id FROM measure m INNER JOIN measure_type mt ON m.mt_id = mt.mt_id WHERE m.p_id = ? AND mt.name = ? GROUP BY m.p_id HAVING MAX(m.created))";
		try {
			ConnectionHelper.getInstance().open();
			conn = ConnectionHelper.getInstance().getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setLong(1, personId);
			statement.setString(2, measureName);	
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				object = processRow(rs);
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
		return object;
    }
	
	public Long create(Long personId, String measureName, Measure measure) {
		Long measureId = null;
		Connection conn = null;
		String query = "INSERT INTO measure (value, created, p_id, mt_id) VALUES (?, strftime('%Y-%m-%d %H:%M:%f',?/1000,'unixepoch','localtime'), ?, (SELECT mt_id FROM measure_type WHERE name = ?))";
		try {
			ConnectionHelper.getInstance().open();
			conn = ConnectionHelper.getInstance().getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setDouble(1, Double.parseDouble(measure.getValue()));
			Date now = new Date();
			if (measure.getCreated() == null) {
				measure.setCreated(now);							
			}			
			long timestamp = measure.getCreated().getTime();
			java.sql.Date created = new java.sql.Date(timestamp);
			statement.setDate(2, created);
			statement.setLong(3, personId);
			statement.setString(4, measureName);
			statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            // Update the id in the returned object. This is important this value must be returned to the client.
            measureId = rs.getLong(1);
		} catch (SQLException e) {
			System.err.println(e.toString());
		} finally {
			try {
				ConnectionHelper.getInstance().close();
			} catch (SQLException e) {
				System.err.println(e.toString());
			}
		}
		return measureId;
    }
	
	public Measure update(Long measureId, Measure measure) {
		measure.setMid(measureId);
		Connection conn = null;
		String query = "UPDATE measure SET value = ? WHERE m_id = ?";
		try {
			ConnectionHelper.getInstance().open();
			conn = ConnectionHelper.getInstance().getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setDouble(1, Double.parseDouble(measure.getValue()));
			statement.setLong(2, measureId);
			statement.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.toString());
		} finally {
			try {
				ConnectionHelper.getInstance().close();
			} catch (SQLException e) {
				System.err.println(e.toString());
			}
		}
		return measure;
    }
	
	public boolean delete(Long measureId) {
		int count = 0;
		Connection conn = null;
		String query = "DELETE FROM measure WHERE m_id = ?";
		try {
			ConnectionHelper.getInstance().open();
			conn = ConnectionHelper.getInstance().getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setLong(1, measureId);
			count = statement.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.toString());
		} finally {
			try {
				ConnectionHelper.getInstance().close();
			} catch (SQLException e) {
				System.err.println(e.toString());
			}
		}
		return (count > 0);
    }
	
	private Measure processRow(ResultSet rs) throws SQLException {
		Long measureId = rs.getLong("m_id");
		Double value = rs.getDouble("value");
		String valueType = rs.getString("type");
		java.sql.Date date = rs.getDate("created");
		Date created = new Date(date.getTime());		
		String valueString = null;
		if ("double".equals(valueType)) {
			valueString = String.valueOf(value);
		} else if ("integer".equals(valueType)) {
			valueString = String.valueOf(value.intValue());
		} 
    	return new Measure(measureId, valueString, created);
    }
}
