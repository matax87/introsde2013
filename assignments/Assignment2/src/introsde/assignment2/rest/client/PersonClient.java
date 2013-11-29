package introsde.assignment2.rest.client;

import introsde.assignment2.rest.model.Person;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class PersonClient {

	private final String baseUrl;

	private final Client webServiceClient;
	
	public PersonClient(String baseUrl) {
	    this.baseUrl = baseUrl;
	    ClientConfig cc = new DefaultClientConfig();
	    webServiceClient = Client.create(cc);
	  }

	// PUT <baseurl>/person/{id}
	public String update(Long personId, Person person) {
		return webServiceClient.resource(UriBuilder.fromUri(baseUrl).path("/person/" + personId).build()).accept(MediaType.APPLICATION_XML).entity(person, MediaType.APPLICATION_XML).put(String.class);
	}

	// GET <baseurl>/person/{id}
	public String get(Long personId) {
		return webServiceClient.resource(UriBuilder.fromUri(baseUrl).path("/person/" + personId).build()).accept(MediaType.APPLICATION_XML).get(String.class);
	}

	// DELETE <baseurl>/person/{id}
	public void delete(Long personId) {
		webServiceClient.resource(UriBuilder.fromUri(baseUrl).path("/person/" + personId).build()).delete();
	}
}
