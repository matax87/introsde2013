package introsde.assignment2.rest.client;

import introsde.assignment2.rest.model.Measure;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class MeasureClient {

	private final String baseUrl;

	private final Client webServiceClient;
	
	public MeasureClient(String baseUrl) {
	    this.baseUrl = baseUrl;
	    ClientConfig cc = new DefaultClientConfig();
	    webServiceClient = Client.create(cc);
	  }
	
	// GET <baseurl>/measures
	public String getAvailability() {
		ClientResponse response = webServiceClient.resource(UriBuilder.fromUri(baseUrl).path("/measures").build()).accept(MediaType.TEXT_PLAIN).get(ClientResponse.class);
		
		if (response.getStatus() != 200) {
			return "NOT IMPLEMENTED";
		} else {
			return response.getEntity(String.class);
		}
	}

	// POST <baseurl>/person/{id}/{measure}
	public URI create(Long personId, String measureName, Measure measure) {
		ClientResponse response = webServiceClient.resource(UriBuilder.fromUri(baseUrl).path("/person/" + personId).path(measureName).build()).entity(measure).post(ClientResponse.class);
		return response.getLocation();
	}

	// GET <baseurl>/person/{id}/{measure}
	public String getAll(Long personId, String measureName) {
		ClientResponse response = webServiceClient.resource(UriBuilder.fromUri(baseUrl).path("/person/" + personId).path(measureName).build())
				.get(ClientResponse.class);
		
		if (response.getStatus() != 200) {
			return "NOT IMPLEMENTED";
		} else {
			return response.getEntity(String.class);
		}
	}

	// PUT <baseurl>/person/{id}/{measure}/{mid}
	public String update(Long personId, String measureName, Long measureId, Measure measure) {
		return webServiceClient.resource(UriBuilder.fromUri(baseUrl).path("/person/" + personId).path(measureName + "/" + measureId).build()).accept(MediaType.APPLICATION_XML).entity(measure, MediaType.APPLICATION_XML).put(String.class);
	}

	// GET <baseurl>/person/{id}/{measure}/{mid}
	public String get(Long personId, String measureName, Long measureId) {
		ClientResponse response =  webServiceClient.resource(UriBuilder.fromUri(baseUrl).path("/person/" + personId).path(measureName + "/" + measureId).build())
				.get(ClientResponse.class);
		
		if (response.getStatus() != 200) {
			return "NOT IMPLEMENTED";
		} else {
			return response.getEntity(String.class);
		}
	}

	// DELETE <baseurl>/person/{id}/{measure}/{mid}
	public void delete(Long personId, String measureName, Long measureId) {
		webServiceClient.resource(UriBuilder.fromUri(baseUrl).path("/person/" + personId).path(measureName + "/" + measureId).build()).delete();
	}
}
