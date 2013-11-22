import generated.*;

import javax.xml.bind.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Matteo Matassoni
 */
public class PeopleMarshaller {

    private PeopleType people;
    
    public PeopleMarshaller(PeopleType people) {
        this.people = people;
    }
    
    public void generateXMLDocument(File xmlDocument) throws JAXBException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance("generated");
		Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty("jaxb.formatted.output", new Boolean(true));
		generated.ObjectFactory factory = new generated.ObjectFactory();
			
		JAXBElement<PeopleType> peopleElement = factory.createPeople(this.people);
	    marshaller.marshal(peopleElement, new FileOutputStream(xmlDocument));
	}
}
