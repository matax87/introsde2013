//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.11.18 at 04:15:14 PM CET 
//


package introsde.assignment2.rest.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement
@XmlType(name = "PeopleType")
public class People {	
    private List<Person> person;
    
    public People() {}

	public People(List<Person> personList) {
		this.person = personList;
	}

	@XmlElement
	public List<Person> getPerson() {
		if (person == null) {
            person = new ArrayList<Person>();
        }
        return this.person;
	}

	public void setPerson(List<Person> person) {
		this.person = person;
	}
	
	
}
