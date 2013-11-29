package introsde.assignment2.rest.client;

import introsde.assignment2.rest.model.Person;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class PeopleClient {

	private final String baseUrl;

	private final Client webServiceClient;
	
	public PeopleClient(String baseUrl) {
	    this.baseUrl = baseUrl;
	    ClientConfig cc = new DefaultClientConfig();
	    webServiceClient = Client.create(cc);
	  }

	// POST <baseurl>/person
	public void create(Person person) {
		webServiceClient.resource(UriBuilder.fromUri(baseUrl).path("/person").build()).entity(person, MediaType.APPLICATION_XML).post();
	}

	// GET <baseurl>/person
	public String getAll() {
	    return webServiceClient.resource(UriBuilder.fromUri(baseUrl).path("/person").build()).accept(MediaType.APPLICATION_XML).get(String.class);
	}
}
