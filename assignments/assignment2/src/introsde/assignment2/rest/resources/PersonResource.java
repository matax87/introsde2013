package introsde.assignment2.rest.resources;

import introsde.assignment2.rest.dao.PersonDao;
import introsde.assignment2.rest.model.Person;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class PersonResource {
	
	// Return a person, will match a GET on the endpoint: /person/{person_id}
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Person get(@PathParam("personId") Integer personId) {
    	// check existence of the resource
    	Person person = PersonDao.getInstance().get(personId);    	
    	if (person == null)
			throw new WebApplicationException(Response.Status.NOT_FOUND);
    	return person;
    }
    
    // Update a person, will match a PUT on the endpoint: /person/{person_id}
    @PUT
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Person update(@PathParam("personId") Integer personId, Person person) {
    	return PersonDao.getInstance().update(personId, person);
    }
    
    // Delete a person, will match a PUT on the endpoint: /person/{person_id}
    @DELETE
    public void delete(@PathParam("personId") Integer personId) {
    	PersonDao.getInstance().delete(personId);
    }
    
    /*// Forward call to endpoints with the form of /person/{person_id}/weight to the weights sub-resource 
    @Path("/weight")
    public WeightsResource weightsSubResource(@Context ResourceContext context) {
    	return context.getResource(WeightsResource.class);
    }
    
    // Forward call to endpoints with the form of /person/{person_id}/height to the heights sub-resource 
    @Path("/height")
    public HeightsResource heightsSubResource(@Context ResourceContext context) {
    	return context.getResource(HeightsResource.class);
    }
    
    // Forward call to endpoints with the form of /person/{person_id}/activity to the activities sub-resource 
    @Path("/activity")
    public ActivitiesResource activitiesSubResource(@Context ResourceContext context) {
    	return context.getResource(ActivitiesResource.class);
    }*/
}
