package introsde.assignment3.ws;

import introsde.assignment3.ws.dao.*;
import introsde.assignment3.ws.model.*;

import java.util.List;

import javax.jws.WebService;

@WebService(serviceName = "LifeStatus", endpointInterface = "introsde.assignment3.ws.LifeStatus")
public class LifeStatusImpl implements LifeStatus {

	@Override
	public Person readPerson(int personId) {
		Long pid = Long.valueOf(personId);
		return PersonDao.getInstance().get(pid);
	}

	@Override
	public int createPerson(Person p) {
		Long createdId = PersonDao.getInstance().create(p);
		if (createdId != null) {
			return createdId.intValue();
		} else {
			return -1;
		}
	}

	@Override
	public int updatePerson(Person p) {
		Person updatedPerson = PersonDao.getInstance().update(p);
		if (updatedPerson != null) {
			return updatedPerson.getId().intValue();
		} else {
			return -1;
		}
	}

	@Override
	public int deletePerson(int personId) {
		Long pid = Long.valueOf(personId);
		boolean deleted = PersonDao.getInstance().delete(pid);
		if (deleted) {
			return 0;
		} else {
			return -1;
		}
	}

	@Override
	public int updatePersonHealthProfile(int personId,
			HealthProfile hp) {
		Long pid = Long.valueOf(personId);
		HealthProfile updatedHP = HealthProfileDao.getInstance().update(pid, hp);
		if (updatedHP != null) {
			return updatedHP.getId().intValue();
		} else {
			// If no health profile exists yet, create it
			HealthProfile latestHP = HealthProfileDao.getInstance().getLatest(pid);
			if (latestHP == null) {
				Long createdId = HealthProfileDao.getInstance().create(pid, hp);
				if (createdId != null) {
					return createdId.intValue();
				}
			}
			return -1;
		}
	}
	
	@Override
	public int addPersonHealthProfile(int personId, HealthProfile hp) {
		Long pid = Long.valueOf(personId);
		Long createdId = HealthProfileDao.getInstance().create(pid, hp);
		if (createdId != null) {
			return createdId.intValue();
		} else {
			return -1;
		}
	}

	@Override
	public HealthProfileHistory getHealthProfileHistory(int personId) {
		Long pid = Long.valueOf(personId);
		List<HealthProfile> historyList = HealthProfileDao.getInstance().getAll(pid);
		return new HealthProfileHistory(historyList);
	}
}
