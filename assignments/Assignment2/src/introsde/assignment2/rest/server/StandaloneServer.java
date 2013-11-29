package introsde.assignment2.rest.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Properties;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

public class StandaloneServer {

	public static void main(String[] args) throws IllegalArgumentException,
			IOException {
		// load port from properties file
		Properties prop = new Properties();
		InputStream in = StandaloneServer.class.getClassLoader().getResourceAsStream("config.properties");
		prop.load(in);
		String port = prop.getProperty("serverPort");
		String protocol = "http://";		
		String hostname = InetAddress.getLocalHost().getHostAddress();
		if (hostname.equals("127.0.0.1")) {
			hostname = "localhost";
		}

		String baseUrl = protocol + hostname + ":" + port + "/";

		final HttpServer server = HttpServerFactory.create(baseUrl);
		server.start();
		System.out.println("server starts on " + baseUrl
				+ "\n [kill the process to exit]");
	}
}
