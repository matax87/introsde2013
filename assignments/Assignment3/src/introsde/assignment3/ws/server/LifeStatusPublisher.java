package introsde.assignment3.ws.server;

import introsde.assignment3.ws.LifeStatusImpl;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.xml.ws.Endpoint;

public class LifeStatusPublisher {

	public static final String PROTOCOL = "http://";
	public static final String BASE_URL = "/ws/lifestatus";
	public static final String DEFAULT_PORT = "6900";	
	
	public static String getEndpointURL() throws UnknownHostException {
		// get the hostname
		String hostname = InetAddress.getLocalHost().getHostAddress(); 
		if (hostname.equals("127.0.0.1")) {
			hostname = "localhost";
		}
		
		// load port from conf.properties or use the default one
		String port = null;
		try {
			// load port from properties file
			Properties prop = new Properties();
			InputStream in = LifeStatusPublisher.class.getClassLoader()
					.getResourceAsStream("config.properties");
			prop.load(in);
			port = prop.getProperty("serverPort");
		} catch (IOException e) {
		} finally {
			if (port == null) {
				port = DEFAULT_PORT;
			}
		}
		String endpointURL = PROTOCOL + hostname + ":" + port + BASE_URL;
		return endpointURL;
	}
	
	public static void main(String[] args) throws UnknownHostException {	
		String endpointURL = getEndpointURL();
		System.out.println("Starting LifeStatus Service...");
		System.out.println("--> Published at = " + endpointURL);
		Endpoint.publish(endpointURL, new LifeStatusImpl());
	}
}
