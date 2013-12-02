package introsde.assignment2.rest.client;

import introsde.assignment2.rest.model.Person;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
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

	// POST <baseurl>/person
	public URI create(Person person) {
		ClientResponse response = webServiceClient.resource(UriBuilder.fromUri(baseUrl).path("/person").build())
				.entity(person, MediaType.APPLICATION_XML).post(ClientResponse.class);
		return response.getLocation();
	}

	// GET <baseurl>/person
	public String getAll() {
		ClientResponse response = webServiceClient.resource(UriBuilder.fromUri(baseUrl).path("/person").build())
				.get(ClientResponse.class);

		if (response.getStatus() != 200) {
			return "NOT IMPLEMENTED";
		} else {
			return response.getEntity(String.class);
		}
	}

	// PUT <baseurl>/person/{id}
	public String update(Long personId, Person person) {
		return webServiceClient.resource(UriBuilder.fromUri(baseUrl).path("/person/" + personId).build())
				.entity(person, MediaType.APPLICATION_XML).put(String.class);
	}

	// GET <baseurl>/person/{id}
	public String get(Long personId) {
		ClientResponse response = webServiceClient.resource(UriBuilder.fromUri(baseUrl).path("/person/" + personId).build())
				.get(ClientResponse.class);

		if (response.getStatus() != 200) {
			return "NOT IMPLEMENTED";
		} else {
			return response.getEntity(String.class);
		}
	}

	// DELETE <baseurl>/person/{id}
	public void delete(Long personId) {
		webServiceClient.resource(UriBuilder.fromUri(baseUrl).path("/person/" + personId).build())
		.delete();
	}
}
