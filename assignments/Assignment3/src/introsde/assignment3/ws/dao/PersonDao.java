package introsde.assignment3.ws.dao;

import introsde.assignment3.ws.model.HealthProfile;
import introsde.assignment3.ws.model.Person;
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
		    	person.setHealthProfile(HealthProfileDao.getInstance().getLatest(personId));
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
		return person;
    }
	
	public Long create(Person person) {
		if (person == null) {
			return null;
		}
		Long personId = null;
		Connection conn = null;
		try {
			ConnectionHelper.getInstance().open();
			conn = ConnectionHelper.getInstance().getConnection();
			String query = "INSERT INTO person (name, lastname, birthdate) VALUES (?, ?, strftime('%Y-%m-%d %H:%M:%f',?/1000,'unixepoch','localtime'))";
			PreparedStatement statement = conn.prepareStatement(query);
			// set the params of the SQL statement if they are provided or NULL
			if (person.getFirstname() == null) {
				statement.setNull(1, java.sql.Types.VARCHAR);
			} else {
				statement.setString(1, person.getFirstname());
			}
			if (person.getLastname() == null) {
				statement.setNull(2, java.sql.Types.VARCHAR);
			} else {
				statement.setString(2, person.getLastname());
			}
			if (person.getBirthdate() == null) {
				statement.setNull(3, java.sql.Types.DATE);
			} else {
				long timestamp = person.getBirthdate().getTime();
				statement.setDate(3, new java.sql.Date(timestamp));
			}	
			statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            // Update the personId in the returned object. This is important this value must be returned to the client.
            personId = rs.getLong(1);
            // Create also the corresponding healthprofile if it is provided
            HealthProfile hp = person.getHealthProfile();
            if (hp != null) {
            	HealthProfileDao.getInstance().create(personId, hp);
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
		return personId;
    }
	
	public Person update(Person person) {
		if (person == null) {
			return null;
		}
		Person updatedPerson = null;
		Connection conn = null;
		try {
			ConnectionHelper.getInstance().open();
			conn = ConnectionHelper.getInstance().getConnection();
			String query = "UPDATE person SET name = ?, lastname = ?, birthdate = strftime('%Y-%m-%d %H:%M:%f',?/1000,'unixepoch','localtime') WHERE p_id = ?";
			PreparedStatement statement = conn.prepareStatement(query);
			// set the params of the SQL statement if they are provided or NULL
			if (person.getFirstname() == null) {
				statement.setNull(1, java.sql.Types.VARCHAR);
			} else {
				statement.setString(1, person.getFirstname());
			}
			if (person.getLastname() == null) {
				statement.setNull(2, java.sql.Types.VARCHAR);
			} else {
				statement.setString(2, person.getLastname());
			}
			if (person.getBirthdate() == null) {
				statement.setNull(3, java.sql.Types.DATE);
			} else {
				long timestamp = person.getBirthdate().getTime();
				statement.setDate(3, new java.sql.Date(timestamp));
			}
			if (person.getBirthdate() == null) {
				statement.setNull(3, java.sql.Types.DATE);
			} else {
				long timestamp = person.getBirthdate().getTime();
				statement.setDate(3, new java.sql.Date(timestamp));
			}
			if (person.getId() == null) {
				statement.setNull(4, java.sql.Types.INTEGER);
			} else {
				statement.setLong(4, person.getId());
			}
			int count = statement.executeUpdate();
			boolean updated = count > 0;
			if (updated) {
				updatedPerson = person;
				// update also the healthprofile if it is provided
				HealthProfile hp = updatedPerson.getHealthProfile();
	            if (hp != null) {
	            	HealthProfile updatedHealthProfile = HealthProfileDao.getInstance().update(person.getId(), hp);
	            	if (updatedHealthProfile != null) {
	            		updatedPerson.setHealthProfile(updatedHealthProfile);
	            	} else {
	            		Long createdId = HealthProfileDao.getInstance().create(person.getId(), hp);
	            		hp.setId(createdId);
	            	}
	            }
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
		return updatedPerson;
    }
	
	public boolean delete(Long personId) {
		boolean deleted = false;
		Connection conn = null;
		try {
			ConnectionHelper.getInstance().open();
			conn = ConnectionHelper.getInstance().getConnection();
			String query = "DELETE FROM person WHERE p_id = ?";
			PreparedStatement statement = conn.prepareStatement(query);
			if (personId == null) {
				statement.setNull(1, java.sql.Types.INTEGER);
			} else {
				statement.setLong(1, personId);
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
	
	private Person processRow(ResultSet rs) throws SQLException {
		Long personId = rs.getLong("p_id");
		String name = rs.getString("name");
		String lastname = rs.getString("lastname");
		java.sql.Date date = rs.getDate("birthdate");		
		Date birthdate = new Date(date.getTime());
    	return new Person(personId, name, lastname, birthdate);
    }
}
