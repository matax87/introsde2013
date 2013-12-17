package introsde.assignment3.ws;

import introsde.assignment3.ws.model.HealthProfile;
import introsde.assignment3.ws.model.HealthProfileHistory;
import introsde.assignment3.ws.model.Person;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface LifeStatus {

	@WebMethod(operationName = "readPerson")
	@WebResult(name = "person")
	public Person readPerson(@WebParam(name = "personId") int personId);
	
	@WebMethod(operationName = "createPerson")
	public int createPerson(@WebParam(name = "person") Person p);
	
	@WebMethod(operationName = "updatePerson")
	public int updatePerson(@WebParam(name = "person") Person p);
	
	@WebMethod(operationName = "deletePerson")
	public int deletePerson(@WebParam(name = "personId") int personId);
	
	@WebMethod(operationName = "updatePersonHealthProfile")
	public int updatePersonHealthProfile(@WebParam(name = "personId") int personId, 
										 @WebParam(name = "healthProfile") HealthProfile hp);
	
	@WebMethod(operationName = "addPersonHealthProfile")
	public int addPersonHealthProfile(@WebParam(name = "personId") int personId, 
			 						  @WebParam(name = "healthProfile") HealthProfile hp);
	
	@WebMethod(operationName = "getHealthProfileHistory")
	@WebResult(name = "healthProfile-history")
	public HealthProfileHistory getHealthProfileHistory(@WebParam(name = "personId") int personId);
}
