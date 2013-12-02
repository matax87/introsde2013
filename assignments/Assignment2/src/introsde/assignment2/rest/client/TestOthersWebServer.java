package introsde.assignment2.rest.client;

import introsde.assignment2.rest.server.StandaloneServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class TestOthersWebServer {

	private PersonClient personClient;
	private MeasureClient measureClient;

	public TestOthersWebServer(String baseUrl) {
		super();
		personClient = new PersonClient(baseUrl);
		measureClient = new MeasureClient(baseUrl);
	}
	
	public PersonClient getPersonClient() {
		return personClient;
	}

	public MeasureClient getMeasureClient() {
		return measureClient;
	}

	private static String getBaseURLString() throws IOException {
		// load port from properties file
		Properties prop = new Properties();
		InputStream in = StandaloneServer.class.getClassLoader()
				.getResourceAsStream("config.properties");
		prop.load(in);
		String port = prop.getProperty("clientPort");
		String protocol = "http://";
		String hostname = InetAddress.getLocalHost().getHostAddress();
		if (hostname.equals("127.0.0.1")) {
			hostname = "localhost";
		}

		String baseUrl = protocol + hostname + ":" + port + "/";
		return baseUrl;
	}

	public static void main(String[] args) throws IOException, ParseException {
		String responseStr = null;
		String baseURL = getBaseURLString(); 
		TestOthersWebServer test = new TestOthersWebServer(baseURL);
	    
	    // Get person
	    Long personId = Long.valueOf(1);
	    System.out.println("GET PERSON WITH ID = " + personId);
	    responseStr = test.getPersonClient().get(personId);
	    System.out.println(responseStr);
	    System.out.println();
	    
	    // Get all person (aka people)
	    System.out.println("GET PEOPLE");
	    responseStr = test.getPersonClient().getAll();
	    System.out.println(responseStr);
	    System.out.println();
		
		// Get comma separated value of supported measures
	    System.out.println("GET SUPPPORTED MEASURES");
		String csvSupportedMeasure = test.getMeasureClient().getAvailability();		
		System.out.println(csvSupportedMeasure);
		System.out.println();
				
		if (csvSupportedMeasure != null) {
			List<String> measureTypeList = Arrays.asList(csvSupportedMeasure.trim().split(","));
			
			// Get measure
			if (!measureTypeList.isEmpty()) {
				String measureName = measureTypeList.get(0);
				Long measureId = Long.valueOf(1);
				System.out.println("GET MEASURE WITH TYPE = " + measureName + " AND PERSON_ID = " + personId);			
				responseStr = test.getMeasureClient().get(personId, measureName, measureId);
				System.out.println(responseStr);
				System.out.println();
			}
			
			// Get measure histories
			for (String measureName : measureTypeList) {
				System.out.println("GET MEASURE-HISTORY WITH TYPE = " + measureName + " AND PERSON_ID = " + personId);
				responseStr = test.getMeasureClient().getAll(personId, measureName);
			    System.out.println(responseStr);
			    System.out.println();
			}
		}
	}

}
