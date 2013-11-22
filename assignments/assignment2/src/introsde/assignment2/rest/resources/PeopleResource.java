package introsde.assignment2.rest.resources;

import introsde.assignment2.rest.dao.PersonDao;
import introsde.assignment2.rest.model.People;
import introsde.assignment2.rest.model.Person;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
    public People getPeople(@QueryParam("measure") @DefaultValue("") String measure,
    				  @QueryParam("max") @DefaultValue("") String max,
    				  @QueryParam("min") @DefaultValue("") String min) {
    	List<Person> personList = PersonDao.getInstance().getAll();
    	People people = new People(personList);
    	return people;
    }
    
    // Create a person, will match a POST on the endpoint: /person
    @POST
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response create(Person person) {
    	Integer personId = PersonDao.getInstance().create(person);
    	return Response.created(URI.create("/" + personId)).build();
    }
    
    // Forward call to endpoints with the form of /person/{person_id} to the person sub-resource 
    @Path("/{personId}")
    public PersonResource personSubResource(@Context ResourceContext context) {
    	return context.getResource(PersonResource.class);
    }
}
