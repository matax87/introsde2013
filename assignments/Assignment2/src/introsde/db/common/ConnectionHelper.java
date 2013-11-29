package introsde.db.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionHelper {
	
	private static ConnectionHelper instance;
	
	private String url;
	private Connection connection;	

	/** A private Constructor prevents any other class from instantiating. 
	 * 
	 * @param url
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	private ConnectionHelper() {
		super();
		try {
			// load the sqlite-JDBC driver using the current class loader
			Class.forName("org.sqlite.JDBC");
		
			// load URL from properties file
			Properties prop = new Properties();
			InputStream in = getClass().getClassLoader().getResourceAsStream("config.properties");
			prop.load(in);
			this.url = prop.getProperty("dbURL");
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public static synchronized ConnectionHelper getInstance() {
		if (instance == null) {
			instance = new ConnectionHelper();
		}
		return instance;
	}
	
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	public void open() throws SQLException {
		if (connection == null || connection.isClosed()) {
			// create a database connection
			connection = DriverManager.getConnection(url);
		}	
	}
	
	public void close() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}	
	}

	public Connection getConnection() {
		return connection;
	}
}