package introsde.assignment2.rest.resources;

import introsde.assignment2.rest.dao.MeasureDao;
import introsde.assignment2.rest.model.Measure;
import introsde.assignment2.rest.model.MeasureHistory;
import introsde.jaxrs.common.DateParam;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.sun.jersey.api.core.ResourceContext;

public class MeasureHistoryResource {
	
	@Context
	UriInfo uriInfo;
	
	// Return the list of measure, will match a GET on the endpoint: /person/{id}/weight
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MeasureHistory get(@PathParam("personId") Long personId,
    						  @PathParam("measure") String measureName,
    						  @DefaultValue("") @QueryParam("beforeDate") DateParam beforeDate,
    						  @DefaultValue("") @QueryParam("afterDate") DateParam afterDate) {
    	Date before = beforeDate.getDate();
    	Date after = afterDate.getDate();   
    	
    	List<Measure> measureList = new ArrayList<Measure>();
    	if (before != null && after != null) {
    		measureList.addAll(MeasureDao.getInstance().getAllInDateRange(personId, measureName, before, after));
    	} else if (before != null) {
    		measureList.addAll(MeasureDao.getInstance().getAllBeforeDate(personId, measureName, before));
    	} else if (after != null) {
    		measureList.addAll(MeasureDao.getInstance().getAllAfterDate(personId, measureName, after));
    	} else {
    		measureList.addAll(MeasureDao.getInstance().getAll(personId, measureName));
    	}
    	return new MeasureHistory(measureList);
    }
    
    // Create a measure, will match a POST on the endpoint: /person/{id}/weight
    @POST
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response create(@PathParam("personId") Long personId, 
    					   @PathParam("measure") String measureName,
    					   Measure measure) {
    	Long measureId = MeasureDao.getInstance().create(personId, measureName, measure);
    	return Response.created(URI.create("/" + measureId)).build();
    }
    
    // Forward call to endpoints with the form of /person/{id}/weight/{mid} to the measure sub-resource
    // support only a path param only composed by digits
    @Path("/{mid : \\d+}")
    public MeasureResource measureSubResource(@Context ResourceContext context,
    										  @PathParam("personId") Long personId,
    										  @PathParam("measure") String measureName,
    										  @PathParam("mid") Long measureId) {
    	// check existence of the sub-resource
    	Measure measure = MeasureDao.getInstance().get(personId, measureName, measureId);    	
    	if (measure == null)
			throw new WebApplicationException(Response.Status.NOT_FOUND);
    	
    	return context.getResource(MeasureResource.class);
    }
}
