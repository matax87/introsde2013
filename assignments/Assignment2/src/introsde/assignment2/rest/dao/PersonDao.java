package introsde.assignment2.rest.dao;

import introsde.assignment2.rest.model.HealthProfile;
import introsde.assignment2.rest.model.Measure;
import introsde.assignment2.rest.model.MeasureType;
import introsde.assignment2.rest.model.Person;
import introsde.db.common.ConnectionHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersonDao {

	private static PersonDao instance;
	
	/** A private Constructor prevents any other class from instantiating. */
	private PersonDao() {
		super();    
	}
	
	public static synchronized PersonDao getInstance() {
		if (instance == null) {
			instance = new PersonDao();
		}
		return instance;
	}
	
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	public List<Person> getAll() {
		List<Person> list = new ArrayList<Person>();
		Connection conn = null;		
		try {
			ConnectionHelper.getInstance().open();
			conn = ConnectionHelper.getInstance().getConnection();
			Statement statement = conn.createStatement();
			String query = "SELECT * FROM person ORDER BY p_id";
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
	
	public List<Person> getFilteredMinMeasure(String measureName, Double min) {
		List<Person> list = new ArrayList<Person>();
		Connection conn = null;		
		try {
			ConnectionHelper.getInstance().open();
			conn = ConnectionHelper.getInstance().getConnection();
			String query = "SELECT p.* FROM person p INNER JOIN measure m ON p.p_id = m.p_id INNER JOIN measure_type mt ON m.mt_id = mt.mt_id WHERE mt.name = ? AND m.value >= ? GROUP BY p.p_id HAVING max(m.created) ORDER BY p.p_id";			
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, measureName);
			statement.setDouble(2, min);
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
	
	public List<Person> getFilteredMaxMeasure(String measureName, Double max) {
		List<Person> list = new ArrayList<Person>();
		Connection conn = null;		
		try {
			ConnectionHelper.getInstance().open();
			conn = ConnectionHelper.getInstance().getConnection();
			String query = "SELECT p.* FROM person p INNER JOIN measure m ON p.p_id = m.p_id INNER JOIN measure_type mt ON m.mt_id = mt.mt_id WHERE mt.name = ? AND m.value <= ? GROUP BY p.p_id HAVING max(m.created) ORDER BY p.p_id";			
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, measureName);
			statement.setDouble(2, max);
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
	
	public List<Person> getFilteredMeasureInRange(String measureName, Double min, Double max) {
		List<Person> list = new ArrayList<Person>();
		Connection conn = null;		
		try {
			ConnectionHelper.getInstance().open();
			conn = ConnectionHelper.getInstance().getConnection();			
			String query = "SELECT p.* FROM person p INNER JOIN measure m ON p.p_id = m.p_id INNER JOIN measure_type mt ON m.mt_id = mt.mt_id WHERE mt.name = ? AND m.value >= ? AND m.value <= ? GROUP BY p.p_id HAVING max(m.created) ORDER BY p.p_id";
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, measureName);
			statement.setDouble(2, min);
			statement.setDouble(3, max);
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
	
	public Person get(Long personId) {
		Person person = null;
		Connection conn = null;		
		try {
			ConnectionHelper.getInstance().open();
			conn = ConnectionHelper.getInstance().getConnection();
			String query = "SELECT * FROM person WHERE p_id = ?";
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setLong(1, personId);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				person = processRow(rs);
			}
			List<MeasureType> list = MeasureTypeDao.getInstance().getAll();
	    	HealthProfile hp = new HealthProfile();
	    	for (MeasureType item : list) {
	    		String measureName = item.getName();
	    		Measure measure = MeasureDao.getInstance().getLatest(personId, measureName);
	    		if (measure != null) {
	    			if ("height".equals(measureName)) {
	    				hp.setHeight(Double.parseDouble(measure.getValue()));
	    			} else if ("weight".equals(measureName)) {    		
	    				hp.setWeight(Double.parseDouble(measure.getValue()));
	    			} else if ("steps".equals(measureName)) {    		
	    				hp.setSteps(Integer.parseInt(measure.getValue()));
	    			}	    
	    		}
	    	}
	    	person.setHealthProfile(hp);
		} catch (SQLException e) {
			System.err.println(e.toString());
		} finally {
			try {
				ConnectionHelper.getInstance().close();
			} catch (SQLException e) {
				System.err.println(e.toString());
			}
		}
		return person;
    }
	
	public Long create(Person person) {
		Long personId = null;
		Connection conn = null;
		try {
			ConnectionHelper.getInstance().open();
			conn = ConnectionHelper.getInstance().getConnection();
			String query = "INSERT INTO person (name, lastname, birthdate) VALUES (?, ?, strftime('%Y-%m-%d %H:%M:%f',?/1000,'unixepoch','localtime'))";
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, person.getFirstname());
			statement.setString(2, person.getLastname());
			if (person.getBirthdate() != null) {
				long timestamp = person.getBirthdate().getTime();
				statement.setDate(3, new java.sql.Date(timestamp));
			}	
			statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            // Update the personId in the returned object. This is important this value must be returned to the client.
            personId = rs.getLong(1);
		} catch (SQLException e) {
			System.err.println(e.toString());
		} finally {
			try {
				ConnectionHelper.getInstance().close();
			} catch (SQLException e) {
				System.err.println(e.toString());
			}
		}
		return personId;
    }
	
	public Person update(Long personId, Person person) {
		person.setId(personId);
		Connection conn = null;
		try {
			ConnectionHelper.getInstance().open();
			conn = ConnectionHelper.getInstance().getConnection();
			String query = "UPDATE person SET name = ?, lastname = ?, birthdate = strftime('%Y-%m-%d %H:%M:%f',?/1000,'unixepoch','localtime') WHERE p_id = ?";
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, person.getFirstname());
			statement.setString(2, person.getLastname());
			if (person.getBirthdate() != null) {
				long timestamp = person.getBirthdate().getTime();
				statement.setDate(3, new java.sql.Date(timestamp));
			}			
			statement.setLong(4, personId);
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
		return person;
    }
	
	public boolean delete(Long personId) {
		int count = 0;
		Connection conn = null;
		try {
			ConnectionHelper.getInstance().open();
			conn = ConnectionHelper.getInstance().getConnection();
			String query = "DELETE FROM person WHERE p_id = ?";
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setLong(1, personId);
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
	
	private Person processRow(ResultSet rs) throws SQLException {
		Long personId = rs.getLong("p_id");
		String name = rs.getString("name");
		String lastname = rs.getString("lastname");
		java.sql.Date date = rs.getDate("birthdate");		
		Date birthdate = null;
		if (date != null) {
			birthdate = new Date(date.getTime());
		}
    	return new Person(personId, name, lastname, birthdate);
    }
}
