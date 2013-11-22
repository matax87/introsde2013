import generated.*;

import javax.xml.bind.*;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Schema;
import org.xml.sax.SAXException;
import java.io.*;

/**
 *
 * @author Matteo Matassoni
 */
public class PeopleUnMarshaller {

    final String xsdFilename = "people.xsd";
    
	public PeopleType unMarshall(File xmlDocument) throws JAXBException, SAXException {
		JAXBContext jaxbContext = JAXBContext.newInstance("generated");

        Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		Schema schema = schemaFactory.newSchema(new File(xsdFilename));
		unMarshaller.setSchema(schema);
		CustomValidationEventHandler validationEventHandler = new CustomValidationEventHandler();
		unMarshaller.setEventHandler(validationEventHandler);

		JAXBElement<PeopleType> peopleElement = (JAXBElement<PeopleType>) unMarshaller.unmarshal(xmlDocument);
		PeopleType people = peopleElement.getValue();
		return people;
	}

	class CustomValidationEventHandler implements ValidationEventHandler {
		public boolean handleEvent(ValidationEvent event) {
			if (event.getSeverity() == ValidationEvent.WARNING) {
				return true;
			}
			if ((event.getSeverity() == ValidationEvent.ERROR)
					|| (event.getSeverity() == ValidationEvent.FATAL_ERROR)) {

				System.out.println("Validation Error:" + event.getMessage());

				ValidationEventLocator locator = event.getLocator();
				System.out.println("at line number:" + locator.getLineNumber());
				System.out.println("Unmarshalling Terminated");
				return false;
			}
			return true;
		}
	}
}
