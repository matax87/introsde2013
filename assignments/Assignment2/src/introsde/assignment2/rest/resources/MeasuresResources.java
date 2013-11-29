package introsde.assignment2.rest.resources;

import introsde.assignment2.rest.dao.MeasureTypeDao;
import introsde.assignment2.rest.model.MeasureType;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/measures")
public class MeasuresResources {

	// Return the list of supported measures, will match a GET on the endpoint: /measures
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String availability() {
    	List<MeasureType> list = MeasureTypeDao.getInstance().getAll();
    	StringBuilder sb = new StringBuilder();
    	for (MeasureType item : list) {
    		if (list.indexOf(item) > 0) {
    			sb.append(',');
    		}
    		sb.append(item.getName());
    	}
    	return sb.toString();
    }
}
