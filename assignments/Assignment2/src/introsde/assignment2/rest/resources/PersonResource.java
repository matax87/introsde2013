package introsde.assignment2.rest.resources;

import introsde.assignment2.rest.dao.MeasureTypeDao;
import introsde.assignment2.rest.dao.PersonDao;
import introsde.assignment2.rest.model.MeasureType;
import introsde.assignment2.rest.model.Person;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.sun.jersey.api.core.ResourceContext;

public class PersonResource {
	
	// Return a person, will match a GET on the endpoint: /person/{person_id}
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Person get(@PathParam("personId") Long id) {
    	return PersonDao.getInstance().get(id);    	
    }
    
    // Update a person, will match a PUT on the endpoint: /person/{person_id}
    @PUT
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Person update(@PathParam("personId") Long id, Person person) {
    	return PersonDao.getInstance().update(id, person);
    }
    
    // Delete a person, will match a PUT on the endpoint: /person/{person_id}
    @DELETE
    public void delete(@PathParam("personId") Long id) {
    	PersonDao.getInstance().delete(id);
    }
    
    // Forward call to endpoints with the form of /person/{person_id}/{measure} to the measure-history sub-resource 
    @Path("/{measure}")
    public MeasureHistoryResource measureHistorySubResource(@Context ResourceContext context,
    														@PathParam("measure") String measureName) {
    	// check existence of the sub-resource
    	MeasureType measureType = MeasureTypeDao.getInstance().findByName(measureName);    	
    	if (measureType == null)
			throw new WebApplicationException(Response.Status.NOT_FOUND);
    	
    	return context.getResource(MeasureHistoryResource.class);
    }
}
