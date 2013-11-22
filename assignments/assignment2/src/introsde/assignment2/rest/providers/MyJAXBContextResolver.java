package introsde.assignment2.rest.providers;

import introsde.jaxb.common.ValidatingJAXBContext;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

@Provider
public class MyJAXBContextResolver implements ContextResolver<JAXBContext> {
	
	private ValidatingJAXBContext ctx;
	private static final String packageContextPath = "introsde.assignment2.rest.model";
	
	public MyJAXBContextResolver(@Context ServletContext servletContext) {		
		try {
			JAXBContext context = JAXBContext.newInstance(packageContextPath);
			
			URL schemaLocation = servletContext.getResource("people.xsd");
			System.out.println("Schema location:" + schemaLocation.toString());
			Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(schemaLocation);
			
			ctx = new ValidatingJAXBContext(context, schema, schemaLocation.toString(), false, null, new MyValidationEventHandler());
		} catch (JAXBException e) {
			throw new RuntimeException("Unable to create JAXB context for package: \"" + packageContextPath + "\"", e);
		} catch (SAXException e) {
			throw new RuntimeException("Error obtaining XML schema", e);
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error obtaining XML schema", e);
		}		
	}

	@Override
	public JAXBContext getContext(Class<?> type) {
		System.out.println(type.getPackage().getName());
		if (packageContextPath.equals(type.getPackage().getName())) {
			System.out.println("OK");
			return ctx;
		} else {
			return null;
		}
	}
	
	class MyValidationEventHandler implements ValidationEventHandler {
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
