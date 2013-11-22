import generated.*;

import javax.xml.bind.*;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Schema;
import org.xml.sax.SAXException;
import java.io.*;
import java.util.List;

/**
 *
 * @author Matteo Matassoni
 */
public class MarshallAndUnMarshall {
    final static String xmlFilename = "people2.xml";    
    
    public static void main(String[] argv) {
        // create some people from the generated JAXB class
        PeopleType people = new PeopleType();
	    List<PersonType> personList = people.getPerson();
	    
	    PersonType person = new PersonType();
        person.setFirstname("George R. R.");
        person.setLastname("Martin");
        HealthProfileType hp = new HealthProfileType();
        hp.setHeight(1.65);
        hp.setWeight(120);
        person.setHealthprofile(hp);
        personList.add(person);
        
        person = new PersonType();
        person.setFirstname("Matteo");
        person.setLastname("Matassoni");
        hp = new HealthProfileType();
        hp.setHeight(1.70);
        hp.setWeight(69.5);
        person.setHealthprofile(hp);
        personList.add(person);	                
				
		try {
		    // marshall will populate a new xml: people2.xml
            PeopleMarshaller marshaller = new PeopleMarshaller(people);
            marshaller.generateXMLDocument(new File(xmlFilename));
        		    
        	// unmarshall will print the people read from xml: people2.xml
		    PeopleUnMarshaller unmarshaller = new PeopleUnMarshaller();
		
		    people = unmarshaller.unMarshall(new File(xmlFilename));
		    personList = people.getPerson();
		    for (PersonType p : personList) {
                System.out.println("PersonType: firstname = " + p.getFirstname() + " lastname = " + p.getLastname());
                hp = p.getHealthprofile();
                if (hp != null) {
                    System.out.println("\tHealthProfileType: height = " + hp.getHeight() + " weight = " + hp.getWeight());
                }
		    }
		} catch (IOException e) {
			System.out.println(e.toString());
		} catch (JAXBException e) {
			System.out.println(e.toString());
		} catch (SAXException e) {
			System.out.println(e.toString());
		}
    }
}