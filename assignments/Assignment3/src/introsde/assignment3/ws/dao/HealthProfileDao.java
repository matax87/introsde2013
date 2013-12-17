package introsde.assignment3.ws.dao;

import introsde.assignment3.ws.model.HealthProfile;
import introsde.db.common.ConnectionHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HealthProfileDao {

	private static HealthProfileDao instance;
	
	/** A private Constructor prevents any other class from instantiating. */
	private HealthProfileDao() {
		super();    
	}
	
	public static synchronized HealthProfileDao getInstance() {
		if (instance == null) {
			instance = new HealthProfileDao();
		}
		return instance;
	}
	
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	public List<HealthProfile> getAll(Long personId) {
		List<HealthProfile> list = new ArrayList<HealthProfile>();
		Connection conn = null;
		String query = "SELECT * FROM healthprofile WHERE person = ? ORDER BY created DESC";
		try {
			ConnectionHelper.getInstance().open();
			conn = ConnectionHelper.getInstance().getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setLong(1, personId);
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
	
	public HealthProfile get(Long healthProfileId) {
		HealthProfile object = null;
		Connection conn = null;
		String query = "SELECT * FROM healthprofile WHERE hp_id = ?";
		try {
			ConnectionHelper.getInstance().open();
			conn = ConnectionHelper.getInstance().getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setLong(1, healthProfileId);	
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
	
	public HealthProfile getLatest(Long personId) {
		HealthProfile object = null;
		Connection conn = null;
		String query = "SELECT * FROM healthprofile WHERE hp_id IN (SELECT hp_id FROM healthprofile WHERE person = ? GROUP BY person HAVING MAX(created))";
		try {
			ConnectionHelper.getInstance().open();
			conn = ConnectionHelper.getInstance().getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setLong(1, personId);	
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
	
	public Long create(Long personId, HealthProfile hp) {
		if (hp == null) {
			return null;
		}
		Long hpId = null;
		Connection conn = null;
		String query = "INSERT INTO healthprofile (created, weight, height, steps, calories, person) VALUES (strftime('%Y-%m-%d %H:%M:%f',?/1000,'unixepoch','localtime'), ?, ?, ?, ?, ?)";
		try {
			ConnectionHelper.getInstance().open();
			conn = ConnectionHelper.getInstance().getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			// set the params of the SQL statement if they are provided or NULL
			if (hp.getCreated() == null) {
				hp.setCreated(new Date());							
			}			
			long timestamp = hp.getCreated().getTime();
			statement.setDate(1, new java.sql.Date(timestamp));
			if (hp.getWeight() == null) {
				statement.setNull(2, java.sql.Types.FLOAT);
			} else {
				statement.setDouble(2, hp.getWeight());
			}
			if (hp.getHeight() == null) {
				statement.setNull(3, java.sql.Types.FLOAT);
			} else {
				statement.setDouble(3, hp.getHeight());
			}
			if (hp.getSteps() == null) {
				statement.setNull(4, java.sql.Types.INTEGER);
			} else {
				statement.setDouble(4, hp.getSteps());
			}		
			if (hp.getCalories() == null) {
				statement.setNull(5, java.sql.Types.INTEGER);
			} else {
				statement.setDouble(5, hp.getCalories());
			}	
			if (personId == null) {
				statement.setNull(6, java.sql.Types.INTEGER);							
			} else {
				statement.setLong(6, personId);
			}	
			statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            // Update the id in the returned object. This is important this value must be returned to the client.
            hpId = rs.getLong(1);
		} catch (SQLException e) {
			System.err.println(e.toString());
		} finally {
			try {
				ConnectionHelper.getInstance().close();
			} catch (SQLException e) {
				System.err.println(e.toString());
			}
		}
		return hpId;
    }
	
	public HealthProfile update(Long personId, HealthProfile hp) {
		if (hp == null) {
			return null;
		}
		HealthProfile updatedHealthProfile = null;
		Connection conn = null;
		String query = "UPDATE healthprofile SET weight = ?, height = ?, steps = ?, calories = ? WHERE hp_id = ? AND person = ?";
		try {
			ConnectionHelper.getInstance().open();
			conn = ConnectionHelper.getInstance().getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			// set the params of the SQL statement if they are provided or NULL			
			if (hp.getWeight() == null) {
				statement.setNull(1, java.sql.Types.FLOAT);
			} else {
				statement.setDouble(1, hp.getWeight());
			}
			if (hp.getHeight() == null) {
				statement.setNull(2, java.sql.Types.FLOAT);
			} else {
				statement.setDouble(2, hp.getHeight());
			}
			if (hp.getSteps() == null) {
				statement.setNull(3, java.sql.Types.INTEGER);
			} else {
				statement.setDouble(3, hp.getSteps());
			}
			if (hp.getCalories() == null) {
				statement.setNull(4, java.sql.Types.INTEGER);
			} else {
				statement.setDouble(4, hp.getCalories());
			}
			if (hp.getId() == null) {
				statement.setNull(5, java.sql.Types.INTEGER);
			} else {
				statement.setLong(5, hp.getId());
			}
			if (personId == null) {
				statement.setNull(6, java.sql.Types.INTEGER);
			} else {
				statement.setLong(6, personId);
			}
			int count = statement.executeUpdate();
			boolean updated = count > 0;
			if (updated) {
				updatedHealthProfile = hp;
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
		return updatedHealthProfile;
	}
	
	public boolean delete(Long personId, Long healthProfileId) {
		boolean deleted = false;
		Connection conn = null;
		String query = "DELETE FROM healthprofile WHERE hp_id = ? AND person = ?";
		try {
			ConnectionHelper.getInstance().open();
			conn = ConnectionHelper.getInstance().getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			if (healthProfileId == null) {
				statement.setNull(1, java.sql.Types.INTEGER);
			} else {
				statement.setLong(1, healthProfileId);
			}
			if (personId == null) {
				statement.setNull(2, java.sql.Types.INTEGER);
			} else {
				statement.setLong(2, personId);
			}
			int count = statement.executeUpdate();
			deleted = count > 0;
		} catch (SQLException e) {
			System.err.println(e.toString());
		} finally {
			try {
				ConnectionHelper.getInstance().close();
			} catch (SQLException e) {
				System.err.println(e.toString());
			}
		}
		return deleted;
    }
	
	private HealthProfile processRow(ResultSet rs) throws SQLException {
		HealthProfile hp = new HealthProfile();
		
		Long healthProfileId = rs.getLong("hp_id");
		hp.setId(healthProfileId);
		java.sql.Date date = rs.getDate("created");
		Date created = new Date(date.getTime());
		hp.setCreated(created);
		Double weight = rs.getDouble("weight");
		if (!rs.wasNull()) {
			hp.setWeight(weight);
		}
		Double height = rs.getDouble("height");
		if (!rs.wasNull()) {
			hp.setHeight(height);
		}
		Integer steps = rs.getInt("steps");
		if (!rs.wasNull()) {
			hp.setSteps(steps);
		}
		Integer calories = rs.getInt("calories");
		if (!rs.wasNull()) {
			hp.setCalories(calories);
		}
    	return hp;
    }
}
