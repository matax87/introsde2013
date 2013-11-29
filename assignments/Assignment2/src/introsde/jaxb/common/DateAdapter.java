package introsde.jaxb.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * An XmlAdapter to convert the format of date parameters received by JAXB
 */
public class DateAdapter extends XmlAdapter<String, Date> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public String marshal(Date v) throws Exception {
        return DATE_FORMAT.format(v);
    }

    @Override
    public Date unmarshal(String v) throws Exception {
        return DATE_FORMAT.parse(v);
    }
}
