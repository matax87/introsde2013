package introsde.assignment2.rest.client;

import introsde.assignment2.rest.model.Measure;
import introsde.assignment2.rest.model.Person;
import introsde.assignment2.rest.server.StandaloneServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class TestMyWebServer {

	private PersonClient personClient;
	private MeasureClient measureClient;

	public TestMyWebServer(String baseUrl) {
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
		String port = prop.getProperty("serverPort");
		String protocol = "http://";
		String hostname = InetAddress.getLocalHost().getHostAddress();
		if (hostname.equals("127.0.0.1")) {
			hostname = "localhost";
		}

		String baseUrl = protocol + hostname + ":" + port + "/";
		return baseUrl;
	}
	
	private static Long extractIdFromLocation(URI location) {
		String[] segments = location.getPath().split("/");
	    String personIdStr = segments[segments.length - 1];
	    return Long.parseLong(personIdStr);
	}

	public static void main(String[] args) throws IOException, ParseException {
		String responseStr = null;
		String baseURL = getBaseURLString(); 
		TestMyWebServer test = new TestMyWebServer(baseURL);
		
		Person person = new Person();
		person.setFirstname("Carlos Ray");
		person.setLastname("Norris");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		person.setBirthdate(dateFormat.parse("01-01-1945"));
		
		// Create person		
	    URI location = test.getPersonClient().create(person);
	    System.out.println("CREATED PERSON AT " + location.toString());
	    System.out.println();
	    
	    // Get person
	    Long personId = extractIdFromLocation(location);
	    System.out.println("GET PERSON WITH ID = " + personId);
	    responseStr = test.getPersonClient().get(personId);
	    System.out.println(responseStr);
	    System.out.println();
	    
	    // Get all person (aka people)
	    System.out.println("GET PEOPLE");
	    responseStr = test.getPersonClient().getAll();
	    System.out.println(responseStr);
	    System.out.println();
	    
	    // Update person
	    System.out.println("UPDATE PERSON WITH ID = " + personId);
	    person.setFirstname("Chuck");
	    person.setLastname("Norris");
	    person.setBirthdate(dateFormat.parse("10-03-1940"));
	    responseStr = test.getPersonClient().update(personId, person);
	    System.out.println(responseStr);
	    System.out.println();
		
		// Get comma separated value of supported measures
	    System.out.println("GET SUPPPORTED MEASURES");
		String csvSupportedMeasure = test.getMeasureClient().getAvailability().trim();
		List<String> measureTypeList = Arrays.asList(csvSupportedMeasure.split(","));
		System.out.println(csvSupportedMeasure);
		System.out.println();
		
		// Create measures
		if (measureTypeList.contains("weight")) {
			Measure weight = new Measure();
			weight.setValue("70");
			weight.setCreated(new Date());
			location = test.getMeasureClient().create(personId, "weight", weight);
		    System.out.println("CREATED MEASURE AT " + location.toString());
		    System.out.println();
		}
		
		if (measureTypeList.contains("height")) {
			Measure height = new Measure();
			height.setValue("1.78");
			height.setCreated(new Date());
			location = test.getMeasureClient().create(personId, "height", height);
			System.out.println("CREATED MEASURE AT " + location.toString());
		    System.out.println();
		}
		
		if (measureTypeList.contains("steps")) {
			Measure steps = new Measure();
			steps.setValue("1000");
			steps.setCreated(new Date());
			location = test.getMeasureClient().create(personId, "steps", steps);
			System.out.println("CREATED MEASURE AT " + location.toString());
		    System.out.println();
		}
		
		// Get measure
		if (measureTypeList.contains("steps")) {
			Long measureId = extractIdFromLocation(location);
			System.out.println("GET MEASURE WITH TYPE = steps AND PERSON_ID = " + personId);			
			responseStr = test.getMeasureClient().get(personId, "steps", measureId);
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
		
		// Update measure
		if (measureTypeList.contains("steps")) {
			Long measureId = extractIdFromLocation(location);
			System.out.println("UPDATE MEASURE WITH TYPE = steps AND ID = " + measureId);
			Measure steps = new Measure();
			steps.setValue("999999");			
			responseStr = test.getMeasureClient().update(personId, "steps", measureId, steps);
			System.out.println(responseStr);
			System.out.println();
		}
		
		// Delete measure
		if (measureTypeList.contains("steps")) {
			Long measureId = extractIdFromLocation(location);
			test.getMeasureClient().delete(personId, "steps", measureId);
		    System.out.println("DELETED MEASURE WITH TYPE = steps AND ID = " + measureId);
		    System.out.println();
		}
		
		// Delete person
	    test.getPersonClient().delete(personId);
	    System.out.println("DELETED PERSON WITH ID = " + personId);
	    System.out.println();
	}

}
