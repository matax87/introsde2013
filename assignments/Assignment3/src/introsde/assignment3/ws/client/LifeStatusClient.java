package introsde.assignment3.ws.client;

import introsde.assignment3.ws.LifeStatus;
import introsde.assignment3.ws.model.HealthProfile;
import introsde.assignment3.ws.model.HealthProfileHistory;
import introsde.assignment3.ws.model.Person;
import introsde.assignment3.ws.server.LifeStatusPublisher;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Date;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class LifeStatusClient {

	public static void main(String[] args) throws MalformedURLException, UnknownHostException {
		URL wsdlURL = new URL(LifeStatusPublisher.getEndpointURL() + "?wsdl");
		QName qname = new QName("http://ws.assignment3.introsde/", "LifeStatus");
		Service service = Service.create(wsdlURL, qname);
		LifeStatus lifeStatus = service.getPort(LifeStatus.class);
		
		
		// readPerson
		System.out.println("READING PERSON WITH ID = 1...");
		Person p = lifeStatus.readPerson(1);
		printResult(p.toString());
		
		// createPerson		
		p = new Person();
		p.setFirstname("Test_name");
		p.setLastname("Test_lastname");
		p.setBirthdate(new Date());
		System.out.println("CREATING PERSON WITH PERSON = " + p + "...");
		int createPersonId = lifeStatus.createPerson(p);
		if (createPersonId > 0) {
			p.setId(Long.valueOf(createPersonId));
			printResult(String.valueOf(createPersonId));
		} else {
			printError(createPersonId);
		}
		
		// updatePerson		
		p.setFirstname("Test_updated_name");
		p.setLastname("Test_updated_lastname");
		System.out.println("UPDATING PERSON WITH PERSON = " + p + "...");
		int updatePersonId = lifeStatus.updatePerson(p);
		if (updatePersonId > 0) {
			printResult(String.valueOf(updatePersonId));
		} else {
			printError(updatePersonId);
		}
		
		// addPersonHealthProfile
		HealthProfile hp = new HealthProfile();
		hp.setWeight(100.3);
		hp.setHeight(184.);
		hp.setCreated(new Date());
		System.out.println("CREATING HEALTHPROFILE WITH PERSON_ID = "
				+ createPersonId + " HEALTHPROFILE = " + hp + "...");
		int createdHpId = lifeStatus.addPersonHealthProfile(createPersonId,
				hp);
		if (createdHpId > 0) {
			hp.setId(Long.valueOf(createdHpId));
			printResult(String.valueOf(createdHpId));
		} else {
			printError(createdHpId);
		}	
		
		// updatePersonHealthProfile
		hp.setWeight(null);
		hp.setHeight(null);
		hp.setSteps(2000);
		hp.setCalories(1800);
		System.out.println("UPDATING HEALTHPROFILE WITH PERSON_ID = " + createPersonId + " HEALTHPROFILE = " + hp + "...");
		int updatedHpId = lifeStatus.updatePersonHealthProfile(createPersonId, hp);
		if (updatedHpId > 0) {
			printResult(String.valueOf(updatedHpId));
		} else {
			printError(updatedHpId);
		}
		
		// getHealthProfileHistory
		System.out.println("GETTING HEALTHPROFILE HISTORY WITH PERSON_ID = " + createPersonId + "...");
		HealthProfileHistory hpHistory = lifeStatus.getHealthProfileHistory(createPersonId);
		printResult(hpHistory.getHealthProfileList().toString());
		
		// deletePerson
		System.out.println("DELETING PERSON WITH ID = " + createPersonId + "...");
		int deleteResponse = lifeStatus.deletePerson(createPersonId);
		boolean deleted = (deleteResponse == 0);
		if (deleted) {
			printResult(String.valueOf(deleteResponse));
		} else {
			printError(deleteResponse);
		}
	}
	
	private static void printResult(String s) {
		System.out.println("output: " + s);
	}
	
	private static void printError(int error) {
		printResult("error (" + error + ")");
	}
}
