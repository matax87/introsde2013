package introsde.assignment3.ws.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlType(name = "MeasureHistoryType")
public class HealthProfileHistory {
	
	private List<HealthProfile> healthProfileList;
	
	public HealthProfileHistory() {
    	super();
    }

	public HealthProfileHistory(List<HealthProfile> healthProfileList) {
		this();
		this.healthProfileList = healthProfileList;
	}
	
	@XmlElement(name = "healthProfile")
	public List<HealthProfile> getHealthProfileList() {
		if (healthProfileList == null) {
			healthProfileList = new ArrayList<HealthProfile>();
        }
        return healthProfileList;
	}
	
	public void setHealthProfileList(List<HealthProfile> healthProfileList) {
		this.healthProfileList = healthProfileList;
	}
}
