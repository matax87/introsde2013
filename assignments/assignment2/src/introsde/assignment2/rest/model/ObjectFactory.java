package introsde.assignment2.rest.model;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {
  public People createPeople() {
    return new People();
  }
  
  public Person createPerson() {
    return new Person();
  }
}

