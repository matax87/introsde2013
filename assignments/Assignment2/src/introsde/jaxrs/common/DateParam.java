package introsde.jaxrs.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

/**
 * A DateParam to validate the format of date parameters received by JAX-RS
 */
public class DateParam {
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final Date DEFAULT_DATE = null;
    
    public static DateParam valueOf(String dateString) {
    	Date date;    	
    	try {
    		if (dateString == null || "null".equals(dateString) || dateString.isEmpty()) {
        		date = DEFAULT_DATE; 
        	} else {
        		date = DATE_FORMAT.parse(dateString);
        	}
        } catch (ParseException pe) {
            throw new WebApplicationException(Status.BAD_REQUEST);
        }
    	
    	return new DateParam(date);
    }
    
    private Date date;

	public DateParam(Date date) {
		super();
		this.date = date;
	}

	public Date getDate() {
		return date;
	}
}
