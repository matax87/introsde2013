package introsde.assignment2.rest.resources;

import introsde.assignment2.rest.dao.PersonDao;
import introsde.assignment2.rest.model.People;
import introsde.assignment2.rest.model.Person;

import java.net.URI;
import java.util.ArrayList;
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

@Path("/person")
public class PeopleResource {
	
	@Context
	UriInfo uriInfo;
	
	// Return the list of people, will match a GET on the endpoint: /person
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public People get(@DefaultValue("") @QueryParam("measure") String measureName,
    					  @DefaultValue("") @QueryParam("min") String minString,
    					  @DefaultValue("") @QueryParam("max") String maxString) {
    	Double min = null;
    	Double max= null;    	
    	try {
    		min = Double.parseDouble(minString);        	
		} catch (NumberFormatException e) {}
    	try {
    		max = Double.parseDouble(maxString);        	
		} catch (NumberFormatException e) {}
    	List<Person> personList = new ArrayList<Person>();
    	if (min != null && max != null) {
    		personList.addAll(PersonDao.getInstance().getFilteredMeasureInRange(measureName, min, max));
    	} else if (min != null) {
    		personList.addAll(PersonDao.getInstance().getFilteredMinMeasure(measureName, min));
    	} else if (max != null) {
    		personList.addAll(PersonDao.getInstance().getFilteredMaxMeasure(measureName, max));
    	} else {
    		personList.addAll(PersonDao.getInstance().getAll());
    	}
    	return new People(personList);
    }
    
    // Create a person, will match a POST on the endpoint: /person
    @POST
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response create(Person person) {
    	Long id = PersonDao.getInstance().create(person);
    	return Response.created(URI.create("/" + id)).build();
    }
    
    // Forward call to endpoints with the form of /person/{person_id} to the person sub-resource
    // support only a path param only composed by digits
    @Path("/{personId : \\d+}")
    public PersonResource personSubResource(@Context ResourceContext context,
    										@PathParam("personId") Long personId) {
    	// check existence of the sub-resource
    	Person person = PersonDao.getInstance().get(personId);    	
    	if (person == null)
			throw new WebApplicationException(Response.Status.NOT_FOUND);
    	
    	return context.getResource(PersonResource.class);
    }
}
