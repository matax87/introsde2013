package introsde.jaxb.common;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.Validator;
import javax.xml.validation.Schema;

@SuppressWarnings("deprecation")
public class ValidatingJAXBContext extends JAXBContext {

	private JAXBContext context;
	private Schema schema;
	private String schemaLocation;
	private boolean formattedOutput;
	private String encoding;
	private ValidationEventHandler validationEventHandler;

	public ValidatingJAXBContext(JAXBContext context, Schema schema,
			String schemaLocation, boolean formattedOutput, String encoding,
			ValidationEventHandler validationEventHandler) {
		this.context = context;
		this.schema = schema;
		this.schemaLocation = schemaLocation;
		this.formattedOutput = formattedOutput;
		this.encoding = encoding;
		this.validationEventHandler = validationEventHandler;
	}

	@Override
	public Marshaller createMarshaller() throws JAXBException {
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formattedOutput);
		
		if (schema != null) {
			marshaller.setSchema(schema);
		}
		
		if (validationEventHandler != null) {
			marshaller.setEventHandler(validationEventHandler);
		}
		
		if (schemaLocation != null) {
			marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, schemaLocation);
		}

		if (encoding != null) {
			marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
		}

		return marshaller;
	}

	@Override
	public Unmarshaller createUnmarshaller() throws JAXBException {
		Unmarshaller unmarshaller = context.createUnmarshaller();

		if (validationEventHandler != null) {
			unmarshaller.setEventHandler(validationEventHandler);
		}

		if (schema != null) {
			unmarshaller.setSchema(schema);
		}

		return unmarshaller;
	}

	@Override
	public Validator createValidator() throws JAXBException {
		return context.createValidator();
	}

}
