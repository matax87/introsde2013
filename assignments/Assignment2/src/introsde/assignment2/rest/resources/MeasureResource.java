package introsde.assignment2.rest.resources;

import introsde.assignment2.rest.dao.MeasureDao;
import introsde.assignment2.rest.model.Measure;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class MeasureResource {
	
	// Return a measure, will match a GET on the endpoint: /measure/{measure_id}
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Measure get(@PathParam("personId") Long personId,
					   @PathParam("measure") String measureName,
					   @PathParam("mid") Long measureId) {
		return MeasureDao.getInstance().get(personId, measureName, measureId);  	
	}

	// Update a measure, will match a PUT on the endpoint: /measure/{measure_id}
	@PUT
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Measure update(@PathParam("personId") Long personId,
						  @PathParam("measure") String measureName,
			   			  @PathParam("mid") Long measureId,
			   			  Measure measure) {
		return MeasureDao.getInstance().update(measureId, measure);
	}

	// Delete a measure, will match a PUT on the endpoint: /measure/{measure_id}
	@DELETE
	public void delete(@PathParam("personId") Long personId,
					   @PathParam("measure") String measureName,
			   		   @PathParam("mid") Long measureId) {
		MeasureDao.getInstance().delete(measureId);
	}
}
