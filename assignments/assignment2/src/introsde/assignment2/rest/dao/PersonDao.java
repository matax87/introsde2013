package introsde.assignment2.rest.dao;

import introsde.assignment2.rest.model.Person;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonDao {
	private Map<Integer, Person> contentProvider = new HashMap<Integer, Person>();
	
	private static PersonDao instance;
	
	/** A private Constructor prevents any other class from instantiating. */
	private PersonDao() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, 8);
        cal.set(Calendar.DATE, 16);
        cal.set(Calendar.YEAR, 1987);		
		Person me = new Person(1, "Matteo", "Matassoni", cal.getTime());
        contentProvider.put(me.getId(), me);
	}
	
	public static synchronized PersonDao getInstance() {
		if (instance == null) {
			instance = new PersonDao();
		}
		return instance;
	}
	
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	public List<Person> getAll() {
        List<Person> list = new ArrayList<Person>();
        list.addAll(contentProvider.values());
        return list;
    }
	
	public Person get(Integer personId) {
        return contentProvider.get(personId);
    }
	
	public Integer create(Person person) {
		int id = contentProvider.size() + 1;
		person.setId(id);
		contentProvider.put(id, person);
		return id;
    }
	
	public Person update(Integer personId, Person newPerson) {
		Person person = contentProvider.get(personId);
		if (person != null) {
			person.setFirstname(newPerson.getFirstname());
			person.setLastname(newPerson.getLastname());
			person.setBirthDate(newPerson.getBirthDate());
		}
		return person;
    }
	
	public void delete(Integer personId) {
        contentProvider.remove(personId);
    }
}
